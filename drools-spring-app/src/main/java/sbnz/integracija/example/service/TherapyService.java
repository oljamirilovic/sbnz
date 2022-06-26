package sbnz.integracija.example.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbnz.integracija.example.dto.TherapyDto;
import sbnz.integracija.example.model.*;
import sbnz.integracija.example.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TherapyService {

    @Autowired
    private TherapyRepository therapyRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Autowired
    private IllnessRepository illnessRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private JmrRepository jmrRepository;

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private SymptomRepository symptomRepository;

    private final KieContainer kieContainer;

    @Autowired
    public TherapyService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    public List<Therapy> findAllByDiagnosisId(long id){
        return therapyRepository.findAllByDiagnosisId(id);
    }

    public void save(Therapy therapy){ this.therapyRepository.saveAndFlush(therapy);}

    public List<Therapy> findAll(){
        return therapyRepository.findAll();
    }

    public List<TherapyDto> getAllTherapiesByDiagnosisId(long id){
        List<Therapy> therapies = therapyRepository.findAllByDiagnosisId(id);
        List<TherapyDto> ret = new ArrayList<>();
        if(!therapies.isEmpty()) {
            for (Therapy d : therapies) {
                ret.add(new TherapyDto(d));
            }
        }
        return ret;
    }

    public Therapy findActiveTherapyByDiagnosisId(long id){
        List<Therapy> therapies = therapyRepository.findAllByDiagnosisId(id);
        for (Therapy t: therapies) {
            if(t.getEndDate() == null){
                return t;
            }
        }
        return null;
    }

    public String newTherapy(Appointment appointment, Diagnosis diagnosis){
        //TODO test newTherapy
        String message="";
        KieSession rulesSession = kieContainer.newKieSession("rules_fc");
        rulesSession = setup(rulesSession);

        rulesSession.getAgenda().getAgendaGroup("therapy").setFocus();
        rulesSession.fireAllRules();
        if (!appointment.isResolved()) {
            message += "No therapy assigned";
            appointment.setResolved(true);
            appointmentRepository.save(appointment);
            rulesSession.dispose();
            return message;
        }
        else {
            int mins = diagnosis.getLastTherapy().getMinutes();
            TherapyType type = diagnosis.getLastTherapy().getTherapyType();
            message += "Assigned new therapy: " + type + " " + mins + "minutes .\n";

            Therapy therapy = diagnosis.getLastTherapy();
            therapy.setDiagnosis(diagnosis);
            therapyRepository.saveAndFlush(therapy);

            diagnosisRepository.save(diagnosis);
            appointmentRepository.save(appointment);

            rulesSession.dispose();
            return message;
        }
    }

    public KieSession setup(KieSession rulesSession){
        List<TestResult> testResults = testResultRepository.findAll();
        for (TestResult a: testResults) {
            rulesSession.insert(a);
        }

        List<Appointment> appointments = appointmentRepository.findAll();
        for (Appointment a: appointments) {
            rulesSession.insert(a);
        }

        List<Illness> illnesses = illnessRepository.findAllWithSymptomList();
        for (Illness illness : illnesses) {
            rulesSession.insert(illness);
        }

        List<Symptom> symptoms = symptomRepository.getAll();
        for (Symptom symptom : symptoms) {
            rulesSession.insert(symptom);
        }

        List<Diagnosis> diagnoses = diagnosisRepository.findAll();
        for (Diagnosis diagnosis : diagnoses) {
            List<Therapy> therapies = therapyRepository.findAllByDiagnosisId(diagnosis.getId());
            List<JointMotionRange> jmr = jmrRepository.findAllByDiagnosisId(diagnosis.getId());
            diagnosis.setTherapyList(therapies);
            diagnosis.setJointMotionRangeList(jmr);
            rulesSession.insert(diagnosis);
        }

        List<Patient> patients = patientRepository.findAll();
        for (Patient patient : patients) {
            List<Diagnosis> medHistory = diagnosisRepository.findDiagnosisByPatientId(patient.getId());
            patient.setMedicalHistory(medHistory);
            rulesSession.insert(patient);
        }

        List<Therapy> therapies = therapyRepository.findAll();
        for (Therapy therapy : therapies) {
            rulesSession.insert(therapy);
        }

        return rulesSession;
    }
}
