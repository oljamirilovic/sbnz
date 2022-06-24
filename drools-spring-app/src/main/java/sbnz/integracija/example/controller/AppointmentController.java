package sbnz.integracija.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sbnz.integracija.example.dto.JmrDTO;
import sbnz.integracija.example.dto.SymptomDTO;
import sbnz.integracija.example.exception.BadRequestException;
import sbnz.integracija.example.exception.NotFoundException;
import sbnz.integracija.example.exception.UserNotFound;
import sbnz.integracija.example.service.AppointmentService;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/appointment")
@Transactional
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/getAllAppointmentsByTherapist/{username}/{searchTerm}")
    @PreAuthorize("hasRole('THERAPIST')")
    public ResponseEntity<?> getAllAppointmentsByTherapist(@PathVariable("username") String username,
                                                           @PathVariable("searchTerm") String searchTerm) {
        try {
            return new ResponseEntity<>(appointmentService.getAllAppointmentsByTherapist(username,searchTerm), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error in getAllAppointmentsByTherapist", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getPatientByAppointmentId/{id}")
    @PreAuthorize("hasRole('THERAPIST')")
    public ResponseEntity<?> getPatientByAppointmentId(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(appointmentService.getPatientByAppointmentId(id), HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity<>("Appointment not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/addSymptomsToAppointment/{id}", consumes = "application/json")
    @PreAuthorize("hasRole('THERAPIST')")
    public ResponseEntity<?> addSymptomsToAppointment(@PathVariable("id") long id, @RequestBody List<SymptomDTO> presentSymptoms){
        try {
            return new ResponseEntity<>(appointmentService.addSymptomsToAppointment(presentSymptoms, id), HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity<>("Appointment not found", HttpStatus.NOT_FOUND);
        }catch (BadRequestException e){
            return new ResponseEntity<>("Symptoms must be added", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/addJmrToAppointment/{id}", consumes = "application/json")
    @PreAuthorize("hasRole('THERAPIST')")
    public ResponseEntity<?> addJmrToAppointment(@PathVariable("id") long id, @RequestBody JmrDTO jmrDTO){
        try {
            return new ResponseEntity<>(appointmentService.addJmrToAppointment(jmrDTO, id), HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity<>("Appointment not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/startForwardChaining/{id}")
    @PreAuthorize("hasRole('THERAPIST')")
    public ResponseEntity<?> startForwardChaining(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(appointmentService.startForwardChaining(id), HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity<>("Appointment not found", HttpStatus.NOT_FOUND);
        }catch (UserNotFound e){
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/isAppointmentResolved/{id}")
    @PreAuthorize("hasRole('THERAPIST')")
    public ResponseEntity<?> isAppointmentResolved(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(appointmentService.isAppointmentResolved(id), HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity<>("Appointment not found", HttpStatus.NOT_FOUND);
        }
    }
}
