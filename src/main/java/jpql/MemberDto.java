package jpql;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

    private Long id;

    private String userName;

    private int age;

    public MemberDto(final String userName, final int age) {
        this.userName = userName;
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (%d)", this.id, this.userName, this.age);
    }
}
