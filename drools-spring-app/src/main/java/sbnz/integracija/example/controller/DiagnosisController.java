package sbnz.integracija.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sbnz.integracija.example.dto.JmrDTO;
import sbnz.integracija.example.exception.NotFoundException;
import sbnz.integracija.example.service.DiagnosisService;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/diagnosis")
@Transactional
public class DiagnosisController {

    @Autowired
    private DiagnosisService diagnosisService;

    @GetMapping("/getAllByPatientUsername/{username}")
    @PreAuthorize("hasAnyRole('THERAPIST', 'PATIENT')")
    public ResponseEntity<?> getAllDiagnosisByPatientUsername(@PathVariable("username") String username) {
        try {
            return new ResponseEntity<>(diagnosisService.getAllByPatientUsername(username), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error in getAllDiagnosisByPatientUsername", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/checkNewTherapyAvailable/{id}", consumes = "application/json")
    @PreAuthorize("hasRole('THERAPIST')")
    public ResponseEntity<?> checkNewTherapyAvailable(@PathVariable("id") long id, @RequestBody JmrDTO jmrDTO){
        try {
            return new ResponseEntity<>(diagnosisService.checkNewTherapyAvailable(jmrDTO, id), HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity<>("Diagnosis not found", HttpStatus.NOT_FOUND);
        }
    }
}
