package sbnz.integracija.example.model;

import lombok.AllArgsConstructor;
import sbnz.integracija.example.model.enums.Gender;
import sbnz.integracija.example.model.enums.PhysicalActivity;
import sbnz.integracija.example.model.User;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class Patient extends User{
	private int age;
    private Gender gender;
    private PhysicalActivity physicalActivity;
    private int bottomPulse;
    private int upperPulse;
    private List<Therapy> therapies;
    private List<Diagnosis> medicalHistory;

    public Patient() {
    	super();
    }

    public Patient(Long id, String username, String pass, String name,String surname, int age, PhysicalActivity pa) {
        this.id = id;
        this.username = username;
        this.password = pass;
        this.surname = surname;
        this.age = age;
        this.name = name;
        this.bottomPulse = 0;
        this.upperPulse = 0;
        this.physicalActivity = pa;
        this.therapies = new ArrayList<Therapy>();
        this.medicalHistory = new ArrayList<Diagnosis>();
    }

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public PhysicalActivity getPhysicalActivity() {
		return physicalActivity;
	}

	public void setPhysicalActivity(PhysicalActivity physicalActivity) {
		this.physicalActivity = physicalActivity;
	}

	public int getBottomPulse() {
		return bottomPulse;
	}

	public void setBottomPulse(int bottomPulse) {
		this.bottomPulse = bottomPulse;
	}

	public int getUpperPulse() {
		return upperPulse;
	}

	public void setUpperPulse(int upperPulse) {
		this.upperPulse = upperPulse;
	}

	public List<Therapy> getTherapies() {
		return therapies;
	}

	public void setTherapies(List<Therapy> therapies) {
		this.therapies = therapies;
	}

	public List<Diagnosis> getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(List<Diagnosis> medicalHistory) {
		this.medicalHistory = medicalHistory;
	}
}
