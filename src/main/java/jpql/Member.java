package jpql;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@NoArgsConstructor
@Getter
@AllArgsConstructor
@ToString(exclude = "orders")
public class Member {

    @Id @GeneratedValue
    private Long id;
    @Column(name = "NAME")
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        this.team = team;
    }

    public static Member getInstance(Team team){
        String str = Sequence.getString();
        int i = Sequence.getInt();
        Member result = new Member(str, i, team);
        team.getMembers().add(result);
        return result;
    }

}
