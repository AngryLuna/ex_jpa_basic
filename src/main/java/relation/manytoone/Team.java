package relation.manytoone;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "RelationManyToOneTeam")
@Getter
@Setter
@Table(name = "many_to_one_team")
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<Member>();

    @Override
    public String toString() {
        return String.format("[%d] %s (%d)", this.id, this.name, this.members.size());
    }
}
