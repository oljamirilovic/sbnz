package sbnz.integracija.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sbnz.integracija.example.service.SymptomService;

@RestController
@RequestMapping("/api/symptom")
public class SymptomController {

    @Autowired
    private SymptomService symptomService;

    @GetMapping("/getAllSymptoms/{searchTerm}")
    @PreAuthorize("hasRole('THERAPIST')")
    public ResponseEntity<?> getAllSymptoms(@PathVariable("searchTerm") String searchTerm) {
        try {
            return new ResponseEntity<>(symptomService.getAllSymptoms(searchTerm), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error in getAllSymptoms", HttpStatus.BAD_REQUEST);
        }
    }
}
