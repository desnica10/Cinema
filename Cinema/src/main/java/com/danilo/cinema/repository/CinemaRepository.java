package com.danilo.cinema.repository;

import com.danilo.cinema.model.Cinema;
import com.danilo.cinema.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    Cinema findByName(String name);
    List<Cinema> findAllByManagers(User manager);
}
