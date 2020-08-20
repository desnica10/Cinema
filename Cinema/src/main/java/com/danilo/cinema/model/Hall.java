package com.danilo.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Cinema cinema;

    @ManyToMany(mappedBy = "halls", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private Set<Projection> projections = new HashSet<>();
}
