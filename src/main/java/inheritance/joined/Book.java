package inheritance.joined;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "InheritanceJoinedBook")
@Getter
@Setter
@Table(name = "inheritance_joined_book")
@DiscriminatorValue("B")
public class Book extends Item {
    private String author;

    private String isbn;

    @Override
    public String toString() {
        return String.format("%s | author : %s | isbn : %s", super.toString(), this.author, this.isbn);
    }
}
