package sbnz.integracija.example.model;

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
@Table(name = "test_result")
public class TestResult {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "score")
    private int score; //0-not set, 1 - no risk, ..., 3 - high risk

    public void calculateScoreForDEXA(double tScore) {
    	if(tScore >= -1) {
    		this.score = 1;
    	}else if(tScore < -1 && tScore > -2.5) {
    		this.score = 2;
    	}else if(tScore <= -2.5) {
    		this.score = 3;
    	}
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
