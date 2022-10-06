package jpql;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private Integer price;
    private Integer stockAmount;

    public Product(String name, Integer price, Integer stockAmount) {
        this.name = name;
        this.price = price;
        this.stockAmount = stockAmount;
    }

    public static Product getInstance(){
        String str = Sequence.getString();
        int i = Sequence.getInt();
        return new Product(str, i*100, i*10);
    }

}
