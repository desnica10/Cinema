package com.danilo.cinema.controller;

import com.danilo.cinema.dto.GradeDTO;
import com.danilo.cinema.dto.MovieDTO;
import com.danilo.cinema.service.MovieService;
import com.danilo.cinema.util.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @GetMapping("/type")
    public ResponseEntity<?> getCinemaTypes(){

        return new ResponseEntity<>(Genre.values(), HttpStatus.OK);
    }

    @GetMapping("/watched/{userId}")
    public ResponseEntity<?> getWatchedMovies(@PathVariable Long userId){

        List<MovieDTO> movies = movieService.findWatchedMovies(userId, new Date());

        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @PutMapping("/watched")
    public ResponseEntity<?> rateWatchedMovies(@RequestBody GradeDTO request){

        List<MovieDTO> movies = movieService.rateWatchedMovies(request);

        if (movies == null){
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/grade/{userId}")
    public ResponseEntity<?> getMoviesRates(@PathVariable Long userId){

        List<GradeDTO> grades = movieService.findUsersGrades(userId);

        if (grades == null){
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(grades, HttpStatus.OK);
    }
}
