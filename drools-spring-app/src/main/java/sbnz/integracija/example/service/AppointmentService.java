package sbnz.integracija.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbnz.integracija.example.dto.AppointmentDTO;
import sbnz.integracija.example.dto.PatientDTO;
import sbnz.integracija.example.exception.NotFoundException;
import sbnz.integracija.example.model.Appointment;
import sbnz.integracija.example.repository.AppointmentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

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
}
