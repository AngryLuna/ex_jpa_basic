package proxy.cascade;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "CascadeParent")
@Getter
@Setter
@Table(name = "cascade_parent")
public class Parent {
    @Id
    @GeneratedValue
    @Column(name = "parent_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> children = new ArrayList<>();

    public void addChild(final Child child) {
        this.children.add(child);
        child.setParent(this);
    }
}
