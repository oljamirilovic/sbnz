package sbnz.integracija.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sbnz.integracija.example.model.Appointment;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class AppointmentDTO {
    private long id;
    private String date;
    private String patientUsername;
    private String patientFN;
    private String patientLN;
    private boolean resolved;

    public AppointmentDTO(Appointment a){
        this.id = a.getId();
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.date = formatters.format(a.getDate());
        this.patientUsername = a.getDiagnosis().getPatient().getUsername();
        this.patientFN = a.getDiagnosis().getPatient().getFirstName();
        this.patientLN = a.getDiagnosis().getPatient().getLastName();
        this.resolved = a.isResolved();
    }
}
