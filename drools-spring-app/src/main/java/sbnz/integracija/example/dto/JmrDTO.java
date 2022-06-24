package sbnz.integracija.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JmrDTO {
    private double elbowFlexion;
    private double kneeFlexion;
    private double shoulderFlexion;
    private double flexionScore;

}
