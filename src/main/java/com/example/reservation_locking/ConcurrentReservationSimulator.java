package com.example.reservation_locking;

import com.example.model.Reservation;
import com.example.service.ReservationService;
import com.example.service.ReservationServiceImpl;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;

public class ConcurrentReservationSimulator {

    public static void main(String[] args) {

        ReservationService service = new ReservationServiceImpl();

        // Créer une réservation initiale
        Reservation reservation = new Reservation(
                "Salle A",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusHours(2)
        );

        reservation = service.creerReservation(reservation);

        Long id = reservation.getId();

        System.out.println("Réservation créée avec ID : " + id);

        // Deux lectures indépendantes de la même réservation
        Reservation tx1 = service.trouverParId(id);
        Reservation tx2 = service.trouverParId(id);

        System.out.println("Version initiale : " + tx1.getVersion());

        // Transaction 1 : modifier la réservation
        tx1.setSalle("Salle B");
        service.modifierReservation(tx1);

        System.out.println("Transaction 1 : modification réussie.");

        // Transaction 2 : tenter une modification obsolète
        try {
            tx2.setSalle("Salle C");
            service.modifierReservation(tx2);

            System.out.println(
                    "Transaction 2 : modification réussie."
            );

        } catch (OptimisticLockException e) {
            System.out.println(
                    "Transaction 2 : conflit détecté."
            );
        }

        // Afficher l'état final
        Reservation finale = service.trouverParId(id);

        System.out.println("Salle finale : " + finale.getSalle());
        System.out.println("Version finale : " + finale.getVersion());
    }
}
