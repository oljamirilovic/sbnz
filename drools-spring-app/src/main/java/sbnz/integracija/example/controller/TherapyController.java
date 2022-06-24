package sbnz.integracija.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sbnz.integracija.example.service.TherapyService;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/therapy")
@Transactional
public class TherapyController {

    @Autowired
    private TherapyService therapyService;

    @GetMapping("/getAllTherapiesByDiagnosisId/{id}")
    @PreAuthorize("hasAnyRole('THERAPIST', 'PATIENT')")
    public ResponseEntity<?> getAllTherapiesByDiagnosisId(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(therapyService.getAllTherapiesByDiagnosisId(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error in getAllTherapiesByDiagnosisId", HttpStatus.BAD_REQUEST);
        }
    }
}
