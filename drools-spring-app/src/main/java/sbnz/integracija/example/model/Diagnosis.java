package sbnz.integracija.example.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sbnz.integracija.example.exception.BadRequestException;

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
	@JoinColumn(name = "illness_id", referencedColumnName = "id", nullable = true)
    private Illness illness;

	@OneToMany(fetch = FetchType.LAZY,  cascade= CascadeType.ALL)
	private List<Therapy> therapyList;

	@OneToMany(fetch = FetchType.LAZY,  cascade= CascadeType.ALL)
	private List<JointMotionRange> jointMotionRangeList;

    @OneToOne
	@JoinColumn(name = "test_result_id", referencedColumnName = "id", nullable = true)
    private TestResult testResult;//TODO samo score?

	@ManyToOne
	@JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "appointment_id", referencedColumnName = "id")
    private Appointment appointment;

	public void addTherapy(Therapy t) {
		if (this.therapyList == null || this.therapyList.isEmpty()) {
			this.therapyList = new ArrayList<Therapy>();
		}
		this.therapyList.add(t);
	}

	public void addJMR(JointMotionRange jmr){
		if (this.jointMotionRangeList == null || this.jointMotionRangeList.isEmpty()) {
			this.jointMotionRangeList = new ArrayList<JointMotionRange>();
		}
		this.jointMotionRangeList.add(jmr);
	}

	public double getScoreOfLastJmr(){
		if (this.jointMotionRangeList == null || this.jointMotionRangeList.isEmpty()) {
			throw new BadRequestException("JointMotionRange list is empty");
		}
		JointMotionRange j = this.jointMotionRangeList.get(0);
		for (JointMotionRange jmr: this.jointMotionRangeList) {
			if(j.getDate().isBefore(jmr.getDate())){
				j = jmr;
			}
		}
		return j.getFlexionScore();
	}

	public Therapy getLastTherapy(){
		if (this.therapyList == null || this.therapyList.isEmpty()) {
			throw new BadRequestException("therapyList is empty");
		}
		Therapy j = this.therapyList.get(0);
		for (Therapy jmr: this.therapyList) {
			if(j.getStartDate().isBefore(jmr.getStartDate())){
				j = jmr;
			}
		}
		return j;
	}

	public int calculateScoreForDEXA(double tScore) {
		if(tScore >= -1) {
			return 1;
		}else if(tScore < -1 && tScore > -2.5) {
			return 2;
		}else if(tScore <= -2.5) {
			return 3;
		}
		return 0;
	}

	public int calculateRefBmd(Gender gender, int age) {
		if(gender.equals(Gender.MALE)) {
			if(age<25) {
				return 1072;
			}else if( age>=25 && age<35) {
				return 1055;
			}else if( age>=35 && age<45) {
				return 1038;
			}else if( age>=45 && age<55) {
				return 1002;
			}else if( age>=55 && age<65) {
				return 990;
			}else if( age>=65 && age<75) {
				return 969;
			}else if( age>=75 && age<85) {
				return 928;
			}else if( age>=85) {
				return 859;
			}
		}else {
			if(age<25) {
				return 973;
			}else if( age>=25 && age<35) {
				return 955;
			}else if( age>=35 && age<45) {
				return 945;
			}else if( age>=45 && age<55) {
				return 920;
			}else if( age>=55 && age<65) {
				return 876;
			}else if( age>=65 && age<75) {
				return 809;
			}else if( age>=75 && age<85) {
				return 740;
			}else if( age>=85) {
				return 679;
			}
		}
		return 0;
	}

	public int calculateSD(Gender gender, int age ) {
		if(gender.equals(Gender.MALE)) {
			if(age<25) {
				return 147;
			}else if( age>=25 && age<35) {
				return 146;
			}else if( age>=35 && age<45) {
				return 144;
			}else if( age>=45 && age<55) {
				return 140;
			}else if( age>=55 && age<65) {
				return 143;
			}else if( age>=65 && age<75) {
				return 157;
			}else if( age>=75 && age<85) {
				return 151;
			}else if( age>=85) {
				return 161;
			}
		}else {
			if(age<25) {
				return 120;
			}else if( age>=25 && age<35) {
				return 123;
			}else if( age>=35 && age<45) {
				return 130;
			}else if( age>=45 && age<55) {
				return 136;
			}else if( age>=55 && age<65) {
				return 139;
			}else if( age>=65 && age<75) {
				return 140;
			}else if( age>=75 && age<85) {
				return 129;
			}else if( age>=85) {
				return 135;
			}
		}
		return 0;
	}
}
