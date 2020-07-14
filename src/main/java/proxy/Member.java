package proxy;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "ProxyMember")
@Getter
@Setter
@Table(name = "proxy_member")
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userName;

    @Override
    public String toString() {
        return String.format("[%d] %s", this.id, this.userName);
    }
}
