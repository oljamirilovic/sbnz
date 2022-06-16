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
@Table(name = "symptom")
public class Symptom {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "illnessSymptoms")
    private List<Illness> illnesses;

    @ManyToMany(mappedBy = "appointmentSymptoms")
    private List<Appointment> appointments;
}
