package com.danilo.cinema.model;

import com.danilo.cinema.util.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tickets")
public class Ticket {

    @Id
    private long id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;

    @Column(nullable = false)
    private Date reservationDate;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private User customer;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private Projection projection;
}
