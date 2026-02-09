    package com.eriksson.repo;

    import com.eriksson.entity.Member;
    import com.eriksson.exception.MemberNotFoundException;
    import com.eriksson.util.HibernateUtil;
    import org.hibernate.Session;
    import org.hibernate.SessionFactory;
    import org.hibernate.Transaction;
    import java.util.List;
    import java.util.Optional;


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
                return session.get(Member.class, id);
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
        public String updateName (String firstName, String lastName, Long id) {
            try (Session session = sessionFactory.openSession()) {
                var tx = session.beginTransaction();
                Member member = session.get(Member.class, id);
                if(member == null) {
                    throw new MemberNotFoundException("Medlemmen hittades inte");
                }
                else {
                    member.setFirstName(firstName);
                    member.setLastName(lastName);
                    session.merge(member);
                    tx.commit();
                    return member.toString();
                }
            }
        }
    }
