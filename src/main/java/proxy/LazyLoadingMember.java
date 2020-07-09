package proxy;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "proxy_lazy_loading_member")
public class LazyLoadingMember {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private LazyLoadingTeam team;

    @Override
    public String toString() {
        return String.format("[%d] %s", this.id, this.userName);
    }
}
