package ingeritance.joined;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "inheritance_joined_album")
@DiscriminatorValue("A")
public class Album extends Item {
    private String artist;

    @Override
    public String toString() {
        return String.format("%s | artist : %s", super.toString(), this.artist);
    }
}
