package com.example.service;

import com.example.model.Reservation;
import java.util.List;

public interface ReservationService {

    // Créer une nouvelle réservation
    Reservation creerReservation(Reservation reservation);

    // Modifier une réservation existante
    Reservation modifierReservation(Reservation reservation);

    // Rechercher une réservation par son identifiant
    Reservation trouverParId(Long id);

    // Récupérer toutes les réservations
    List<Reservation> listerReservations();
}
