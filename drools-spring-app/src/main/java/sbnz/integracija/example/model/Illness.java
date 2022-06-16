package sbnz.integracija.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "illness")
public class Illness {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", unique = true)
	private String name;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "illnessSymptoms",
			joinColumns = @JoinColumn(name = "illness_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "symptom_id", referencedColumnName = "id"))
    private List<Symptom> illnessSymptoms;

	@Column(name = "testType", nullable = false)
	@Enumerated(EnumType.STRING)
    private TestType testType;

	@OneToMany(fetch = FetchType.LAZY,  cascade= CascadeType.ALL)
	private List<Diagnosis> diagnosis;

}
