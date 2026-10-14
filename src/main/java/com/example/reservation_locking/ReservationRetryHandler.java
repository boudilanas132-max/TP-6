package com.example.reservation_locking;

import com.example.model.Reservation;
import com.example.service.ReservationService;

import javax.persistence.OptimisticLockException;

public class ReservationRetryHandler {

    private static final int MAX_RETRIES = 3;

    private final ReservationService service;

    public ReservationRetryHandler(ReservationService service) {
        this.service = service;
    }

    public Reservation modifierAvecRetry(
            Reservation reservation,
            String nouvelleSalle) {

        Long id = reservation.getId();

        for (int tentative = 1; tentative <= MAX_RETRIES; tentative++) {
            try {
                // Recharger la réservation avec sa version actuelle
                Reservation actuelle = service.trouverParId(id);

                if (actuelle == null) {
                    throw new IllegalArgumentException(
                            "Réservation non trouvée");
                }

                // Réappliquer la modification souhaitée
                actuelle.setSalle(nouvelleSalle);

                // Essayer d'enregistrer la modification
                return service.modifierReservation(actuelle);

            } catch (OptimisticLockException e) {
                System.out.println(
                        "Conflit détecté, tentative "
                        + tentative + "/" + MAX_RETRIES);

                if (tentative == MAX_RETRIES) {
                    throw e;
                }
            }
        }

        throw new IllegalStateException("Modification impossible");
    }
}
