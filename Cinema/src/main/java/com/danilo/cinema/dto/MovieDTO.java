package com.danilo.cinema.dto;

import com.danilo.cinema.util.Genre;
import lombok.Data;

@Data
public class MovieDTO {
    private Long id;
    private String name;
    private String description;
    private Genre type;
    private int duration;
    private double averageGrade;
}
