package sbnz.integracija.example.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbnz.integracija.example.dto.DiagnosisDTO;
import sbnz.integracija.example.dto.JmrDTO;
import sbnz.integracija.example.exception.NotFoundException;
import sbnz.integracija.example.exception.UserNotFound;
import sbnz.integracija.example.model.*;
import sbnz.integracija.example.repository.AppointmentRepository;
import sbnz.integracija.example.repository.DiagnosisRepository;
import sbnz.integracija.example.repository.JmrRepository;

import java.time.LocalDate;
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

    @Autowired
    private TherapyService therapyService;

    private final KieContainer kieContainer;

    @Autowired
    public DiagnosisService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

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
                    Diagnosis dig = d.getDiagnosis();
                    dig.setAppointment(d);
                    DiagnosisDTO dto = new DiagnosisDTO(d.getDiagnosis());
                    ret.add(dto);
                }
            }
        }
        return ret;
    }

    public String checkNewTherapyAvailable(JmrDTO jmrDTO, long diagnosisId){
        Optional<Diagnosis> diagnosis = findById(diagnosisId);
        if(!diagnosis.isPresent()){
            throw new NotFoundException("Diagnosis not found");
        }
        List<JointMotionRange> jmrs = jmrRepository.findAllByDiagnosisId(diagnosisId);
        diagnosis.get().setJointMotionRangeList(jmrs);

        JointMotionRange newJmr = new JointMotionRange();
        newJmr.setDate(LocalDate.now());
        if(jmrDTO.getFlexionScore() != 0){
            newJmr.setFlexionScore(jmrDTO.getFlexionScore());
        }else{
            newJmr = new JointMotionRange(jmrDTO.getElbowFlexion(), jmrDTO.getKneeFlexion(), jmrDTO.getShoulderFlexion());
            newJmr.setScoreByFlexions(diagnosis.get().getPatient().getGender());
        }

        Therapy therapy = therapyService.findActiveTherapyByDiagnosisId(diagnosisId);
        Appointment appointment = appointmentRepository.findByDiagnosisId(diagnosisId);
        if(therapy == null){
            appointment.setResolved(false);
            appointmentRepository.saveAndFlush(appointment);

            return therapyService.newTherapy(appointment,diagnosis.get());
        }else{
            ChosenValues chosenValues = new ChosenValues(therapy.getId(), newJmr.getFlexionScore(), false);

            KieSession rulesSession = kieContainer.newKieSession("rules_endtherapy");
            rulesSession.insert(therapy);
            rulesSession = setup(rulesSession);
            rulesSession.insert(chosenValues);
            rulesSession.fireAllRules();

            if(therapy.isResolved()){
                therapyService.save(therapy);
                appointmentRepository.saveAndFlush(appointment);

                rulesSession.dispose();
                return therapyService.newTherapy(appointment,diagnosis.get());
            }else{
                rulesSession.dispose();
                return "Continue current therapy";
            }
        }
    }

    public KieSession setup(KieSession rulesSession){

        List<Appointment> appointments = appointmentRepository.findAll();
        for (Appointment a: appointments) {
            rulesSession.insert(a);
        }

        List<Diagnosis> diagnoses = diagnosisRepository.findAll();
        for (Diagnosis diagnosis : diagnoses) {
            List<Therapy> therapies = therapyService.findAllByDiagnosisId(diagnosis.getId());
            List<JointMotionRange> jmr = jmrRepository.findAllByDiagnosisId(diagnosis.getId());
            diagnosis.setTherapyList(therapies);
            diagnosis.setJointMotionRangeList(jmr);
            rulesSession.insert(diagnosis);
        }

        List<Therapy> therapies = therapyService.findAll();
        for (Therapy therapy : therapies) {
            rulesSession.insert(therapy);
        }

        return rulesSession;
    }
}
