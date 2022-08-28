package sbnz.integracija.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sbnz.integracija.example.dto.NewPatientDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.InheritanceType.JOINED;

@Getter
@Setter
@Entity
@Table(name = "therapist")
@Inheritance(strategy=JOINED)
public class Therapist extends User{

    @OneToMany(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    private List<Appointment> appointments;

    public Therapist() {
    	super();
    }

    public Therapist(Long id, String username, String pass, String name,String surname) {
        super(id, username, pass, name, surname);
        this.appointments = new ArrayList<Appointment>();
    }

    public Therapist(NewPatientDTO dto){
        super(dto.getUsername(), dto.getPassword(), dto.getFirstName(), dto.getLastName());
        this.appointments = new ArrayList<Appointment>();
    }
}
