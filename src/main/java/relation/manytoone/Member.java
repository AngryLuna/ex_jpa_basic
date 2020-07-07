package relation.manytoone;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "member_mto")
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userName;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    /**
     * 팀 변경 편의 메서드
     *
     * @param team
     */
    public void changeTeam(final Team team) {
        setTeam(team);
        this.team.getMembers().add(this);
    }

    @Override
    public String toString() {
        return String.format("[%d] %s", this.id, this.userName);
    }
}
