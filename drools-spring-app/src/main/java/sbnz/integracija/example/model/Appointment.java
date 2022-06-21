package sbnz.integracija.example.model;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "appointment")
public class Appointment {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "therapist_id", referencedColumnName = "id")
	private Therapist therapist;

	@Column(name = "date", nullable=false)
    private LocalDate date;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "appointmentSymptoms",
			joinColumns = @JoinColumn(name = "appointment_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "symptom_id", referencedColumnName = "id"))
    private List<Symptom> appointmentSymptoms;

	@OneToOne
	@JoinColumn(name = "diagnosis_id", referencedColumnName = "id")
    private Diagnosis diagnosis;

	@Column(name = "resolved")
    private boolean resolved;


   /* public Appointment(Appointment oldApp, JointMotionRange jmr) {
    	this.therapist = oldApp.therapist;
    	this.appointmentSymptoms = oldApp.appointmentSymptoms;
    	this.diagnosis = oldApp.diagnosis;
    	this.resolved = false;
    	this.jointMotionRange = jmr;
    	this.date = LocalDate.now();
    }*/


	public Appointment(long id, Therapist therapist, LocalDate date, List<Symptom> currentSymptoms,
			boolean resolved) {
		super();
		this.id = id;
		this.therapist = therapist;
		this.date = date;
		this.appointmentSymptoms = currentSymptoms;
		this.diagnosis = null;
		this.resolved = resolved;
		/*this.jointMotionRange = jointMotionRange;*/
	}


}
