package sbnz.integracija.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbnz.integracija.example.dto.DiagnosisDTO;
import sbnz.integracija.example.exception.UserNotFound;
import sbnz.integracija.example.model.Appointment;
import sbnz.integracija.example.model.Diagnosis;
import sbnz.integracija.example.model.JointMotionRange;
import sbnz.integracija.example.model.Patient;
import sbnz.integracija.example.repository.AppointmentRepository;
import sbnz.integracija.example.repository.DiagnosisRepository;
import sbnz.integracija.example.repository.JmrRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DiagnosisService {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Autowired
    private JmrRepository jmrRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Diagnosis> findByPatientIdAndResolved(long id){
        return appointmentRepository.findDiagnosisByPatientIdAndResolved(id);
    }

    public Diagnosis findByAppointmentIdWithJmrs(long id){
        return appointmentRepository.findDiagnosisByAppointmentIdWithJmrs(id);
    }

    public Optional<Diagnosis> findById(long id){
        return diagnosisRepository.findById(id);
    }

    public void save(Diagnosis diagnosis){
        diagnosisRepository.save(diagnosis);
    }

    public void saveJmr(JointMotionRange jointMotionRange){
        jmrRepository.save(jointMotionRange);
    }

    public List<DiagnosisDTO> getAllByPatientUsername(String username){
        Optional<Patient> patient = userService.getByUsername(username);
        if(!patient.isPresent()){
            throw new UserNotFound("Patient not found");
        }
        List<Appointment> all = this.appointmentRepository.findByPatientIdAndResolved(patient.get().getId());
        List<DiagnosisDTO> ret = new ArrayList<>();
        if(!all.isEmpty()) {
            for (Appointment d : all) {
                if(d.getDiagnosis()!=null) {
                    DiagnosisDTO dto = new DiagnosisDTO(d.getDiagnosis());
                    ret.add(dto);
                }
            }
        }
        return ret;
    }
}
