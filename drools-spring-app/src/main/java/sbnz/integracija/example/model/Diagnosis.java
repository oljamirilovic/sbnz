package sbnz.integracija.example.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "diagnosis")
public class Diagnosis {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "date")
    private LocalDate date;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "illness_id", referencedColumnName = "id")
    private Illness illness;

	@OneToMany(fetch = FetchType.LAZY,  cascade= CascadeType.ALL)
	private List<Therapy> therapyList;

	@OneToMany(fetch = FetchType.LAZY,  cascade= CascadeType.ALL)
	private List<JointMotionRange> jointMotionRangeList;

    @OneToOne
    private TestResult testResult;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

	@OneToOne
    private Appointment appointment;

	public void addTherapy(Therapy t) {
		if (this.therapyList == null || this.therapyList.isEmpty()) {
			this.therapyList = new ArrayList<Therapy>();
		}
		this.therapyList.add(t);
	}


}
