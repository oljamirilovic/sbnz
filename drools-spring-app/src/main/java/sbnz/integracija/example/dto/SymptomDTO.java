package sbnz.integracija.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sbnz.integracija.example.model.Symptom;

@Getter
@Setter
@NoArgsConstructor
public class SymptomDTO {
    private long id;
    private String name;

    public SymptomDTO(Symptom s){
        this.id = s.getId();
        this.name = s.getName();
    }
}
