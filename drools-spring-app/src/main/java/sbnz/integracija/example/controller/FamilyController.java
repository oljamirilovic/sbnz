package sbnz.integracija.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sbnz.integracija.example.SampleApp;
import sbnz.integracija.example.exception.NotFoundException;
import sbnz.integracija.example.service.FamilyService;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/family")
@Transactional
public class FamilyController {

    private static Logger log = LoggerFactory.getLogger(SampleApp.class);


    @Autowired
    private FamilyService familyService;

    @GetMapping("/getAllByChildUsername/{username}")
    @PreAuthorize("hasRole('THERAPIST')")
    public ResponseEntity<?> getAllByChildUsername(@PathVariable("username") String username) {
        try {
            return new ResponseEntity<>(familyService.getAllByChildUsername(username), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>("No family found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/startBackwardChaining/{username}/{illness}")
    @PreAuthorize("hasRole('THERAPIST')")
    public ResponseEntity<?> startBackwardChaining(@PathVariable("username") String username,
                                                   @PathVariable("illness") String illness) {
        try {
            return new ResponseEntity<>(familyService.startBackwardChaining(username, illness), HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("startBackwardChaining error", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getParentsByChildUsername/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getParentsByChildUsername(@PathVariable("username") String username) {
        try {
            return new ResponseEntity<>(familyService.getParentsByChildUsername(username), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>("No parents found", HttpStatus.NOT_FOUND);
        }
    }
}
