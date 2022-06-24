package sbnz.integracija.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbnz.integracija.example.dto.IllnessDTO;
import sbnz.integracija.example.model.Illness;
import sbnz.integracija.example.repository.IllnessRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class IllnessService {

    @Autowired
    private IllnessRepository illnessRepository;

    public List<IllnessDTO> getAll() {
        List<Illness> all = illnessRepository.findAll();
        List<IllnessDTO> ret = new ArrayList<>();
        if (!all.isEmpty()) {
            for (Illness d : all) {
                IllnessDTO dto = new IllnessDTO(d);
                ret.add(dto);
            }
        }
        return ret;
    }
}
