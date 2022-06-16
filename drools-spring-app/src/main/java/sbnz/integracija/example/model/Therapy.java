package sbnz.integracija.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "therapy")
public class Therapy {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "minutes")
    private int minutes;

	@Column(name = "therapyType", nullable = false)
	@Enumerated(EnumType.STRING)
    private TherapyType therapyType;

	@Column(name = "startDate")
    private LocalDate startDate;

	@Column(name = "endDate")
    private LocalDate endDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "diagnosis_id", referencedColumnName = "id")
    private Diagnosis diagnosis;

    public Therapy(TherapyType type, int mins) {
    	this.startDate = LocalDate.now();
    	this.minutes = mins;
    	this.therapyType = type;
    }

}
