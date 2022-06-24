package sbnz.integracija.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
