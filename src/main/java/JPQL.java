import jpql.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

public class JPQL extends BASE{

    // alt + shift + insert -> shift up/down, alt + j
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    private static EntityManager em = emf.createEntityManager();
    private static EntityTransaction tx = em.getTransaction();

    public static void main(String[] args) {


        tx.begin();

        try {

            // typedQuery();
            // innerJoin();
            // projection();
            // paging();


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
        List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
                .setFirstResult(5)
                .setMaxResults(5)
                .getResultList();
        printList(resultList);
    }

    private static void projection() {
        List<Address> resultList =
                em.createQuery("select o.address from Order o",
                        Address.class).getResultList();
        printList(resultList);

        // 순서를 보장하지 않는다. String이 함께 나타나서 그런가?
        List<Object[]> resultList1 = em.createQuery("select distinct m.username, m.age FROM Member m").getResultList();
        printList(resultList1);

        List<MemberDTO> resultList2 = em.createQuery("select distinct new jpql.MemberDTO(m.username, m.age) FROM Member m", MemberDTO.class).getResultList();
        printList(resultList2);

    }

    private static void innerJoin() {
        // join 은 명시적으로 하는 게 좋다.
        List<Team> resultList =
                em.createQuery("select t from Member m join m.team t",
                        Team.class).getResultList();
        printList(resultList);

        List<Team> resultList1 =
                em.createQuery("select m.team from Member m",
                        Team.class).getResultList();
        printList(resultList1);
    }

    private static void typedQuery() {
        TypedQuery<Member> query = em.createQuery("select m from Member as m", Member.class);

        List<Member> resultList = query.getResultList();
        printList(resultList);

        Member singleResult1 = em.createQuery(
                        "select m from Member as m where m.username = :username",
                        Member.class)
                .setParameter("username", "2")
                .getSingleResult();
        printOne(singleResult1);

        TypedQuery<String> query4 = em.createQuery("select m.username from Member as m", String.class);

        Query query100 = em.createQuery("SELECT m.username, m.age from Member m");
    }

}
