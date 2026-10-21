package org.example.gomme.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Check;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "GOMME")
@Getter
@Setter
public class Gomme {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "targa", nullable = false, columnDefinition = "CHAR(7)")
    @Pattern(regexp = "^[A-Z]{2}\\d{3}[A-Z]{2}$")
    @Check(constraints = "targa ~ '^[A-Z]{2}\\d{3}[A-Z]{2}$'")
    private String targa;

    @Column(name = "cliente", nullable = false)
    private String cliente;

    @Column(name = "misura", nullable = false)
    @Pattern(regexp = "^\\d+/\\d+R\\d+$")
    @Check(constraints = "misura ~ '^\\d+/\\d+R\\d+$'")
    private String misura;

    @Column(name = "carico", nullable = false)
    private int carico;

    @Column(name = "speed", nullable = false)
    @Enumerated(EnumType.STRING)
    private Speed speed;

    @Column(name = "season", nullable = false)
    @Enumerated(EnumType.STRING)
    private Season season;

    @Column(name = "vendita", nullable = false)
    private boolean vendita;

    @Column(name = "noleggio", nullable = false)
    private String noleggio;

    @Column(name = "edificio", nullable = false)
    @Enumerated(EnumType.STRING)
    private Edificio edificio;

    @Column(name = "area", nullable = false)
    @Enumerated(EnumType.STRING)
    private Area area;

    @Column(name = "fila", nullable = false)
    @PositiveOrZero
    private int fila;

    @Column(name = "quantity", nullable = false)
    @Positive
    private int quantity;
}
