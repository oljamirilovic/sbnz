package sbnz.integracija.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class NewAppointmentDTO {
    private LocalDate date;
    private String patientUsername;
    private String therapistUsername;
}
