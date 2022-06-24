package sbnz.integracija.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sbnz.integracija.example.model.Diagnosis;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class DiagnosisDTO {
    private long id;
    private String date;
    private String illness;
    private String therapistUsername;

    public DiagnosisDTO(Diagnosis d){
        this.id = d.getId();
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.date = formatters.format(d.getDate());
        this.illness = d.getIllness().getName();
        this.therapistUsername = d.getAppointment().getTherapist().getUsername();
    }
}
