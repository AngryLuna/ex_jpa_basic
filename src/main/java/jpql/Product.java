package jpql;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int price;

    @Column(name = "stock_amount")
    private int stockAmount;

    @Override
    public String toString() {
        return String.format("[%d] %s (%d)", this.id, this.name, this.price);
    }
}
