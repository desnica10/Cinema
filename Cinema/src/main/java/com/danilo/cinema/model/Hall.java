package com.danilo.cinema.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Table
@Entity(name = "halls")
public class Hall {

    @Id
    @SequenceGenerator(name = "hall_id_seq", sequenceName = "hall_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hall_id_seq")
    private long id;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private Cinema cinema;

    @ManyToMany(mappedBy = "halls")
    private Set<Projection> projections = new HashSet<>();
}
