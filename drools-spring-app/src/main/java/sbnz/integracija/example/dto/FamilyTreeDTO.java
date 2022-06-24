package sbnz.integracija.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FamilyTreeDTO {
    private String child;
    private List<FamilyTreeDTO> parents;

}
