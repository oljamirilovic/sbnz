package sbnz.integracija.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbnz.integracija.example.dto.SymptomDTO;
import sbnz.integracija.example.model.Symptom;
import sbnz.integracija.example.repository.SymptomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SymptomService {
    @Autowired
    private SymptomRepository symptomRepository;

    public List<Symptom> findAll(){
        return symptomRepository.findAll();
    }

    public Optional<Symptom> findById(long id){
        return this.symptomRepository.findById(id);
    }

    public List<SymptomDTO> getAllSymptoms(String searchTerm){
        List<SymptomDTO> ret = new ArrayList<>();
        List<Symptom> all = new ArrayList<>();
        if(searchTerm != null && !searchTerm.equals("") && !searchTerm.equals("all")) {
            all = this.symptomRepository.findAllByName(searchTerm);
        }else{
            all = this.symptomRepository.findAll();
        }
        if(!all.isEmpty()) {
            for (Symptom a : all) {
                SymptomDTO dto = new SymptomDTO(a);
                ret.add(dto);
            }
        }
        return ret;
    }

}
