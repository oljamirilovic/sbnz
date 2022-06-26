package sbnz.integracija.example.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbnz.integracija.example.dto.*;
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
        String message = "";
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
            message += "No illness has been detected.\n";
            rulesSession.dispose();
            return message;
        }else {
            message += "Illness " + diagnosis.get().getIllness().getName() + " has been detected.\n";

            rulesSession.getAgenda().getAgendaGroup("tests").setFocus();
            rulesSession.insert(patient.get());
            rulesSession.fireAllRules();
            if (diagnosis.get().getTestResult() == null) {
                message += "Tests haven't been done.";
                rulesSession.dispose();
                return message;
            }
            else {
                message += "Test for " + diagnosis.get().getIllness().getTestType() + " has been done.\n";
                testResultRepository.save(diagnosis.get().getTestResult());

                rulesSession.getAgenda().getAgendaGroup("therapy").setFocus();
                rulesSession.insert(diagnosis.get().getTestResult());
                rulesSession.fireAllRules();
                if (!appointment.get().isResolved()) {
                    message += "Appointment not resolved.\n No therapy recommended.";
                    rulesSession.dispose();
                    return message;
                }
                else {
                    int mins = diagnosis.get().getLastTherapy().getMinutes();
                    TherapyType type = diagnosis.get().getLastTherapy().getTherapyType();
                    message += "Appointment resolved with therapy: " + type + " " + mins + "minutes .\n";

                    Therapy therapy = diagnosis.get().getLastTherapy();
                    therapy.setDiagnosis(diagnosis.get());
                    therapyRepository.saveAndFlush(therapy);

                    diagnosis.get().setDate(LocalDate.now());
                    diagnosisService.save(diagnosis.get());
                    appointmentRepository.saveAndFlush(appointment.get());

                    rulesSession.dispose();
                    return message;
                }
            }
        }
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

    public boolean isAppointmentResolved(long appointmentId){
        Optional<Appointment> appointment = Optional.ofNullable(appointmentRepository.findById(appointmentId));
        if(!appointment.isPresent()){
            throw new NotFoundException("Appointment not found");
        }
        return appointment.get().isResolved();
    }

    public String newAppointment(NewAppointmentDTO newAppointmentDTO){
        Optional<Patient> patient = userService.getByUsername(newAppointmentDTO.getPatientUsername());
        if(!patient.isPresent()){
            throw new UserNotFound("Patient not found");
        }
        Optional<Therapist>therapist = userService.getTherapistByUsername(newAppointmentDTO.getTherapistUsername());
        if(!therapist.isPresent()){
            throw new NotFoundException("Therapist not found");
        }
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setPatient(patient.get());
        diagnosisService.save(diagnosis);
        Appointment appointment = new Appointment();
        appointment.setTherapist(therapist.get());
        appointment.setDate(newAppointmentDTO.getDate());
        appointment.setDiagnosis(diagnosis);
        appointment.setResolved(false);
        appointmentRepository.save(appointment);
        return "New Appointment created!";
    }
}
