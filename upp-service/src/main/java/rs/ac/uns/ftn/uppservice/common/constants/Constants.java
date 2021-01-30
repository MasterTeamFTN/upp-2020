package rs.ac.uns.ftn.uppservice.common.constants;

import org.springframework.stereotype.Component;

@Component
public final class Constants {

    // Process names
    public static final String PROCESS_PUBLISH_BOOK = "Process_BookPublishing";
    public static final String PROCESS_PLAGIARISM = "Plagiarism_Process";

    public static final String REQUESTED_MEMBER = "requestedMember";
    public static final String TOKEN = "token";
    public static final String BOARD_MEMBERS = "boardMembers";
    public static final String REGISTRATION_FORM_DATA = "registrationFormData";
    public static final String CHOOSE_GENRES_FORM_DATA = "chooseGenresFormData";
    public static final String READER = "reader";
    public static final String WRITER = "writer";
    public static final String FORM_DATA = "formData";

    // Constants from process publishing book
    public static final String BOOK = "book";
    public static final String BETA_READER_ASSIGNEE = "betaReaderAssignee";
    public static final String BETA_READERS = "betaReaders";

    // Constants from process plagiarism
    public static final String COMPLAINT = "complaint";
    public static final String EDITORS = "editors";
    public static final String EDITORS_ASSIGNEE = "editorAssignee";
    public static final String NEW_EDITORS = "newEditors";
    public static final String BOARD_MEMBERS_REVIEW = "boardMembers";
    public static final String BOARD_MEMBER_ASSIGNEE = "boardMemberAssignee";
    public static final String BOARD_MEMBERS_VOTES = "boardMembersVotes";
    public static final String VOTES_TOTAL = "votesTotal";
    public static final String NUM_YES_VOTES = "numOfYesVotes";
    public static final String NUM_NO_VOTES = "numOfNoVotes";

    public static final String BOARD_MEMBER_DECISIONS = "boardMemberDecisions";
    public static final String DECISION = "decision";
    public static final String WORK_COUNT = "workCount";
    public static final String LOOP_COUNT = "loopCount";

    // Files
    public static final String SUBMIT_FILE_DATA = "submitFileData";
    public static final String FILE_TEMP_FOLDER = "src/main/resources/temp/";
    public static final String TEMP_FILE_PATH = "tempFilePath";


}
