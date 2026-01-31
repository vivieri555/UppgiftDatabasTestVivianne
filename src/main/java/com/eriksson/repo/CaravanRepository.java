package com.eriksson.repo;

import com.eriksson.entity.Caravan;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Optional;

public class CaravanRepository implements CaravanRepositoryInterface {

    private final SessionFactory sessionFactory;

    public CaravanRepository(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory;}

    @Override
    public void save (Caravan caravan) {
        try (Session session = this.sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
           session.persist(caravan);
            tx.commit();
        }
    }

    @Override
    public Optional<Caravan> findById(Long id) {
       try (Session session = sessionFactory.openSession()) {
           return Optional.ofNullable(session.get(Caravan.class, id));
       }
    }
}
