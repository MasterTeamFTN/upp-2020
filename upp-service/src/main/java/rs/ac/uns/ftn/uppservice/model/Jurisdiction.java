package rs.ac.uns.ftn.uppservice.model;

public enum Jurisdiction {

    WRITERS("writers_jurisdiction"),
    EDITORS("editors_jurisdiction"),
    READERS("readers_jurisdiction"),
    SIMPLE_EDITORS("simple_editors_jurisdiction"),
    BOARD_MEMBERS("board_members_jurisdiction"),
    LECTURERS("lecturers_jurisdiction");

    public final String label;

    public String getLabel() {
        return label;
    }

    private Jurisdiction(String label) {
        this.label = label;
    }
}
