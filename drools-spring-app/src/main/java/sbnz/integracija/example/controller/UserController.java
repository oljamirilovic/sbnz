package sbnz.integracija.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sbnz.integracija.example.dto.NewAppointmentDTO;
import sbnz.integracija.example.dto.NewPatientDTO;
import sbnz.integracija.example.exception.ForbiddenException;
import sbnz.integracija.example.exception.NotFoundException;
import sbnz.integracija.example.exception.UserNotFound;
import sbnz.integracija.example.model.TherapyType;
import sbnz.integracija.example.service.PatientService;
import sbnz.integracija.example.service.UserService;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/users")
@Transactional
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PatientService patientService;

    @GetMapping("/getAllPatients/{searchTerm}")
    @PreAuthorize("hasAnyRole('THERAPIST', 'ADMIN')")
    public ResponseEntity<?> getAllPatients(@PathVariable("searchTerm") String searchTerm) {
        try {
            return new ResponseEntity<>(userService.getAllPatients(searchTerm), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error in getAllPatients", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getPatient/{username}")
    @PreAuthorize("hasAnyRole('THERAPIST', 'ADMIN')")
    public ResponseEntity<?> getPatient(@PathVariable("username") String username) {
        try {
            return new ResponseEntity<>(userService.getPatient(username), HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/newPatient", consumes = "application/json")
    @PreAuthorize("hasRole('THERAPIST')")
    public ResponseEntity<?> newPatient(@RequestBody NewPatientDTO newPatientDTO){
        try {
            return new ResponseEntity<>(userService.newPatient(newPatientDTO), HttpStatus.OK);
        }catch (UserNotFound e){
            return new ResponseEntity<>("Parent1 not found", HttpStatus.NOT_FOUND);
        }catch (ForbiddenException e){
            return new ResponseEntity<>("Username already exists", HttpStatus.FORBIDDEN);
        }catch (NotFoundException e){
            return new ResponseEntity<>("Parent not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/resolvableTherapies")
    @PreAuthorize("hasRole('THERAPIST')")
    public ResponseEntity<?> resolvableTherapies() {
        try {
            return new ResponseEntity<>(patientService.getAllPatientsWithTherapiesWithMinDaysPast(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error in resolvableTherapies", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/potentialAbuse")
    @PreAuthorize("hasRole('THERAPIST')")
    public ResponseEntity<?> potentialAbuse() {
        try {
            return new ResponseEntity<>(patientService.getAllPotentiallyAbusedPatients(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error in potentialAbuse", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/resolvableTherapiesByType/{type}")
    @PreAuthorize("hasRole('THERAPIST')")
    public ResponseEntity<?> resolvableTherapiesByType(@PathVariable("type") String type) {
        try {
            return new ResponseEntity<>(patientService.
                    getAllPatientsWithTherapiesWithMinDaysPastByTherapyType(TherapyType.valueOf(type)), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error in resolvableTherapiesByType", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/deletePatient/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletePatient(@PathVariable("username") String username) {
        try {
            return new ResponseEntity<>(userService.deletePatient(username), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error in deletePatient", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/newTherapist", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> newTherapist(@RequestBody NewPatientDTO newPatientDTO){
        try {
            return new ResponseEntity<>(userService.newPatient(newPatientDTO), HttpStatus.OK);
        }catch (ForbiddenException e){
            return new ResponseEntity<>("Username already exists", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/getAllTherapists/{searchTerm}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllTherapists(@PathVariable("searchTerm") String searchTerm) {
        try {
            return new ResponseEntity<>(userService.getAllTherapists(searchTerm), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error in getAllTherapists", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getTherapist/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getTherapist(@PathVariable("username") String username) {
        try {
            return new ResponseEntity<>(userService.getTherapist(username), HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity<>("Therapist not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/deleteTherapist/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTherapist(@PathVariable("username") String username) {
        try {
            return new ResponseEntity<>(userService.deleteTherapist(username), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error in deleteTherapist", HttpStatus.BAD_REQUEST);
        }
    }
}
