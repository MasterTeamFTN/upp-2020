package rs.ac.uns.ftn.uppservice.model;

public enum MembershipDecision {

    NOT_SUBMITTED_YET("notSubmittedYet"),
    NEED_MORE_INFO("needMoreInfo"),
    REJECT("reject"),
    APPROVE("approve");

    public final String label;

    public String getLabel() {
        return label;
    }

    private MembershipDecision(String label) {
        this.label = label;
    }
}
