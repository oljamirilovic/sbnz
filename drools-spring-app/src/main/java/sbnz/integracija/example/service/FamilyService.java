package sbnz.integracija.example.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbnz.integracija.example.dto.FamilyTreeDTO;
import sbnz.integracija.example.exception.NotFoundException;
import sbnz.integracija.example.model.*;
import sbnz.integracija.example.repository.DiagnosisRepository;
import sbnz.integracija.example.repository.FamilyRepository;
import sbnz.integracija.example.repository.IllnessRepository;
import sbnz.integracija.example.repository.PatientRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class FamilyService {

    private final KieContainer kieContainer;

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Autowired
    private IllnessRepository illnessRepository;

    @Autowired
    public FamilyService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    public FamilyTreeDTO getAllByChildUsername(String username) {
        List<Family> all = familyRepository.findAllByChildUsername(username);
        if (all.isEmpty()) {
            throw new NotFoundException("No family found");
        }
        FamilyTreeDTO familyTreeDTO = new FamilyTreeDTO();
        familyTreeDTO.setChild(username);

        List<FamilyTreeDTO> familyTreeDTOS = hasParents1(username);
        if(familyTreeDTOS.isEmpty()){
            familyTreeDTO.setParents(new ArrayList<>());
        }else{
            familyTreeDTO.setParents(familyTreeDTOS);
        }
        return familyTreeDTO;
    }

    public List<FamilyTreeDTO> hasParents1(String childUsername) {
        List<Family> parentsAll = familyRepository.findAllByChildUsername(childUsername);
        List<FamilyTreeDTO> parents = new ArrayList<>();
        if (!parentsAll.isEmpty()) {
            for (Family f : parentsAll) {
                FamilyTreeDTO d = new FamilyTreeDTO();
                d.setChild(f.getParent());
                List<FamilyTreeDTO> newList = hasParents1(f.getParent());
                if(newList.isEmpty()){
                    d.setParents(new ArrayList<>());
                }else{
                    d.setParents(newList);
                }
                parents.add(d);
            }
        }
        return parents;
    }

    public String startBackwardChaining(String childUsername, String illnessName) {
        FamilyHistory familyHistory = new FamilyHistory(childUsername, illnessName, false);

        KieSession rulesSession = kieContainer.newKieSession("rules_bc");
        rulesSession = setup(rulesSession);

        rulesSession.insert(familyHistory);
        rulesSession.fireAllRules();

        if (!familyHistory.isHasIllness()) {
            return "NO illness " + illnessName + " in family tree";
        } else {
            return "FOUND illness " + illnessName + " in family tree";
        }
    }

    public KieSession setup(KieSession kieSession) {
        List<Family> families = familyRepository.findAll();
        for (Family family : families) {
            kieSession.insert(family);
        }

        List<Patient> patients = patientRepository.findAll();
        for (Patient patient : patients) {
            List<Diagnosis> medHistory = diagnosisRepository.findDiagnosisByPatientId(patient.getId());
            patient.setMedicalHistory(medHistory);
            kieSession.insert(patient);
        }

        List<Diagnosis> diagnoses = diagnosisRepository.findAll();
        for (Diagnosis diagnosis : diagnoses) {
            kieSession.insert(diagnosis);
        }

        List<Illness> illnesses = illnessRepository.findAll();
        for (Illness illness : illnesses) {
            kieSession.insert(illness);
        }

        return kieSession;
    }
}
