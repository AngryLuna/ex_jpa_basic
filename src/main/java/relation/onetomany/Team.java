package relation.onetomany;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "one_to_many_team")
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name = "team_id") // 일대다 관계에서 JoinColumn 사용하지 않으면 중간 테이블이 생성됨
    private List<Member> members = new ArrayList<Member>();

    /**
     * 팀원 추가 편의 메서드
     *
     * @param members
     */
    public void addMembers(final Member... members) {
        for (final Member member : members) {
            this.members.add(member);
            member.setTeam(this);
        }
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (%d)", this.id, this.name, this.members.size());
    }
}
