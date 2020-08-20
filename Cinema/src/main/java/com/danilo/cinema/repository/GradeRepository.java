package com.danilo.cinema.repository;

import com.danilo.cinema.model.Grade;
import com.danilo.cinema.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAllByCustomer(User user);
}
