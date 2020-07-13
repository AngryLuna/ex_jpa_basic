package valuetype.collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String city;

    private String street;

    @Column(name = "zip_code")
    private String zipCode;

    @Override
    public String toString() {
        return String.format("%s | %s | %s", this.city, this.street, this.zipCode);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        } else {
            final Address other = (Address) obj;

            return Objects.equals(this.city, other.city) &&
                    Objects.equals(this.street, other.street) &&
                    Objects.equals(this.zipCode, other.zipCode);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.city, this.street, this.zipCode);
    }
}
