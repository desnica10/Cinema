package com.danilo.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "grades")
public class Grade {

    @Id
    @SequenceGenerator(name = "grade_id_seq", sequenceName = "grade_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grade_id_seq")
    private long id;

    @Column(nullable = false)
    private double grade;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private Movie movie;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private User customer;
}
