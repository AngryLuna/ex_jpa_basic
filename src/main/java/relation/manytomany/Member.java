package relation.manytomany;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "RelationManyToManyMember")
@Getter
@Setter
@Table(name = "many_to_many_member")
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userName;

    @ManyToMany
    @JoinTable(name = "many_to_many_member_product") // 다대다 연관관계 설정시 조인용 테이블을 생성
    private List<Product> products = new ArrayList<Product>();

    /**
     * 상품 추가 편의 메서드
     *
     * @param products
     */
    public void addProducts(final Product... products) {
        for (final Product product : products) {
            this.products.add(product);
            product.getMembers().add(this);
        }
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (%d)", this.id, this.userName, this.products.size());
    }
}
