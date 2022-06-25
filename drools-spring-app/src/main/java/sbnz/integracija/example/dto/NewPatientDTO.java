package sbnz.integracija.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewPatientDTO {
    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public int age;
    public String gender;
    public int bmd;
    public String pa;
    public String p1;
    public String p2;
}
