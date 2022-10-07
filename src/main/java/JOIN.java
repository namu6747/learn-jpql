import jpql.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

public class JOIN {

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
            //caseQuery();

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


            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

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


    private static void dataInit() {
        for (int i = 0; i < 5; i++) {
            Team team = Team.getInstance();
            Member member = Member.getInstance(team);
            Member member2 = Member.getInstance(team);
            member2.setUsername(null);
            Product product = Product.getInstance();
            Order order = Order.getInstance(member, product);
            Order order2 = Order.getInstance(member2, product);

            em.persist(team);
            em.persist(member);
            em.persist(member2);
            em.persist(product);
            em.persist(order);
            em.persist(order2);
        }

        em.flush();
        em.clear();
    }

    private static <T extends List> void printList(T obj) {
        if (obj.size() > 0 && obj.get(0) instanceof Object[]) {
            System.out.println("object[]");
            for (Object o : obj) {
                Object[] list = (Object[]) o;
                if (list.length == 0) {
                    System.out.println("none");
                    return;
                }
                for (Object object : list) {
                    System.out.println(object.getClass() + " = " + object);
                }
            }
            return;
        }

        if (obj != null && obj.size() > 0) {
            String clazz = obj.get(0).getClass().getName();
            System.out.println("===== clazz = " + clazz);
            for (Object o : obj) {
                String shoot = (o != null) ? o.getClass() + " = " + o : null;
                System.out.println(shoot);
            }
            System.out.println("===== clazz = " + clazz);
        }
        System.out.println();
    }

    private static <T> void printOne(T obj) {
        if (obj != null) {
            String clazz = obj.getClass().getName();
            System.out.println("===== clazz = " + clazz);
            System.out.println(obj.getClass() + " = " + obj);
            System.out.println("===== clazz = " + clazz);
        }
        System.out.println();
    }

}
