package inheritance.tableperclass;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "inheritance_table_per_class_book")
public class Book extends Item {
    private String author;

    private String isbn;

    @Override
    public String toString() {
        return String.format("%s | author : %s | isbn : %s", super.toString(), this.author, this.isbn);
    }
}
