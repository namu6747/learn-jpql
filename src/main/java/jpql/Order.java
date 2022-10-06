package jpql;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ORDERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id @GeneratedValue
    private Long id;
    private int orderAmount;

    @Embedded
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public Order(int orderAmount, Address address, Member member, Product product) {
        this.orderAmount = orderAmount;
        this.address = address;
        this.member = member;
        this.product = product;
    }

    public static Order getInstance(Member member, Product product){
        String str = Sequence.getString();
        int i = Sequence.getInt();
        Address addr = Address.getInstance();
        Order result = new Order(i, addr, member, product);
        member.getOrders().add(result);
        return result;
    }

}
