package com.eriksson.repo;

import com.eriksson.entity.Member;
import com.eriksson.entity.Rental;
import com.eriksson.exception.InvalidIdException;
import com.eriksson.exception.MemberNotFoundException;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentalRepository implements RentalRepositoryInterface {
    private final SessionFactory sessionFactory;

    public RentalRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Rental rental) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            if (rental.getId() != null) {
                session.merge(rental);
            } else {
                session.persist(rental);
            }
            tx.commit();
        }
    }

    @Override
    public List<String> getAllRentals() {
        String sql = "SELECT id, rentalType FROM Rental";

        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery(sql)) {
            List<String> rentals = new ArrayList<>();
            while (rs.next()) {
                Long id = rs.getLong("id");
                String rentalType = rs.getString("rentalType");
                rentals.add("Rental{id=%d, rentalType=%s'}".formatted(id, rentalType));
            }
            return rentals;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Rental getRentalById(Long id) {
      //  String sql = "UPDATE Rental SET returnDate = ? WHERE id = ?";
        try (Session session = sessionFactory.openSession())
        {
            Transaction tx = session.beginTransaction();

            // 1. Hämta entiteten (t.ex. User-klass mappad med @Entity)
            Rental rental = session.get(Rental.class, 1L);

            // 2. Ändra värden via setters
            if (rental != null) {
                rental.setReturnDate(LocalDate.now());
                // 3. Uppdatera (valfritt om du är inom en transaction,
                // då Hibernate automatiskt sparar ändringar vid commit)
                session.merge(rental);
            }

            tx.commit(); // Här körs SQL UPDATE
            if (id == null) {
                throw new InvalidIdException("Hittar ingen uthyrning med detta id");
            }
            return rental;
        }
    }

    public void terminateRental(Long id) {
        String sql = """
                UPDATE rental SET returnDate = ? rental WHERE id = ? """;
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);

                ps.setDate(1, Date.valueOf(LocalDate.now()));
                ps.setLong(2, id);
            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rad(er) uppdaterad(e).");

        } catch (SQLException e) {
            throw new InvalidIdException("Finns ingen uthyrning med detta id");
        }
    }
}

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