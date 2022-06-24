package sbnz.integracija.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbnz.integracija.example.dto.TherapyDto;
import sbnz.integracija.example.model.Therapy;
import sbnz.integracija.example.repository.TherapyRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TherapyService {

    @Autowired
    private TherapyRepository therapyRepository;

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
}
