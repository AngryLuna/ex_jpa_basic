package relation.onetomany;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "one_to_many_member")
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userName;

    @ManyToOne
    @JoinColumn(name = "team_id", insertable = false, updatable = false) // 읽기 전용으로 설정해서 양방향 처럼 사용
    private Team team;

    @Override
    public String toString() {
        return String.format("[%d] %s", this.id, this.userName);
    }
}
