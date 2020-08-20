package com.danilo.cinema.repository;

import com.danilo.cinema.model.Movie;
import com.danilo.cinema.util.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(value = "select distinct p.movie from tickets t inner join t.projection p where ((:userId) = (t.customer" +
            ".id)) " +
            "and " +
            "((p.date) < (:date)) and ((t.status) = (:status))")
    List<Movie> findWatchedMovies(@Param("userId") Long userId, @Param("date") Date date,
                                  @Param("status") TicketStatus status);
}
