package sbnz.integracija.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sbnz.integracija.example.service.IllnessService;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/illness")
@Transactional
public class IllnessController {

    @Autowired
    private IllnessService illnessService;

    @GetMapping("/getAllIllnesses")
    @PreAuthorize("hasRole('THERAPIST')")
    public ResponseEntity<?> getAllIllnesses() {
        try {
            return new ResponseEntity<>(illnessService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error in getAllPatients", HttpStatus.BAD_REQUEST);
        }
    }
}
