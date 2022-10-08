import jpql.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class JOIN  extends BASE{

    // alt + shift + insert -> shift up/down, alt + j
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    private static EntityManager em = emf.createEntityManager();
    private static EntityTransaction tx = em.getTransaction();

    public static void main(String[] args) {


        tx.begin();

        try {

            //dataInit();
            //join();
            //subQuery();
            //enumQuery();
            //joinQuery();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }

    private static void joinQuery() {
        String query = "select m.team from Member m ";
        String query2 = "select t.members from Team t";
        String query3 = "select m.age from Team t join t.members m";

        List<Team> resultList = em.createQuery(query, Team.class).getResultList();
        List<Collection> resultList1 = em.createQuery(query2, Collection.class).getResultList();
        List<Integer> resultList2 = em.createQuery(query3, Integer.class).getResultList();

        printList(resultList);
        printList(resultList1);
        printList(resultList2);
    }


    private static void enumQuery() {
        String query =
                "select m, 'fuck', FALSE, 20, 20L " +
                "from Member m " +
                "where type = :usertype OR type = jpql.MemberType.ADMIN";

        List resultList = em.createQuery(query)
                .setParameter("usertype",MemberType.USER)
                .getResultList();

        printList(resultList);
    }

    private static void subQuery() {
        String query = "select m from Member m WHERE m.age > (select avg(m2.age) from Member m2)";
        String query2 = "select avg(m.age) from Member m";

        List<Member> resultList = em.createQuery(query, Member.class).getResultList();
        Double singleResult = em.createQuery(query2, Double.class).getSingleResult();

        printList(resultList);
        printOne(singleResult);
    }

    private static void join() {
        String query = "select m from Member m inner join m.team t";

        List<Member> resultList1 = em.createQuery(query, Member.class).getResultList();
        Member member = resultList1.get(0);
        member.getTeam().setName(member.getUsername());
        printOne(member);


        em.flush();
        em.clear();

        String query2 = "select m from Member m left outer join m.team t";
        String query3 = "select m FROM Member m, Team t WHERE m.username = t.name";
        String query4 = "select m from Member m left outer join m.team t on t.name = '2'";
        String query5 = "select m from Member m inner join Team t on m.username = t.name";

        List<Member> resultList = em.createQuery(query3, Member.class).getResultList();
        List<Member> resultList2 = em.createQuery(query4, Member.class).getResultList();
        List<Member> resultList3 = em.createQuery(query5, Member.class).getResultList();

        printList(resultList);
        printList(resultList2);
        printList(resultList3);
    }

}
