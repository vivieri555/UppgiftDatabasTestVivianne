package com.eriksson.repo;

import com.eriksson.entity.Rental;
import com.eriksson.enums.RENTALTYPE;
import com.eriksson.exception.InvalidIdException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.*;
import java.time.LocalDate;
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
    public List<Rental> getAllRentals() {
        try (Session session = sessionFactory.openSession()) {
            String sql = "SELECT * FROM Rental";

            return session.createNativeQuery(sql, Rental.class).getResultList();

                } catch (Exception ex) {
                    throw ex;
            }
    }
@Override
    public Rental getRentalById(Long id) {
        try (Session session = sessionFactory.openSession())
        {
            Transaction tx = session.beginTransaction();
            Rental rental = session.get(Rental.class, 1L);
            if (rental != null) {
                rental.setReturnDate(LocalDate.now());

                session.merge(rental);
            }
            tx.commit();
            if (id == null) {
                throw new InvalidIdException("Hittar ingen uthyrning med detta id");
            }
            return rental;
        }
    }
    //Kan inte hyra om större än noll
    public Boolean isVehicleRented(RENTALTYPE rentalType, long rentalObjectId) {
        String sql = "SELECT rentalType, rentalObjectId " +
                "FROM Rental WHERE rentalType = ? " +
                "AND  rentalObjectId = ? AND returnDate > now() AND rentalDate < now()";
        try (Connection c = Database.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, rentalType.ordinal());
            ps.setLong(2, rentalObjectId);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void terminateRental(Long id) {
        String sql = """
                UPDATE rental SET returnDate = ? WHERE id = ? """;
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

                ps.setDate(1, Date.valueOf(LocalDate.now()));
                ps.setLong(2, id);
                ps.executeUpdate();
            System.out.println("Avslutat uthyrning");

        } catch (SQLException e) {
            throw new InvalidIdException("Finns ingen uthyrning med detta id");
        }
    }
}