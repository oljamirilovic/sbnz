package sbnz.integracija.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sbnz.integracija.example.model.enums.TestType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TestResult {
    private int id;
    private Patient patient;
    private TestType testType;
    private int score; // 1 - no risk, ..., 3 - high risk
    // test inputs
}
