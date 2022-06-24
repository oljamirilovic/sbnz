package sbnz.integracija.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sbnz.integracija.example.model.Family;

@Setter
@Getter
@NoArgsConstructor
public class FamilyDTO {
    private long id;
    private String child;
    private String parent;

    public FamilyDTO(Family family){
        this.id = family.getId();
        this.child = family.getChild();
        this.parent = family.getParent();
    }
}
