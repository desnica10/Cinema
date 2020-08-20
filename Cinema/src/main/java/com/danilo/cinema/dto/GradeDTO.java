package com.danilo.cinema.dto;

import lombok.Data;

@Data
public class GradeDTO {
    private long id;
    private double grade;
    private MovieDTO movie;
    private UserDTO customer;
}
