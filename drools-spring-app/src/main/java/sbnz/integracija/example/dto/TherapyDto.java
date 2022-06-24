package sbnz.integracija.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sbnz.integracija.example.model.Therapy;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class TherapyDto {
    private long id;
    private String startDate;
    private String endDate;
    private String type;
    private int minutes;

    public TherapyDto(Therapy therapy){
        this.id = therapy.getId();
        this.minutes = therapy.getMinutes();
        this.type = therapy.getTherapyType().name();
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.startDate = formatters.format(therapy.getStartDate());
        if(therapy.getEndDate() != null){
            this.endDate = formatters.format(therapy.getEndDate());
        }else{
            this.endDate = "";
        }
    }
}
