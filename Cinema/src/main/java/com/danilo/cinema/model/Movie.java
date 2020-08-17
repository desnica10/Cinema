package com.danilo.cinema.model;

import com.danilo.cinema.util.Genre;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Table
@Entity(name = "movies")
public class Movie {

    @Id
    @SequenceGenerator(name = "movie_id_seq", sequenceName = "movie_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_id_seq")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Genre type;

    @Column(nullable = false)
    private int duration;

    @Column(name = "grade")
    private double averageGrade;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Grade> grades = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Projection> projections = new HashSet<>();
}
