package com.danilo.cinema.repository;

import com.danilo.cinema.model.User;
import com.danilo.cinema.util.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    List<User> findAllByTypeAndActive(UserType type, boolean active);
}
