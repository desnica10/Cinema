package com.danilo.cinema.dto;

import com.danilo.cinema.model.User;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CinemaDTO {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    Set<User> managers = new HashSet<>();
}
