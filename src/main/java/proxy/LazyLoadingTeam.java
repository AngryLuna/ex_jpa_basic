package proxy;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "ProxyLazyLoadingTeam")
@Getter
@Setter
@Table(name = "proxy_lazy_loading_team")
public class LazyLoadingTeam {
    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<LazyLoadingMember> members = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("[%d] %s", this.id, this.name);
    }
}
