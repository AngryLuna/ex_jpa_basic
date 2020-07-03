package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
//@SequenceGenerator(name = "MEMBER_SEQ_GENERATOR", sequenceName = "MEMBER_SEQ", initialValue = 100, allocationSize = 1)
@TableGenerator(name = "MEMBER_SEQ_GENERATOR", table = "MY_SEQUENCES", pkColumnValue = "MEMBER_SEQ", allocationSize = 1)
public class Member {
    @Id // PK
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

    @Column(name = "name", // 컬럼명은 name으로 사용
            updatable = false, // 수정 불가
            nullable = false, // null 허용하지 않음
            unique = true, // unique 제약 조건 (제약 조건명은 알아서 생성됨)
            length = 100) // 문자 길이 제한
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING) // enum 타입의 이름을 DB에 저장
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP) // TIMESTAMP 형식 날짜
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP) // TIMESTAMP 형식 날짜
    private Date lastModifiedDate;

    @Lob // 길이 제한없는 데이터
    private String description;

    @Transient // 매핑 안함
    private String temp;

    public Member() {
    }

    public Member(final Long id, final String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return this.roleType;
    }

    public void setRoleType(final RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(final Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
