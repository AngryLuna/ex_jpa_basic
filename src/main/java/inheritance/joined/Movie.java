package inheritance.joined;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "InheritanceJoinedMovie")
@Getter
@Setter
@Table(name = "inheritance_joined_movie")
@DiscriminatorValue("M")
public class Movie extends Item {
    private String director;

    private String actor;

    @Override
    public String toString() {
        return String.format("%s | director : %s | actor : %s", super.toString(), this.director, this.actor);
    }
}
