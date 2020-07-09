package inheritance.tableperclass;

import inheritance.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Item extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    @Override
    public String toString() {
        return String.format("id : %d | name : %s | price : %d", this.id, this.name, this.price);
    }
}
