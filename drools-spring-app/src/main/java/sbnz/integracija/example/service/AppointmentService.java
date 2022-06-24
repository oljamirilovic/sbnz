package sbnz.integracija.example.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbnz.integracija.example.dto.AppointmentDTO;
import sbnz.integracija.example.dto.JmrDTO;
import sbnz.integracija.example.dto.PatientDTO;
import sbnz.integracija.example.dto.SymptomDTO;
import sbnz.integracija.example.exception.BadRequestException;
import sbnz.integracija.example.exception.NotFoundException;
import sbnz.integracija.example.exception.UserNotFound;
import sbnz.integracija.example.model.*;
import sbnz.integracija.example.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private SymptomService symptomService;

    @Autowired
    private DiagnosisService diagnosisService;

    @Autowired
    private UserService userService;

    @Autowired
    private JmrRepository jmrRepository;

    @Autowired
    private TherapyRepository therapyRepository;

    @Autowired
    private TestResultRepository testResultRepository;

    /*@Autowired
    @Qualifier(value = "rulesSession")
    private KieSession rulesSession;*/

    @Autowired
    private SymptomRepository symptomRepository;

    @Autowired
    private IllnessRepository illnessRepository;

    private final KieContainer kieContainer;

    @Autowired
    public AppointmentService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }


    public List<AppointmentDTO> getAllAppointmentsByTherapist(String username, String searchTerm){
        List<AppointmentDTO> ret = new ArrayList<>();
        List<Appointment> all = new ArrayList<>();
        if(searchTerm != null && !searchTerm.equals("") && !searchTerm.equals("all")) {
            all = this.appointmentRepository.findByTherapistUsernameAndPatient(username, searchTerm);
        }else{
            all = this.appointmentRepository.findByTherapistUsername(username);
        }
        if(!all.isEmpty()) {
            for (Appointment a : all) {
                AppointmentDTO dto = new AppointmentDTO(a);
                ret.add(dto);
            }
        }
        return ret;
    }

    public PatientDTO getPatientByAppointmentId(long id){
        Appointment appointment = appointmentRepository.findById(id);
        if(appointment != null && appointment.getDiagnosis() != null){
            return new PatientDTO(appointment.getDiagnosis().getPatient());
        }
        throw new NotFoundException("Appointment not found.");
    }

    public boolean addSymptomsToAppointment(List<SymptomDTO> symptomDTOS, long appointmentId){
        Optional<Appointment> appointment = Optional.ofNullable(appointmentRepository.findById(appointmentId));
        if(!appointment.isPresent()){
            throw new NotFoundException("Appointment not found");
        }
        if (symptomDTOS.isEmpty()){
            throw new BadRequestException("Symptom list shouldn be empty.");
        }
        List<Symptom> symptoms = new ArrayList<>();
        for (SymptomDTO s:symptomDTOS) {
            Optional<Symptom> symptom = symptomService.findById(s.getId());
            if(!symptom.isPresent()){
                throw new NotFoundException("Symptom not found");
            }
            symptoms.add(symptom.get());
        }
        appointment.get().setAppointmentSymptoms(symptoms);
        appointmentRepository.save(appointment.get());
        return true;
    }

    public boolean addJmrToAppointment(JmrDTO jmrDTO, long appointmentId){
        Optional<Appointment> appointment = Optional.ofNullable(appointmentRepository.findById(appointmentId));
        if(!appointment.isPresent()){
            throw new NotFoundException("Appointment not found");
        }
        Diagnosis diagnosis = diagnosisService.findByAppointmentIdWithJmrs(appointmentId);
        JointMotionRange jointMotionRange = new JointMotionRange();
        jointMotionRange.setDate(LocalDate.now());
        if(jmrDTO.getFlexionScore()!=0){
            jointMotionRange.setFlexionScore(jmrDTO.getFlexionScore());
        }else{
            jointMotionRange.setShoulderFlexion(jmrDTO.getShoulderFlexion());
            jointMotionRange.setKneeFlexion(jmrDTO.getKneeFlexion());
            jointMotionRange.setElbowFlexion(jmrDTO.getElbowFlexion());
            jointMotionRange.setScoreByFlexions(diagnosis.getPatient().getGender());
        }
        //diagnosis.addJMR(jointMotionRange);
        jointMotionRange.setDiagnosis(diagnosis);
        diagnosisService.saveJmr(jointMotionRange);
        return true;
    }

    public String startForwardChaining(long appointmentId){
        Optional<Appointment> appointment = Optional.ofNullable(appointmentRepository.findById(appointmentId));
        if(!appointment.isPresent()){
            throw new NotFoundException("Appointment not found");
        }
        Optional<Diagnosis> diagnosis = diagnosisService.findById(appointment.get().getId());
        if(!diagnosis.isPresent()){
            throw new NotFoundException("Diagnosis not found");
        }
        List<JointMotionRange> jointMotionRangeList = jmrRepository.findAllByDiagnosisId(diagnosis.get().getId());
        if(jointMotionRangeList.isEmpty()){
            diagnosis.get().setJointMotionRangeList(new ArrayList<>());
        }else{
            diagnosis.get().setJointMotionRangeList(jointMotionRangeList);
        }
        List<Therapy> therapies = therapyRepository.findAllByDiagnosisId(diagnosis.get().getId());
        if(therapies.isEmpty()){
            diagnosis.get().setTherapyList(new ArrayList<>());
        }else{
            diagnosis.get().setTherapyList(therapies);
        }

        Optional<Patient> patient = userService.getById(diagnosis.get().getPatient().getId());
        if(!patient.isPresent()){
            throw new UserNotFound("Patient not found");
        }
        List<Diagnosis> medicalHistory = diagnosisService.findByPatientIdAndResolved(patient.get().getId());
        if(medicalHistory.isEmpty()){
            patient.get().setMedicalHistory(new ArrayList<>());
        }else{
            patient.get().setMedicalHistory(medicalHistory);
        }

        KieSession rulesSession = kieContainer.newKieSession("rules_fc");
        rulesSession = setup(rulesSession);

        rulesSession.getAgenda().getAgendaGroup("check-symptoms").setFocus();
        rulesSession.insert(appointment.get());
        rulesSession.insert(diagnosis.get());
        rulesSession.fireAllRules();
        if(diagnosis.get().getIllness() == null){
            return "No illness has been detected.";
        }
        rulesSession.getAgenda().getAgendaGroup("tests").setFocus();
        rulesSession.insert(patient.get());
        rulesSession.fireAllRules();
        if(diagnosis.get().getTestResult() == null){
            return "Tests haven't been done.";
        }
        testResultRepository.save(diagnosis.get().getTestResult());

        rulesSession.getAgenda().getAgendaGroup("therapy").setFocus();
        rulesSession.insert(diagnosis.get().getTestResult());
        rulesSession.fireAllRules();
        if(!appointment.get().isResolved()){
            return "Appointment not resolved";
        }
        diagnosisService.save(diagnosis.get());
        appointmentRepository.save(appointment.get());

        return "Appointment finished!!!!";
    }

    public KieSession setup(KieSession kieSession){
        List<Symptom> symptoms = symptomRepository.getAll();
        for (Symptom symptom : symptoms) {
            kieSession.insert(symptom);
        }

        List<Illness> illnesses = illnessRepository.findAll();
        for (Illness illness : illnesses) {
            kieSession.insert(illness);
        }
        return kieSession;
    }
}
