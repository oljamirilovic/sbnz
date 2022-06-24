package sbnz.integracija.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "test_result")
public class TestResult {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "score")
    private int score; //0-not set, 1 - no risk, ..., 3 - high risk

	public TestResult(int score){
		this.score = score;
	}



}
