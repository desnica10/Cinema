package com.danilo.cinema.controller;

import com.danilo.cinema.dto.CinemaDTO;
import com.danilo.cinema.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cinema")
public class CinemaController {

    @Autowired
    CinemaService cinemaService;

    @GetMapping("")
    public ResponseEntity<?> getAllCinemas(){

        List<CinemaDTO> cinemas = cinemaService.findAllCinemas();

        return new ResponseEntity<>(cinemas, HttpStatus.OK);
    }

    @GetMapping("/{cinemaId}")
    public ResponseEntity<?> getCinemasById(@PathVariable Long cinemaId){

        CinemaDTO cinema = cinemaService.findCinemaById(cinemaId);

        if (cinema == null){
            return new ResponseEntity<>("Cinema with that id do not exists", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(cinema, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addCinema(@RequestBody CinemaDTO request){

        if (request.getManagers().size() <= 0){
            return new ResponseEntity<>("Cinema must have manager", HttpStatus.BAD_REQUEST);
        }

        List<CinemaDTO> cinemas = cinemaService.addCinema(request);

        if (cinemas == null){
            return new ResponseEntity<>("Cinema with that name already exsists", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(cinemas, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<?> editCinema(@RequestBody CinemaDTO request){

        if (request.getManagers().size() <= 0){
            return new ResponseEntity<>("Cinema must have manager", HttpStatus.BAD_REQUEST);
        }

        List<CinemaDTO> cinemas = cinemaService.editCinema(request);

        if (cinemas == null){
            return new ResponseEntity<>("Cinema with that name already exsists", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(cinemas, HttpStatus.OK);
    }

    @DeleteMapping("/{cinemaId}")
    public ResponseEntity<?> deleteCinema(@PathVariable Long cinemaId){

        List<CinemaDTO> cinemas = cinemaService.deleteCinema(cinemaId);

        if (cinemas == null){
            return new ResponseEntity<>("Cinema with that id do not exists", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(cinemas, HttpStatus.OK);
    }
}
