package sbnz.integracija.example.model;

public class ChosenValues {
    public long chosenTherapyId;
    public double newJmrScore;
    public boolean patientRequested;

    public ChosenValues() {
    }

    public ChosenValues(long chosenTherapyId, double newJmrScore, boolean patientRequested) {
        this.chosenTherapyId = chosenTherapyId;
        this.newJmrScore = newJmrScore;
        this.patientRequested = patientRequested;
    }

    public long getChosenTherapyId() {
        return chosenTherapyId;
    }

    public void setChosenTherapyId(long chosenTherapyId) {
        this.chosenTherapyId = chosenTherapyId;
    }

    public double getNewJmrScore() {
        return newJmrScore;
    }

    public void setNewJmrScore(double newJmrScore) {
        this.newJmrScore = newJmrScore;
    }

    public boolean isPatientRequested() {
        return patientRequested;
    }

    public void setPatientRequested(boolean patientRequested) {
        this.patientRequested = patientRequested;
    }
}
