package relation.onetoone;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "RelationOneToOneMember")
@Getter
@Setter
@Table(name = "one_to_one_member")
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userName;

    @OneToOne
    @JoinColumn(name = "locker_id")
    private Locker locker;

    /**
     * 락커 변경 편의 메서드
     *
     * @param locker
     */
    public void changeLocker(final Locker locker) {
        this.locker = locker;
        this.locker.setMember(this);
    }

    @Override
    public String toString() {
        return String.format("[%d] %s", this.id, this.userName);
    }
}
