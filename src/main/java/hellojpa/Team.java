package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team") // 1:n 관계, TeamMember의 team 필드와 매핑
    private List<TeamMember> members = new ArrayList<TeamMember>();

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<TeamMember> getMembers() {
        return this.members;
    }

    public void setMembers(final List<TeamMember> members) {
        this.members = members;
    }
}
