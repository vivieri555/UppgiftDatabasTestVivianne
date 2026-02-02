package com.eriksson.repo;

import com.eriksson.entity.Member;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
    public void delete(Member member) {
            try (Session session = sessionFactory.openSession()) {
                Transaction tx = session.beginTransaction();
                session.remove(member);
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
//    public Member searchMemberList(String searchedMember){
//        for(Member member: memberRegistry.getMembers()){
//            if(member.getName().contains(searchedMember)){
//                return member;
//            }
//        } return null;
//    }


    @Override
    public String searchEmail (Member member) {
        String sql = """
                    SELECT *
                    FROM member
                    WHERE member_email = ?
                    """;
        try (Session session = sessionFactory.openSession()) {
            String r = (String) session.createNativeQuery(sql)
                    .setParameter(1, member.getEmail())
                    .getSingleResult();
            return r;
        }
    }
/*
    public void searchMember(Member member) {
        String sql = """
                    SELECT *
                    FROM member
                    WHERE member_id = ?
                    """;
        try (Connection connection = Database.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, member_id);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    String name = rs.getString("pingla_name");
                }
            }
        }

        try (Session session  = sessionFactory.openSession()
        PreparedStatement ps = connection) {
            ps.setString(1, id);
            try (PreparedStatement ps = session.prepareStatement(sql)) {


            Number count = (Number) session.createNativeQuery(sql)
                    .setParameter("memberId", memberId)
                    .getSingleResult();
            return count.longValue() > 0;
        }
    }
}

// getSingleResult returnerar ett Number (dialekt-beroende)
// Om count > 0 finns minst en bokning
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
                }
