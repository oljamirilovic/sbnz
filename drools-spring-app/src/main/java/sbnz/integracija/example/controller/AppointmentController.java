package sbnz.integracija.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sbnz.integracija.example.exception.NotFoundException;
import sbnz.integracija.example.service.AppointmentService;

@RestController
@RequestMapping("/api/appointment")
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
}
