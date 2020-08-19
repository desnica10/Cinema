package com.danilo.cinema.controller;

import com.danilo.cinema.dto.UserDTO;
import com.danilo.cinema.service.UserService;
import com.danilo.cinema.util.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getAllUser(){

        List<UserDTO> users = userService.findAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

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

    @PutMapping("/{userId}/{active}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @PathVariable boolean active){

        List<UserDTO> users = userService.changeUserActivity(userId, active);

        if (users == null){
            return new ResponseEntity<>("User do not exists", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/type/{userId}/{type}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @PathVariable UserType type){

        List<UserDTO> users = userService.changeUserType(userId, type);

        if (users == null){
            return new ResponseEntity<>("User do not exists", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> changePassword(@PathVariable Long userId, @RequestBody UserDTO request){

        boolean success = userService.changePassword(userId, request);

        if (success == false){
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Your changed password successfuly", HttpStatus.OK);
    }

    @GetMapping("/types")
    public ResponseEntity<?> getUserTypes(){

        return new ResponseEntity<>(UserType.values(), HttpStatus.OK);
    }

    @GetMapping("/manager")
    public ResponseEntity<?> getManagers(){

        List<UserDTO> users = userService.findActiveManagers();

        if (users == null){
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
