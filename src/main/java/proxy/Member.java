package proxy;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "proxy_member")
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userName;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public void changeTeam(final Team team) {
        this.team = team;
        this.team.getMembers().add(this);
    }

    @Override
    public String toString() {
        return String.format("[%d] %s", this.id, this.userName);
    }
}
