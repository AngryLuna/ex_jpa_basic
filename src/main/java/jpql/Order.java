package jpql;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "orders")
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "order_amount")
    private int orderAmount;

    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public String toString() {
        return String.format("[%d] %s (%d)", this.id, this.address.toString(), this.orderAmount);
    }
}
