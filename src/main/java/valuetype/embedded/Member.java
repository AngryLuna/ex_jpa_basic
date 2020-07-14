package valuetype.embedded;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "ValueTypeEmbeddedMember")
@Getter
@Setter
@Table(name = "value_type_embedded_member")
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String username;

    @Embedded
    private Period workPeriod;

    @Embedded
    @AttributeOverrides({ // 다수의 임베디드 타입을 사용하는 경우 컬럼명 중복으로 인한 에러 발생, 컬럼명 오버라이딩 필요
            @AttributeOverride(name = "city", column = @Column(name = "home_city")),
            @AttributeOverride(name = "street", column = @Column(name = "home_street")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "home_zip_code"))
    })
    private Address homeAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "work_city")),
            @AttributeOverride(name = "street", column = @Column(name = "work_street")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "work_zip_code"))
    })
    private Address workAddress;
}
