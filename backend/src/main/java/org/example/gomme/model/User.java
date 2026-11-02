package org.example.gomme.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull @NotBlank
    @Column(unique = true)
    private String username;

    @NotNull @NotBlank
    private String password;
}
