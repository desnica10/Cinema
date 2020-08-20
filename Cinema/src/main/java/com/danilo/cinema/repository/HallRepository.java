package com.danilo.cinema.repository;

import com.danilo.cinema.model.Cinema;
import com.danilo.cinema.model.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
    List<Hall> findAllByCinema(Cinema cinema);

    Hall findByName(String name);
}
