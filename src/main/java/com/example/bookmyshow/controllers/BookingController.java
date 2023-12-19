package com.example.bookmyshow.controllers;

import com.example.bookmyshow.dto.BookMovieRequestDto;
import com.example.bookmyshow.dto.BookMovieResponseDto;
import com.example.bookmyshow.dto.ResponseStatus;
import com.example.bookmyshow.models.Booking;
import com.example.bookmyshow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {
    private BookingService bookingService;
    @Autowired // Automatically find the objects in params,
                // and create it if not already, then pass it over
    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }
    public BookMovieResponseDto bookMovie(BookMovieRequestDto bookMovieRequestDto){
        BookMovieResponseDto responseDto = new BookMovieResponseDto();
        try {
            Booking booking = bookingService.bookMovie(bookMovieRequestDto.getUserId(),
                    bookMovieRequestDto.getShowSeatids(), bookMovieRequestDto.getShowId());

            responseDto.setBookingId(booking.getId());
            responseDto.setAmount(booking.getCost());
            responseDto.setResponseStatus(ResponseStatus.SUCCESSFUL);
        }
        catch (Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
