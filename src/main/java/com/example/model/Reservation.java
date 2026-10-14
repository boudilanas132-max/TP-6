package com.example.model;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String salle;

    @Column(nullable = false)
    private LocalDateTime dateDebut;

    @Column(nullable = false)
    private LocalDateTime dateFin;

    // Active le verrouillage optimiste
    @Version
    private int version;

    // Constructeur vide obligatoire pour JPA
    public Reservation() {
    }

    // Constructeur avec paramètres
    public Reservation(String salle, LocalDateTime dateDebut,
                       LocalDateTime dateFin) {
        this.salle = salle;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public int getVersion() {
        return version;
    }
}
