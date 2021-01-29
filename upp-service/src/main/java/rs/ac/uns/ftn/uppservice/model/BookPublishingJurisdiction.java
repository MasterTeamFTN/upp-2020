package rs.ac.uns.ftn.uppservice.model;

public enum BookPublishingJurisdiction {

    WRITERS("writers_jurisdiction"),
    EDITORS("editors_jurisdiction"),
    READERS("readers_jurisdiction"),
    LECTURERS("lecturers_jurisdiction");

    public final String label;

    public String getLabel() {
        return label;
    }

    private BookPublishingJurisdiction(String label) {
        this.label = label;
    }
}
