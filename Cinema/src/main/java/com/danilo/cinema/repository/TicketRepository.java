package com.danilo.cinema.repository;

import com.danilo.cinema.model.Hall;
import com.danilo.cinema.model.Projection;
import com.danilo.cinema.model.Ticket;
import com.danilo.cinema.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{

    List<Ticket> findAllByCustomer(User customer);
    List<Ticket> findAllByProjectionAndHall(Projection projection, Hall hall);

    @Query(value = "select distinct t from tickets t inner join t.projection p where ((:userId) = (t.customer.id)) " +
            "and " +
            "((:cinemaId) = (p.cinema.id))")
    List<Ticket> findAllTicketsInCinema(@Param("userId") Long userId, @Param("cinemaId") Long cinemaId);
}
