import jpql.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;

public class FETCH_JOIN extends BASE{

    // alt + shift + insert -> shift up/down, alt + j
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    private static EntityManager em = emf.createEntityManager();
    private static EntityTransaction tx = em.getTransaction();

    public static void main(String[] args) {


        tx.begin();

        try {

            //fetchJoin();
            //distinct();
            //paging();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }

    private static void paging() {
        String query = "select distinct t from Team t join fetch t.members";
        String query2 = "select t from Team t join fetch t.members";
        String query3 = "select t from Team t";

        List<Team> resultList = em.createQuery(query3, Team.class)
                //.setFirstResult(0)
                //.setMaxResults(2)
                .getResultList();

        printList(resultList);
        printList(resultList.get(0).getMembers());
        printList(resultList.get(1).getMembers());
    }

    private static void distinct() {
        String query4 = "select distinct t from Team t join fetch t.members";
        String query5 = "select distinct t from Team t join t.members";

        List<Team> resultList4 = em.createQuery(query4, Team.class).getResultList();
        List<Team> resultList5 = em.createQuery(query5, Team.class).getResultList();
    }

    private static void fetchJoin() {
        String query0 = "select m from Member m join m.team";
        String query1 = "select m from Member m inner join fetch m.team";
        String query2 = "select m from Member m join fetch m.team";
        String query3 = "select t from Team t join fetch t.members";

        List<Member> resultList = em.createQuery(query0, Member.class).getResultList();
        List<Member> resultList1 = em.createQuery(query1, Member.class).getResultList();
        List<Member> resultList2 = em.createQuery(query2, Member.class).getResultList();
        List<Team> resultList3 = em.createQuery(query3, Team.class).getResultList();
    }


}
