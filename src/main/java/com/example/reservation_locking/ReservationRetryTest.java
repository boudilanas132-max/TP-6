package com.example.reservation_locking;


import com.example.model.Reservation;
import com.example.service.ReservationService;
import com.example.service.ReservationServiceImpl;

import java.time.LocalDateTime;

public class ReservationRetryTest {

    public static void main(String[] args) {

        ReservationService service = new ReservationServiceImpl();

        // Créer une réservation
        Reservation reservation = new Reservation(
                "Salle A",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusHours(2)
        );

        reservation = service.creerReservation(reservation);

        // Créer le gestionnaire de retry
        ReservationRetryHandler handler =
                new ReservationRetryHandler(service);

        // Modifier la salle avec la stratégie de retry
        Reservation resultat = handler.modifierAvecRetry(
                reservation,
                "Salle B"
        );

        System.out.println("Modification terminée.");
        System.out.println("Salle finale : " + resultat.getSalle());
        System.out.println("Version finale : " + resultat.getVersion());
    }
}
