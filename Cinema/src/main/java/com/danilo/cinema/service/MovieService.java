package com.danilo.cinema.service;

import com.danilo.cinema.dto.GradeDTO;
import com.danilo.cinema.dto.MovieDTO;
import com.danilo.cinema.model.Grade;
import com.danilo.cinema.model.Movie;
import com.danilo.cinema.model.User;
import com.danilo.cinema.repository.*;
import com.danilo.cinema.util.TicketStatus;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    GradeRepository gradeRepository;

    @Autowired
    UserRepository userRepository;

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

    public List<MovieDTO> findWatchedMovies(Long userId, Date date) {

        List<Movie> movies = movieRepository.findWatchedMovies(userId, date, TicketStatus.PAID);
        List<MovieDTO> movieDTOs = new ArrayList<>();

        for (Movie movie : movies) {
            movieDTOs.add(mapper.map(movie,MovieDTO.class));
        }

        return movieDTOs;
    }

    public List<MovieDTO> rateWatchedMovies(GradeDTO request) {

        Grade grade = mapper.map(request, Grade.class);
        Movie movie = movieRepository.findById(request.getMovie().getId()).orElse(null);

        if (movie == null){
            return null;
        }

        double rate =
                (movie.getAverageGrade()*movie.getGrades().size() + grade.getGrade()) / (movie.getGrades().size()+1);
        movie.setAverageGrade(rate);

        movieRepository.save(movie);
        gradeRepository.save(grade);

        return findWatchedMovies(grade.getCustomer().getId(), new Date());
    }

    public List<GradeDTO> findUsersGrades(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null){
            return null;
        }

        List<Grade> grades = gradeRepository.findAllByCustomer(user);
        List<GradeDTO> gradeDTOs = new ArrayList<>();

        for (Grade grade :grades) {
            gradeDTOs.add(mapper.map(grade, GradeDTO.class));
        }

        return gradeDTOs;
    }
}
