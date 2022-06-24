package sbnz.integracija.example.model;

public class FamilyHistory {
    private String patientUsername;
    private String searchedIllness;
    private boolean hasIllness;

    public FamilyHistory() {
    }

    public FamilyHistory(String patientUsername, String searchedIllness, boolean hasIllness) {
        this.patientUsername = patientUsername;
        this.searchedIllness = searchedIllness;
        this.hasIllness = hasIllness;
    }

    public String getPatientUsername() {
        return patientUsername;
    }

    public void setPatientUsername(String patientUsername) {
        this.patientUsername = patientUsername;
    }

    public String getSearchedIllness() {
        return searchedIllness;
    }

    public void setSearchedIllness(String searchedIllness) {
        this.searchedIllness = searchedIllness;
    }

    public boolean isHasIllness() {
        return hasIllness;
    }

    public void setHasIllness(boolean hasIllness) {
        this.hasIllness = hasIllness;
    }
}
