package com.example.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Seat extends BaseModel{

    @ManyToOne
    private SeatType seatType;
    private String seatNumber;

    @OneToOne
    private Coordinate topLeft;
    @OneToOne
    private Coordinate bottomRight;
}
