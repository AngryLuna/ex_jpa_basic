package hellojpa;

import javax.persistence.*;

@Entity
public class TeamMember {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username")
    private String userName;

//    @Column(name = "team_id")
//    private Long teamId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(final Team team) {
        this.team = team;
        this.team.getMembers().add(this);
    }
}
