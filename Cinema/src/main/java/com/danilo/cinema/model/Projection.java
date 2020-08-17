package com.danilo.cinema.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Table
@Entity(name = "projections")
public class Projection {

    @Id
    @SequenceGenerator(name = "projection_id_seq", sequenceName = "projection_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projection_id_seq")
    private long id;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private int price;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private Movie movie;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private Cinema cinema;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "projections_halls",
            joinColumns = @JoinColumn(name = "projection_id"),
            inverseJoinColumns = @JoinColumn(name = "hall_id")
    )
    private Set<Hall> halls = new HashSet<>();

    @OneToMany(mappedBy = "projection", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Ticket> tickets = new HashSet<>();
}
