package ingeritance.tableperclass;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "inheritance_table_per_class_movie")
public class Movie extends Item {
    private String director;

    private String actor;

    @Override
    public String toString() {
        return String.format("%s | director : %s | actor : %s", super.toString(), this.director, this.actor);
    }
}
