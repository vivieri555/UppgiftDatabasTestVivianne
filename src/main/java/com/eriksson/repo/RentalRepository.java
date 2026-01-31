package com.eriksson.repo;

import com.eriksson.entity.Rental;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class RentalRepository implements RentalRepositoryInterface {
    private final SessionFactory sessionFactory;

    public RentalRepository(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    public void save(Rental rental) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            if (rental.getId() != null) {
                Long rentalId = rental.getId();
                Rental dbRental = session.get(Rental.class, rentalId);
                if (dbRental == null) {
                    session.save(rental);
                } else {
                    //uppdatera dbRental

                }
                session.persist(dbRental);
                tx.commit();
            }
        }
    }
}

/**
 * Setter för Show.
 *
 * Används av Show.addBooking(...)
 * för att hålla relationen i synk.
 */
//public void setShow(Show show) {
//    this.show = show;
//}

/**
 * BookingRepositoryImpl
 *
 * Hibernate-baserad implementation av BookingRepository.
 *
 * Ansvar:
 * - Spara bokningar i databasen
 * - Kontrollera om en plats redan är bokad
 * - Hämta bokningar via id
 *
 * Pedagogiskt:
 * - Detta är DAL-lagret (Data Access Layer)
 * - Innehåller ENBART databaslogik
 * - Ingen affärslogik (den ligger i service-lagret)
 */