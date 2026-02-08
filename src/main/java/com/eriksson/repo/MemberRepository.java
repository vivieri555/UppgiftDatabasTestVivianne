package com.eriksson.repo;

import com.eriksson.entity.Member;
import com.eriksson.exception.MemberNotFoundException;
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
        try (Session session = sessionFactory.openSession()) {
            Member result = session.get(Member.class, id);
            return result;
        } catch (Exception ex) {
            throw new MemberNotFoundException("Medlemmen hittades inte");
        }
    }

    @Override
    public Optional<Member> searchEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Member m where m.email = :email", Member.class)
                    .setParameter("email", email)
                    .uniqueResultOptional();
        } catch(Exception ex) {
            throw new MemberNotFoundException("Email hittades inte");
        }
    }
    public String updateName(String firstName) {
        String sql = """
                SELECT id, firstName, lastName FROM member WHERE firstName = ? """;
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, firstName);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                return "Customer{id=%d, name='%s', email='%s'}"
                        .formatted(rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("email"));
            }
        } catch (SQLException e) {
            throw new MemberNotFoundException("Hittade ingen medlem");
        }
    }
    public void updateName (String firstName, String lastName, Long id) {
        try (Session session = sessionFactory.openSession()) {
            Member member = session.get(Member.class, id);
            member.setFirstName(firstName);
            member.setLastName(lastName);
            session.merge(member);
        }
    }
}
