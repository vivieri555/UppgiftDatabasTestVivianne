package com.eriksson.repo;

import com.eriksson.entity.Bike;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Optional;

public class BikeRepository implements BikeRepositoryInterface {

    private final SessionFactory sessionFactory;
    public BikeRepository(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory;}

    @Override
    public void save(Bike bike) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            session.persist(bike);
            tx.commit();
        }
    }

    @Override
    public Optional<Bike> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(
                    session.get(Bike.class, id)
            );
        }
    }
}
