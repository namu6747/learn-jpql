import jpql.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;

public class OTHERS extends BASE{

    // alt + shift + insert -> shift up/down, alt + j
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    private static EntityManager em = emf.createEntityManager();
    private static EntityTransaction tx = em.getTransaction();

    public static void main(String[] args) {


        tx.begin();

        try {

            //paramBinding();
            //namedQuery();
            //bulkQuery();


            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }

    private static void bulkQuery() {
        int resultCount = em.createQuery("update Member m set m.age = '5'")
                .executeUpdate();
    }

    private static void namedQuery() {
        List<Member> username =
                em.createNamedQuery("Member.findByUsername", Member.class)
                .setParameter("username", "15")
                .getResultList();
        printOne(username);
    }

    private static void paramBinding() {
        String query = "select i from Item i where type(i) (Book)";
        String query2 = "select i from Item i where treat(i as Book).author - '5'";
        String query3 = "select count(m.id) from Member m where m = :member";
        String query4 = "select count(m) from Member m where m.id = :memberId";
        String query5 = "select m from Member m where m.team = :team";
        String query6 = "select m from Member m where m.team.id = :teamId";

        Team team = em.find(Team.class, 1L);

        List<Member> team1 = em.createQuery(query5, Member.class).setParameter("team", team).getResultList();
        List<Member> teamId = em.createQuery(query6, Member.class).setParameter("teamId", 7L).getResultList();

        printList(team1);
        printList(teamId);
    }


}

