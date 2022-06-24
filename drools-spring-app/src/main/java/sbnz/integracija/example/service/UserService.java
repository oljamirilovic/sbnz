package sbnz.integracija.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbnz.integracija.example.dto.PatientDTO;
import sbnz.integracija.example.exception.NotFoundException;
import sbnz.integracija.example.model.Patient;
import sbnz.integracija.example.repository.PatientRepository;
import sbnz.integracija.example.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    public Patient getPatientWithMedicalHistoryById(long id){
        return patientRepository.getPatientWithMedicalHistoryById(id);
    }

    public Optional<Patient> getById(long id){
        return patientRepository.findById(id);
    }

    public Optional<Patient> getByUsername(String id){
        return patientRepository.findByUsername(id);
    }

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
}
