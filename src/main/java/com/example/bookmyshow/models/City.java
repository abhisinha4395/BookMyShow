package com.example.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class City extends BaseModel {
    private String name;
    private String state; //should be an enum

    // C : T
    // 1 : M
    // 1 : 1
    @OneToMany
    private List<Theatre> theatres;
}
