package sbnz.integracija.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbnz.integracija.example.model.Appointment;
import sbnz.integracija.example.model.Diagnosis;
import sbnz.integracija.example.model.Illness;
import sbnz.integracija.example.model.JointMotionRange;
import sbnz.integracija.example.model.Patient;
import sbnz.integracija.example.model.TestResult;
import sbnz.integracija.example.model.Therapist;
import sbnz.integracija.example.model.Therapy;
import sbnz.integracija.example.model.enums.*;

@Service
public class SampleAppService {

	private static Logger log = LoggerFactory.getLogger(SampleAppService.class);

	private final KieContainer kieContainer;

	@Autowired
	public SampleAppService(KieContainer kieContainer) {
		log.info("Initialising a new example session.");
		this.kieContainer = kieContainer;
	}

	public Patient getPulseLimits(Patient i) {
		KieSession kieSession = kieContainer.newKieSession();
		kieSession.insert(i);
		kieSession.fireAllRules();
		kieSession.dispose();
		return i;
	}

	public void setDB() {
		/*KieSession kSession = kieContainer.newKieSession();

        Patient p1 = new Patient(1L, "username1", "pass1", "name1", "surname1", 50, PhysicalActivity.VERY_ACTIVE, Gender.FEMALE);
        Patient p2 = new Patient(2L, "username2", "pass2", "name2", "surname2", 60, PhysicalActivity.MODERATE, Gender.FEMALE);

        Therapist t1 = new Therapist(3L, "Tusername1", "Tpass1", "Tname1", "Tsurname1");
        Therapist t2 = new Therapist(4L, "Tusername2", "Tpass2", "Tname2", "Tsurname2");

        List<Symptom> symptoms1 = new ArrayList<Symptom>();
        symptoms1.add(Symptom.BAD_POSTURE);
        symptoms1.add(Symptom.HEIGHT_LOSS);
        symptoms1.add(Symptom.HORMONE_INTAKE);
        symptoms1.add(Symptom.SMOKER);
        symptoms1.add(Symptom.EARLY_MENOPAUSE);
        symptoms1.add(Symptom.BACK_PAIN);
        symptoms1.add(Symptom.BONE_FRACTURE);

        List<Symptom> symptoms2 = new ArrayList<Symptom>();
        symptoms1.add(Symptom.BAD_POSTURE);
        symptoms1.add(Symptom.HORMONE_INTAKE);
        symptoms1.add(Symptom.SMOKER);
        symptoms1.add(Symptom.EARLY_MENOPAUSE);
        symptoms1.add(Symptom.BACK_PAIN);*/

        /*Illness i1 = new Illness("osteoporoza", symptoms1, TestType.BONE_DENSITY);
        Illness i2 = new Illness("osteopenija", symptoms2, TestType.BONE_DENSITY);*/

        /*List<Symptom> symptoms3 = new ArrayList<Symptom>();
        symptoms1.add(Symptom.HEIGHT_LOSS);
        symptoms1.add(Symptom.HORMONE_INTAKE);
        symptoms1.add(Symptom.EARLY_MENOPAUSE);
        symptoms1.add(Symptom.BACK_PAIN);
        symptoms1.add(Symptom.BONE_FRACTURE);*/

        //TestResult testResult = new TestResult(1L, i1, p1, 3);
        /*JointMotionRange jointMotionRange = new JointMotionRange();
        Appointment a1 = new Appointment(1L, t1, LocalDate.now().minusDays(14), symptoms3,true, jointMotionRange) ;
        Diagnosis d1 = new Diagnosis(1L, LocalDate.now().minusDays(14), i1, testResult, p1, a1);*/

        /*kSession.insert(p1);
        kSession.insert(p2);

        kSession.insert(t1);
        kSession.insert(t1);

        kSession.insert(i1);
        kSession.insert(i2);

        kSession.fireAllRules();*/
	}
}
