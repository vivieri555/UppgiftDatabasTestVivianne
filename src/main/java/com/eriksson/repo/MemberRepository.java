package com.eriksson.repo;

import com.eriksson.entity.Member;
import com.eriksson.exception.MemberNotFoundException;
import com.eriksson.service.MemberService;
import com.eriksson.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

//spara, hämta och söka data
//Hur kan jag söka i databasen? Select from, villkor på vad som ska vara uppfyllt Where
//en sorts sql query i intellij, Prepared statement, skicka till en query istället för inparametrar

public class MemberRepository implements MemberRepositoryInterface {

    private final SessionFactory sessionFactory;

    public MemberRepository(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    @Override
    public void save(Member member) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(member);
            tx.commit();
        }
    }
    // remove medlem
    public void delete(Long mem) {
            try (Session session = sessionFactory.openSession()) {
                Transaction tx = session.beginTransaction();
                Member member = session.get(Member.class, mem);
                if (member == null) {
                    throw new MemberNotFoundException("Medlemmen hittades inte");
                }
                else {
                    session.remove(member);
                    System.out.println("Medlemmen är borttagen");
                }
                tx.commit();
            }
        }

    @Override
    public List<Member> getAllMembers() {
        //hämta medlemmar från databasen
        try (Session session = sessionFactory.openSession()) {
            String sql = "SELECT * FROM member";
            {
                try {
                    return session.createNativeQuery(sql, Member.class).getResultList();

                } catch (Exception ex) {
                    throw ex;
                }
            }

        }
    }

    @Override
    public Member searchId(Long id) {
        String sql = """
                SELECT id FROM member WHERE id = ? """;
        try (Session session = sessionFactory.openSession()) {
            Member result = (Member) session.createNativeQuery(sql)
                .setParameter(1, id)
                    .getSingleResult();
            return result;
        }
    }

    @Override
    public Optional<Member> searchEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Member c where c.email = :email", Member.class)
                    .setParameter("email", email)
                    .uniqueResultOptional();
        }
    }
}
/*

String sql = "SELECT id, name, email FROM customer WHERE email = ?";

        try (
Connection con = Database.getConnection();
PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, email);
            try (
ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) return null;

        return "Customer{id=%d, name='%s', email='%s'}"
        .formatted(rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("email"));
        }

        } catch (
SQLException e) {
        throw new RuntimeException(e);
        } */

