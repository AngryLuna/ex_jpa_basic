package ingeritance.joined;

import ingeritance.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "inheritance_joined_item")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class Item extends BaseEntity {
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
