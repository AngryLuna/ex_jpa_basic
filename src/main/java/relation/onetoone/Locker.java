package relation.onetoone;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "one_to_one_locker")
public class Locker {
    @Id
    @GeneratedValue
    @Column(name = "locker_id")
    private Long id;

    private String name;

    @OneToOne(mappedBy = "locker")
    private Member member;

    @Override
    public String toString() {
        return String.format("[%d] %s", this.id, this.name);
    }
}
