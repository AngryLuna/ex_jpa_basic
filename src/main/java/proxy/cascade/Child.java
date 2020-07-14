package proxy.cascade;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "CascadeChild")
@Getter
@Setter
@Table(name = "cascade_child")
public class Child {
    @Id
    @GeneratedValue
    @Column(name = "child_id")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;
}
