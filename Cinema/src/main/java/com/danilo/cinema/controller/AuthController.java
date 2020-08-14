package com.danilo.cinema.controller;

import com.danilo.cinema.dto.UserDTO;
import com.danilo.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO request){

        UserDTO user = userService.createCustomer(request);

        if (user == null){
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO request){
        UserDTO user = userService.findUserByUsername(request.getUsername());

        if (user == null){
            return new ResponseEntity<>("Bad credentials", HttpStatus.BAD_REQUEST);
        }

        if (!request.getPassword().equals(user.getPassword())){
            return new ResponseEntity<>("Bad credentials", HttpStatus.BAD_REQUEST);
        }

        if (!userService.isUserActive(user)){
            return new ResponseEntity<>("This user is not active", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
