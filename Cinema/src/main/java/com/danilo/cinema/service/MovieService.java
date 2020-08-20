package com.danilo.cinema.service;

import com.danilo.cinema.dto.MovieDTO;
import com.danilo.cinema.model.Movie;
import com.danilo.cinema.repository.CinemaRepository;
import com.danilo.cinema.repository.MovieRepository;
import com.danilo.cinema.repository.ProjectioRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    DozerBeanMapper mapper = new DozerBeanMapper();

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    CinemaRepository cinemaRepository;

    @Autowired
    ProjectioRepository projectioRepository;

    public List<MovieDTO> findAllMovies() {

        List<Movie> movies = movieRepository.findAll();
        List<MovieDTO> movieDTOs = new ArrayList<>();

        for (Movie movie: movies) {
            movieDTOs.add(mapper.map(movie, MovieDTO.class));
        }

        return movieDTOs;
    }

    public List<MovieDTO> findCinemaMovies(Long cinemaId) {

        List<Movie> movies = projectioRepository.findCinemaMovies(cinemaId);
        List<MovieDTO> movieDTOs = new ArrayList<>();

        for (Movie movie : movies) {
            movieDTOs.add(mapper.map(movie,MovieDTO.class));
        }

        return movieDTOs;
    }
}
