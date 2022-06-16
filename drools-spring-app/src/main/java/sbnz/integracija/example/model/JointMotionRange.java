package sbnz.integracija.example.model;

import java.time.LocalDate;

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
@Table(name = "joint_motion_range")
public class JointMotionRange {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "date", nullable=false)
	private LocalDate date;

	@Column(name = "elbowFlexion")
	private double elbowFlexion;

	@Column(name = "kneeFlexion")
	private double kneeFlexion;

	@Column(name = "shoulderFlexion")
	private double shoulderFlexion;

	public int getScoreByFlexions(Gender gender) {
		int score = 0;
		if(gender.equals(Gender.FEMALE)) {
			if(kneeFlexion < 137 && shoulderFlexion < 168 && elbowFlexion < 148) {
				score = 1;
			}else if((kneeFlexion >= 137 && kneeFlexion < 142 )
					&& (shoulderFlexion >= 168 && shoulderFlexion < 172 )
					&& (elbowFlexion >= 148 && elbowFlexion < 150.1 )) {
				score = 2;
			}else if((kneeFlexion >= 140 && kneeFlexion <= 143.8 )
					&& (shoulderFlexion >= 169.9 && shoulderFlexion <= 173.8 )
					&& (elbowFlexion >= 148.5 && elbowFlexion <= 150.9 )) {
				score = 3;
			}
		}else {
			if(kneeFlexion < 136.5 && shoulderFlexion < 167.3 && elbowFlexion < 143.6) {
				score = 1;
			}else if((kneeFlexion >= 136.5 && kneeFlexion < 138.9 )
					&& (shoulderFlexion >= 167.3 && shoulderFlexion < 170.3 )
					&& (elbowFlexion >= 143.6 && elbowFlexion < 145.6 )) {
				score = 2;
			}else if((kneeFlexion >= 140.4 && kneeFlexion <= 144.4 )
					&& (shoulderFlexion >= 169.1 && shoulderFlexion <= 172.7 )
					&& (elbowFlexion >= 146.8 && elbowFlexion <= 149.8 )) {
				score = 3;
			}
		}
		return score;
	}

}
