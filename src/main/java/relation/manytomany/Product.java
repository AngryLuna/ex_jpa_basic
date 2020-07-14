package relation.manytomany;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "RelationManyToManyProduct")
@Getter
@Setter
@Table(name = "many_to_many_product")
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "products")
    private List<Member> members = new ArrayList<Member>();

    @Override
    public String toString() {
        return String.format("[%d] %s (%d)", this.id, this.name, this.members.size());
    }
}
