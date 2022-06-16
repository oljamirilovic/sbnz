package sbnz.integracija.example.model;

public enum PhysicalActivity {
    SEDENTARY  (1),
	MODERATE   (2),
	VERY_ACTIVE (3),
	;

	private final int activityValue;

	PhysicalActivity(int activityValue) {
	    this.activityValue = activityValue;
	}

	public int getActivityValue() {
	    return this.activityValue;
	}
}
