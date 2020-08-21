package com.danilo.cinema.repository;

import com.danilo.cinema.model.Cinema;
import com.danilo.cinema.model.Movie;
import com.danilo.cinema.model.Projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProjectioRepository extends JpaRepository<Projection, Long> {
    List<Projection> findAllByCinema(Cinema cinema);
    Projection findOneById(Long id);

    @Query(value = "select distinct p.movie from projections p inner join p.cinema c where ((:cinemaId) = (c.id)) and" +
            " ((:date) <= (p.date))")
    List<Movie> findCinemaMovies(@Param("cinemaId") Long cinemaId, @Param("date") Date date);

    List<Projection> findAllByCinemaAndMovie(Cinema cinema, Movie movie);

    @Query(value = "select distinct p from projections p inner join p.cinema c where ((:cinema) = (c)) and" +
            " ((:date) <= (p.date))")
    List<Projection> findAllByCinemaAndDate(@Param("cinema") Cinema cinema, @Param("date") Date date);

    @Query(value = "select distinct p from projections p inner join p.cinema c where ((:cinema) = (c)) and" +
            "((:movie) = (p.movie)) and ((:date) <= (p.date))")
    List<Projection> findAllByCinemaAndMovieAndDate(@Param("cinema") Cinema cinema, @Param("movie") Movie movie,
                                                    @Param("date") Date date);
}
