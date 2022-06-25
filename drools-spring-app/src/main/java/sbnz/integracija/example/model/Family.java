package sbnz.integracija.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kie.api.definition.type.Position;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "family")
public class Family {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Position(0)
    private String child; //username

    @Position(1)
    private String parent;//username

    public Family(String child, String parent){
        this.child = child;
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Family that = (Family) o;

        if (parent != null ? !parent.equals(that.parent) : that.parent != null) { return false; }
        if (child != null ? !child.equals(that.child) : that.child != null) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        int result = parent != null ? parent.hashCode() : 0;
        result = 31 * result + (child != null ? child.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ParentChild{" +
                "parent='" + parent + '\'' +
                ", child='" + child + '\'' +
                '}';
    }

}
