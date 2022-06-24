package sbnz.integracija.example.configKie;

import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.context.annotation.SessionScope;
import sbnz.integracija.example.model.Illness;
import sbnz.integracija.example.model.Patient;
import sbnz.integracija.example.model.Symptom;
import sbnz.integracija.example.model.Therapist;
import sbnz.integracija.example.repository.IllnessRepository;
import sbnz.integracija.example.repository.PatientRepository;
import sbnz.integracija.example.repository.SymptomRepository;
import sbnz.integracija.example.repository.TherapistRepository;
/*
@Configuration
public class KieConfiguration {

	@Autowired
	private KieSessionHolder kieSessionHolder;

	@Autowired
	private SymptomRepository symptomRepository;

	@Autowired
	private IllnessRepository illnessRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private TherapistRepository therapistRepository;

	@Bean
	public KieContainer kieContainer() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
				.newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(10_000);
		return kContainer;
	}

	@Bean(name = "rulesSession")
	//@SessionScope
	public KieSession kieSession() {
		KieSession kieSession = this.kieContainer().newKieSession("rulesSession");
		//kieSession.setGlobal("chosenPatientUsername", "");
		System.out.println("Creating new kie session");

		List<Symptom> symptoms = symptomRepository.getAll();
		for (Symptom symptom : symptoms) {
			kieSession.insert(symptom);
		}

		List<Illness> illnesses = illnessRepository.findAll();
		for (Illness illness : illnesses) {
			kieSession.insert(illness);
		}

		List<Patient> patients = patientRepository.findAll();
		for (Patient patient : patients) {
			kieSession.insert(patient);
		}

		List<Therapist> therapists = therapistRepository.findAll();
		for (Therapist therapist : therapists) {
			kieSession.insert(therapist);
		}

		//this.kieSessionHolder.add(kieSession);
		return kieSession;
	}
}
*/
