package sbnz.integracija.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sbnz.integracija.example.dto.NewPatientDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.InheritanceType.JOINED;

@Getter
@Setter
@Entity
@Table(name = "patient")
@Inheritance(strategy=JOINED)
public class Patient extends User{

    @Column(name = "age")
	private int age;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "bmd")
    private double bmd;

    @Column(name = "physical_activity", nullable = false)
    @Enumerated(EnumType.STRING)
    private PhysicalActivity physicalActivity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Diagnosis> medicalHistory;


    public Patient() {
    	super();
    }

    public Patient(Long id, String username, String pass, String name,String surname, int age, PhysicalActivity pa) {
        super(id, username, pass, name, surname);
        this.age = age;
        this.physicalActivity = pa;
        this.bmd = 0;
        this.medicalHistory = new ArrayList<Diagnosis>();
    }

    public Patient(Long id, String username, String pass, String name,String surname, int age, PhysicalActivity pa, Gender g) {
        super(id, username, pass, name, surname);
        this.age = age;
        this.gender = g;
        this.physicalActivity = pa;
        this.bmd = 0;
        this.medicalHistory = new ArrayList<Diagnosis>();
    }

    public Patient(NewPatientDTO dto){
        super(dto.getUsername(), dto.getPassword(), dto.getFirstName(), dto.getLastName());
        this.age = dto.getAge();
        this.gender = Gender.valueOf(dto.getGender());
        this.physicalActivity = PhysicalActivity.valueOf(dto.getPa());
        this.bmd = dto.getBmd();
        this.medicalHistory = new ArrayList<Diagnosis>();
    }

    public void addMedicalHistory(Diagnosis d) {
    	if(!this.medicalHistory.contains(d)) {
    		this.medicalHistory.add(d);
    	}
    }

    public boolean hadIllness(Illness illness){
        if(this.getMedicalHistory() != null && !this.getMedicalHistory().isEmpty()){
            for (Diagnosis d: this.getMedicalHistory()) {
                if(d.getIllness()!=null && d.getIllness().getName().equals(illness.getName())){
                    return true;
                }
            }
        }
        return false;
    }
}
