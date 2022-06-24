package sbnz.integracija.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sbnz.integracija.example.model.Illness;

@Getter
@Setter
@NoArgsConstructor
public class IllnessDTO {
    private long id;
    private String name;

    public IllnessDTO(Illness illness){
        this.id = illness.getId();
        this.name = illness.getName();
    }
}
