package com.danilo.cinema.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Table
@Entity(name = "cinemas")
public class Cinema {

    @Id
    @SequenceGenerator(name = "cinema_id_seq", sequenceName = "cinema_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cinema_id_seq")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "menagers",
            joinColumns = @JoinColumn(name = "cinema_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    Set<User> managers = new HashSet<>();

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Hall> halls = new HashSet<>();

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Projection> projections = new HashSet<>();
}
