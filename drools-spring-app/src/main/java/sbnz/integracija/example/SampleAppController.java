package sbnz.integracija.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sbnz.integracija.example.model.Patient;
import sbnz.integracija.example.model.PhysicalActivity;

@RestController
public class SampleAppController {
	private static Logger log = LoggerFactory.getLogger(SampleAppController.class);

	private final SampleAppService sampleService;

	@Autowired
	public SampleAppController(SampleAppService sampleService) {
		this.sampleService = sampleService;
	}

	/*@RequestMapping(value = "/item", method = RequestMethod.GET, produces = "application/json")
	public Item getQuestions(@RequestParam(required = true) String id, @RequestParam(required = true) String name,
			@RequestParam(required = true) double cost, @RequestParam(required = true) double salePrice) {

		Item newItem = new Item(Long.parseLong(id), name, cost, salePrice);

		log.debug("Item request received for: " + newItem);

		Item i2 = sampleService.getClassifiedItem(newItem);

		return i2;
	}*/

	@RequestMapping(value = "/patient", method = RequestMethod.GET, produces = "application/json")
	public Patient getInfo(@RequestParam(required = true) String id, @RequestParam(required = true) String username,
			@RequestParam(required = true) String password, @RequestParam(required = true) String name,
			@RequestParam(required = true) String surname, @RequestParam(required = true) int age,
			@RequestParam(required = true) String physicalActivity
			) {
		/*PhysicalActivity ps = PhysicalActivity.SEDENTARY;
		if(physicalActivity == 0) {
			ps = PhysicalActivity.SEDENTARY;
		}else if(physicalActivity == 1) {
			ps = PhysicalActivity.MODERATE;
		}else {
			ps = PhysicalActivity.VERY_ACTIVE;
		}*/
		log.debug("pa:" + PhysicalActivity.valueOf(physicalActivity).toString());

		Patient newItem = new Patient(Long.parseLong(id), username, password, name, surname, age, PhysicalActivity.valueOf(physicalActivity));

		log.debug("Patient request received for: " + newItem);

		Patient i2 = sampleService.getPulseLimits(newItem);

		return i2;
	}



}
