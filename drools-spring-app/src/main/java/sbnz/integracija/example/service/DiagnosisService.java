package sbnz.integracija.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbnz.integracija.example.dto.DiagnosisDTO;
import sbnz.integracija.example.model.Diagnosis;
import sbnz.integracija.example.repository.DiagnosisRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiagnosisService {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    public List<DiagnosisDTO> getAllByPatientUsername(String username){
        List<Diagnosis> all = this.diagnosisRepository.findByUsername(username);
        List<DiagnosisDTO> ret = new ArrayList<>();
        if(!all.isEmpty()) {
            for (Diagnosis d : all) {
                DiagnosisDTO dto = new DiagnosisDTO(d);
                ret.add(dto);
            }
        }
        return ret;
    }
}
