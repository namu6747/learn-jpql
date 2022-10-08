import jpql.Member;
import jpql.Order;
import jpql.Product;
import jpql.Team;

import javax.persistence.EntityManager;
import java.util.List;

public abstract class BASE {

    public static <T extends List> void printList(T obj) {
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

    public static <T> void printOne(T obj) {
        if (obj != null) {
            String clazz = obj.getClass().getName();
            System.out.println("===== clazz = " + clazz);
            System.out.println(obj.getClass() + " = " + obj);
            System.out.println("===== clazz = " + clazz);
        }
        System.out.println();
    }

    public static void dataInit(EntityManager em) {
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
}
