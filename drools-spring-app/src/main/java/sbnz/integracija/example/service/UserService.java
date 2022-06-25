package sbnz.integracija.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sbnz.integracija.example.dto.NewPatientDTO;
import sbnz.integracija.example.dto.PatientDTO;
import sbnz.integracija.example.exception.ForbiddenException;
import sbnz.integracija.example.exception.NotFoundException;
import sbnz.integracija.example.exception.UserNotFound;
import sbnz.integracija.example.model.Family;
import sbnz.integracija.example.model.Patient;
import sbnz.integracija.example.model.Therapist;
import sbnz.integracija.example.model.UserRole;
import sbnz.integracija.example.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private TherapistRepository therapistRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public Patient getPatientWithMedicalHistoryById(long id){
        return patientRepository.getPatientWithMedicalHistoryById(id);
    }

    public Optional<Patient> getById(long id){
        return patientRepository.findById(id);
    }

    public Optional<Patient> getByUsername(String id){
        return patientRepository.findByUsername(id);
    }

    public Optional<Therapist> getTherapistByUsername(String username){return therapistRepository.findByUsername(username);}

    public PatientDTO getPatient(String username){
        Optional<Patient> p = this.patientRepository.findByUsername(username);
        if(p.isPresent()){
            return new PatientDTO(p.get());
        }
        throw new NotFoundException("Patient not found");
    }

    public List<PatientDTO> getAllPatients(String searchTerm){
        List<PatientDTO> ret = new ArrayList<>();
        List<Patient> all = new ArrayList<>();
        if(searchTerm != null && !searchTerm.equals("") && !searchTerm.equals("all")) {
            all = this.patientRepository.findAllBySearchTerm(searchTerm);
        }else{
            all = this.patientRepository.findAll();
        }
        if(!all.isEmpty()) {
            for (Patient user : all) {
                PatientDTO dto = new PatientDTO(user);
                ret.add(dto);
            }
        }
        return ret;
    }

    public String newPatient(NewPatientDTO newPatientDTO){
        if(patientRepository.findByUsername(newPatientDTO.getUsername()).isPresent()){
            throw new ForbiddenException("Username already exists");
        }
        if(newPatientDTO.getP1() != null && !newPatientDTO.getP1().equals("")
                && !getByUsername(newPatientDTO.getP1()).isPresent()){
            throw new UserNotFound("Parent1 not found");
        }
        if(newPatientDTO.getP2() != null && !newPatientDTO.getP2().equals("")
                && !getByUsername(newPatientDTO.getP2()).isPresent()){
            throw new NotFoundException("Parent2 not found");
        }

        Patient patient = new Patient(newPatientDTO);
        patient.setPassword(passwordEncoder.encode(newPatientDTO.getPassword()));
        patient.setRole(userRoleRepository.findByName("PATIENT").get());
        this.patientRepository.saveAndFlush(patient);

        if(newPatientDTO.getP1() != null && !newPatientDTO.getP1().equals("")){
            Family f1 = new Family(newPatientDTO.getUsername(), newPatientDTO.getP1());
            this.familyRepository.saveAndFlush(f1);
        }
        if(newPatientDTO.getP2() != null && !newPatientDTO.getP2().equals("")) {
            Family f2 = new Family(newPatientDTO.getUsername(), newPatientDTO.getP2());
            this.familyRepository.saveAndFlush(f2);
        }

        return "New patient created";
    }
}
