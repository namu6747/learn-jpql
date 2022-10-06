package jpql;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {

    private String city;
    private String street;
    private String zipcode;

    public static Address getInstance(){
        String str = Sequence.getString();
        return new Address(str, str, str);
    }

}
