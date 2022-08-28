package sbnz.integracija.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sbnz.integracija.example.model.Therapist;

@Getter
@Setter
@NoArgsConstructor
public class TherapistDTO {
    public String username;
    public String firstName;
    public String lastName;
    public boolean deleted;

    public TherapistDTO(Therapist p){
        this.username = p.getUsername();
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.deleted = p.isDeleted();
    }
}
