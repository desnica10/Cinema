package com.danilo.cinema.controller;

import com.danilo.cinema.dto.MovieDTO;
import com.danilo.cinema.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping("")
    public ResponseEntity<?> getAllMovies(){

        List<MovieDTO> movies = movieService.findAllMovies();

        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/{cinemaId}")
    public ResponseEntity<?> getCinemaMovies(@PathVariable Long cinemaId){

        List<MovieDTO> movies = movieService.findCinemaMovies(cinemaId);

        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
}
