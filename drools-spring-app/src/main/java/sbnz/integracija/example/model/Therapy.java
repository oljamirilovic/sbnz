package sbnz.integracija.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sbnz.integracija.example.model.enums.Frequency;
import sbnz.integracija.example.model.enums.TherapyType;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Therapy {
    private int id;
    private int minutes;
    private TherapyType therapyType;
    private Frequency frequency;
    private LocalDate startDate;
    private LocalDate endDate;
}
