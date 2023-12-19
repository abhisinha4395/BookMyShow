package com.example.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Movie extends BaseModel {
    private String name;


    @Enumerated(EnumType.ORDINAL)
    @ElementCollection
    private List<Genre> genres;
    private double duration;

    //private List<String> languages;
    //private List<String> cast;

    // M : F
    // 1 : M
    // M : 1
    @Enumerated(EnumType.ORDINAL)
    @ElementCollection
    private List<Feature> features;
}
