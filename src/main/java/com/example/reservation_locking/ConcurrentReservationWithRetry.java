package com.example.reservation_locking;

import com.example.model.Reservation;
import com.example.service.ReservationService;
import com.example.service.ReservationServiceImpl;

import java.time.LocalDateTime;

public class ConcurrentReservationWithRetry {

    public static void main(String[] args) {

        ReservationService service = new ReservationServiceImpl();

        ReservationRetryHandler retryHandler =
                new ReservationRetryHandler(service);

        // 1. Créer une réservation
        Reservation reservation = new Reservation(
                "Salle A",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusHours(2)
        );

        reservation = service.creerReservation(reservation);

        // 2. Simuler deux lectures de la même réservation
        Reservation tx1 =
                service.trouverParId(reservation.getId());

        Reservation tx2 =
                service.trouverParId(reservation.getId());

        // 3. Première modification
        tx1.setSalle("Salle B");
        service.modifierReservation(tx1);

        System.out.println(
                "Transaction 1 : modification réussie."
        );

        // 4. Deuxième modification avec retry
        try {
            Reservation resultat =
                    retryHandler.modifierAvecRetry(tx2, "Salle C");

            System.out.println(
                    "Transaction 2 : modification réussie après retry."
            );

            System.out.println(
                    "Salle finale : " + resultat.getSalle()
            );

            System.out.println(
                    "Version finale : " + resultat.getVersion()
            );

        } catch (Exception e) {
            System.err.println(
                    "Transaction 2 : échec - " + e.getMessage()
            );
        }
    }
}