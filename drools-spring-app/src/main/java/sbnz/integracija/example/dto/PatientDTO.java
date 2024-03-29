package sbnz.integracija.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sbnz.integracija.example.model.Patient;

@Getter
@Setter
@NoArgsConstructor
public class PatientDTO {
    public String username;
    public String firstName;
    public String lastName;
    public int age;
    public String gender;
    public double bmd;
    public String pa;
    public boolean deleted;

    public PatientDTO(Patient p){
        this.username = p.getUsername();
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.age = p.getAge();
        this.gender = p.getGender().name();
        this.bmd = p.getBmd();
        this.pa = p.getPhysicalActivity().name();
        this.deleted = p.isDeleted();
    }
}
