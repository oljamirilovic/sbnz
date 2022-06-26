package sbnz.integracija.example.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbnz.integracija.example.dto.PatientDTO;
import sbnz.integracija.example.model.*;
import sbnz.integracija.example.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    private TherapyRepository therapyRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private SymptomRepository symptomRepository;

    private final KieContainer kieContainer;

    @Autowired
    public PatientService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    public List<PatientDTO> getAllPatientsWithTherapiesWithMinDaysPast() {
        List<PatientDTO> patientDTOS = new ArrayList<>();
        patientDTOS = filter("getAllPatientsWithTherapiesWithMinDaysPast", null);
        return patientDTOS;
    }

    public List<PatientDTO> getAllPatientsWithTherapiesWithMinDaysPastByTherapyType(TherapyType type) {
        List<PatientDTO> patientDTOS = new ArrayList<>();
        patientDTOS = filter("getAllPatientsWithTherapiesWithMinDaysPastByTherapyType", type);
        return patientDTOS;
    }

    public List<PatientDTO> getAllPotentiallyAbusedPatients() {
        List<PatientDTO> patientDTOS = new ArrayList<>();
        patientDTOS = filter("getAllPotentiallyAbusedPatients", null);
        return patientDTOS;
    }

    public List<PatientDTO> filter( String query, TherapyType type) {
        KieSession rulesSession = kieContainer.newKieSession("rules_reports");
        rulesSession = setup(rulesSession);

        QueryResults results = null;
        if(query.equals("getAllPatientsWithTherapiesWithMinDaysPast")){
            results = rulesSession.getQueryResults("getAllPatientsWithTherapiesWithMinDaysPast");
        }
        else if (query.equals("getAllPotentiallyAbusedPatients")){
            results = rulesSession.getQueryResults("getAllPotentiallyAbusedPatients");
        }
        else{
            results = rulesSession.getQueryResults("getAllPotentiallyAbusedPatients", type);
        }
        List<Patient> patientList = new ArrayList<>();

        for (QueryResultsRow row : results) {
            patientList = (List<Patient>) row.get("$patients");
        }

        List<PatientDTO> patientDTOS = new ArrayList<>();
        if(!patientList.isEmpty()){
            for (Patient user : patientList) {
                patientDTOS.add(new PatientDTO(user));
            }
        }
        return patientDTOS;
    }


    public KieSession setup(KieSession rulesSession){

        List<Appointment> appointments = appointmentRepository.findAllWithSymptoms();
        for (Appointment a: appointments) {
            rulesSession.insert(a);
        }

        List<Symptom> symptoms = symptomRepository.getAll();
        for (Symptom symptom : symptoms) {
            rulesSession.insert(symptom);
        }

        List<Diagnosis> diagnoses = diagnosisRepository.findAll();
        for (Diagnosis diagnosis : diagnoses) {
            List<Therapy> therapies = therapyRepository.findAllByDiagnosisId(diagnosis.getId());
            Appointment a = appointmentRepository.findByDiagnosisId(diagnosis.getId());
            diagnosis.setTherapyList(therapies);
            diagnosis.setAppointment(a);
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
