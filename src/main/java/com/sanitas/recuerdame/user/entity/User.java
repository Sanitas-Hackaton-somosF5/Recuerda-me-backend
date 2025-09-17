package com.sanitas.recuerdame.user.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 150, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


//     @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//     private List<Medication> medication;
 }