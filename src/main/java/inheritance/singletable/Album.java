package inheritance.singletable;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue("A")
public class Album extends Item {
    private String artist;

    @Override
    public String toString() {
        return String.format("%s | artist : %s", super.toString(), this.artist);
    }
}
