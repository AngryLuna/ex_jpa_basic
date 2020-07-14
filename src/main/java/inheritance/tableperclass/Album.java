package inheritance.tableperclass;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "InheritanceTablePerClassAlbum")
@Getter
@Setter
@Table(name = "inheritance_table_per_class_album")
public class Album extends Item {
    private String artist;

    @Override
    public String toString() {
        return String.format("%s | artist : %s", super.toString(), this.artist);
    }
}
