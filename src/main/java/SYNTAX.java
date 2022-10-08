import jpql.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;

public class SYNTAX extends BASE{

    // alt + shift + insert -> shift up/down, alt + j
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    private static EntityManager em = emf.createEntityManager();
    private static EntityTransaction tx = em.getTransaction();

    public static void main(String[] args) {


        tx.begin();

        try {

            //caseQuery();
            //functionQuery();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }

    private static void functionQuery() {
        String query = "select concat('a','b') || 'c' from Member m";
        String query2 = "select substring(m.username, 1, 1) from Member m ";
        String query3 = "select locate('de', 'abcdefghj') from Member m";
        String query4 = "select size(t.members) from Team t";
        String query5 = "select function('group_concat', m.username) from Member m ";
        String query6 = "select group_concat(m.username) from Member m ";

        List resultList = em.createQuery(query).getResultList();
        List resultList1 = em.createQuery(query2).getResultList();
        List resultList2 = em.createQuery(query3).getResultList();
        List resultList3 = em.createQuery(query4).getResultList();
        List resultList4 = em.createQuery(query5).getResultList();
        List resultList5 = em.createQuery(query6).getResultList();

        printList(resultList);
        printList(resultList1);
        printList(resultList2);
        printList(resultList3);
        printList(resultList4);
        printList(resultList5);
    }

    private static void caseQuery() {
        String query =
                "select " +
                        "case when m.age <= 20 then '급식' " +
                        "     when m.age >= 20 then '학식' " +
                        "     else '인간' end " +
                        "from Member m";
        String query2 = "select NULLIF(m.username, '15') from Member m";
        String query3 = "select coalesce(m.username, 'coal') from Member m";

        List resultList = em.createQuery(query).getResultList();
        List<String> resultList1 = em.createQuery(query2,String.class).getResultList();
        List<String> resultList2 = em.createQuery(query3,String.class).getResultList();

        printList(resultList);
        printList(resultList1);
        printList(resultList2);
    }

}
