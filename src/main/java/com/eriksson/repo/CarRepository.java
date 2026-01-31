package com.eriksson.repo;

import com.eriksson.entity.Car;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Optional;

public class CarRepository implements CarRepositoryInterface {

    private final SessionFactory sessionFactory;

    public CarRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public void save(Car car) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(car);
            tx.commit();
        }
    }
    public Optional<Car> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Car.class, id));
        }
    }
}
