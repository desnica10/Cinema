package com.danilo.cinema.controller;

import com.danilo.cinema.dto.UserDTO;
import com.danilo.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId){

        UserDTO user = userService.findUserById(userId);

        if (user == null){
            return new ResponseEntity<>("User do not exists", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserDTO request){

        UserDTO user = userService.updateUser(userId, request);

        if (user == null){
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> changePassword(@PathVariable Long userId, @RequestBody UserDTO request){

        boolean success = userService.changePassword(userId, request);

        if (success == false){
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Your changed password successfuly", HttpStatus.OK);
    }
}
