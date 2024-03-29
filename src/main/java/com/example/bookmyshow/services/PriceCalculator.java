package com.example.bookmyshow.services;

import com.example.bookmyshow.models.Show;
import com.example.bookmyshow.models.ShowSeat;
import com.example.bookmyshow.models.ShowSeatType;
import com.example.bookmyshow.repositories.ShowSeatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceCalculator {

    private ShowSeatTypeRepository showSeatTypeRepository;

    @Autowired
    PriceCalculator(ShowSeatTypeRepository showSeatTypeRepository){
        this.showSeatTypeRepository = showSeatTypeRepository;
    }

    public long calcultatePrice(List<ShowSeat> showSeats, Show show){

        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShow(show);
        long amount = 0;
        for (ShowSeat showSeat : showSeats){
            for (ShowSeatType showSeatType : showSeatTypes){
                if (showSeat.getSeat().getSeatType().equals(showSeatType.getSeatType())){
                    amount += showSeatType.getPrice();
                }
            }
        }
        return amount;
    }
}
