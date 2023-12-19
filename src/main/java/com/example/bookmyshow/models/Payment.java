package com.example.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Payment extends BaseModel {
    // P : B
    // 1 : 1
    // M : 1
    @ManyToOne
    private Booking booking;
    private int amount;

    @Enumerated(EnumType.ORDINAL)
    private BookingStatus bookingStatus;

    @Enumerated(EnumType.ORDINAL)
    private PaymentProvider paymentProvider;
    private String transactionId;
}
