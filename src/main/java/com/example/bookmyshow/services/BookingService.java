package com.example.bookmyshow.services;

import com.example.bookmyshow.models.*;
import com.example.bookmyshow.repositories.BookingRepository;
import com.example.bookmyshow.repositories.ShowRepository;
import com.example.bookmyshow.repositories.ShowSeatRepository;
import com.example.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private BookingRepository bookingRepository;
    private PriceCalculator priceCalculator;

    @Autowired
    BookingService(UserRepository userRepository, ShowRepository showRepository,
                   ShowSeatRepository showSeatRepository, BookingRepository bookingRepository,
                   PriceCalculator priceCalculator){
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.bookingRepository = bookingRepository;
        this.priceCalculator = priceCalculator;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(Long userId, List<Long> seatIds, Long showId){

        // 1. Get the user by userId
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()){
            throw new RuntimeException("User not Found");
        }
        User bookedBy = userOptional.get();

        // 2. get the show with showId
        Optional<Show> showOptional = showRepository.findById(showId);
        if (showOptional.isEmpty()){
            throw new RuntimeException("Show not available");
        }
        Show bookedShow = showOptional.get();

        // ------ Take lock --------- (start transaction)
        // 3. get the showSeat using seatIds
        List<ShowSeat> showSeats = showSeatRepository.findAllById(seatIds);

        // 4. check if all the seats are available
        for (ShowSeat showSeat : showSeats){
            if (!(showSeat.getState().equals(SeatState.AVAILABLE) ||
                    (showSeat.getState().equals(SeatState.BLOCKED) &&
                            Duration.between(showSeat.getLockedAt().toInstant(),
                                    new Date().toInstant()).toMinutes() > 15))){
                // 5. if no , throw error
                throw new RuntimeException("Selected seats not available");
            }
        }

        // 6. if yes, mark all the selected seats as BLOCKED
        List<ShowSeat> savedShowSeats = new ArrayList<>();
        for (ShowSeat showSeat : showSeats){
            showSeat.setState(SeatState.BLOCKED);
            showSeat.setLockedAt(new Date());
            // 7. Save it in the database
            savedShowSeats.add(showSeatRepository.save(showSeat));
        }

        // ------- release the lock : end transaction -----
        // 8. Create the corresponding Booking object
        Booking booking = new Booking();
        booking.setShow(bookedShow);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setUser(bookedBy);
        booking.setShowSeats(savedShowSeats);
        booking.setBookedAt(new Date());
        booking.setPayments(new ArrayList<>());
        booking.setCost(priceCalculator.calcultatePrice(savedShowSeats, bookedShow));

        // 9. save the booking details in the database
        booking = bookingRepository.save(booking);
        // 10. return  the booking object
        return booking;
    }
}
