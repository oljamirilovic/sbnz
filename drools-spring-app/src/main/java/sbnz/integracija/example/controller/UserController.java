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
import sbnz.integracija.example.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAllPatients/{searchTerm}")
    @PreAuthorize("hasRole('THERAPIST')")
    public ResponseEntity<?> getAllPatients(@PathVariable("searchTerm") String searchTerm) {
        try {
            return new ResponseEntity<>(userService.getAllPatients(searchTerm), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error in getAllPatients", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getPatient/{username}")
    @PreAuthorize("hasRole('THERAPIST')")
    public ResponseEntity<?> getPatient(@PathVariable("username") String username) {
        try {
            return new ResponseEntity<>(userService.getPatient(username), HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }
    }
}
