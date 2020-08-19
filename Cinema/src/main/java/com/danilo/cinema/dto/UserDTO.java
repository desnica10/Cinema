package com.danilo.cinema.dto;

import com.danilo.cinema.util.UserType;
import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Date birth;
    private UserType type;
    private boolean active;
}
