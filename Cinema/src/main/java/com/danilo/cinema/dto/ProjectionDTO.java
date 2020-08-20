package com.danilo.cinema.dto;

import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class ProjectionDTO {
    private long id;
    private Date date;
    private int price;
    private MovieDTO movie;
    private Set<HallDTO> halls = new HashSet<>();
    private CinemaDTO cinema;
}
