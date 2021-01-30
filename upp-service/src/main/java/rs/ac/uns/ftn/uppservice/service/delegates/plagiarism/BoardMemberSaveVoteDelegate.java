package rs.ac.uns.ftn.uppservice.service.delegates.plagiarism;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.model.BoardMember;
import rs.ac.uns.ftn.uppservice.model.Complaint;
import rs.ac.uns.ftn.uppservice.service.BookService;
import rs.ac.uns.ftn.uppservice.service.ComplaintService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardMemberSaveVoteDelegate implements JavaDelegate {

    private final BookService bookService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        var votes = (List<String>) execution.getVariable(Constants.BOARD_MEMBERS_VOTES);
        var form = (List<FormSubmissionDto>) execution.getVariable(Constants.FORM_DATA);
        String vote = (String) form.get(0).getFieldValue();
        votes.add(vote);

        String boardMemberUsername = (String) execution.getVariable(Constants.BOARD_MEMBER_ASSIGNEE);
        Complaint complaint = (Complaint) execution.getVariable(Constants.COMPLAINT);

        bookService.addBoardMembersDecision(complaint.getId(), boardMemberUsername, vote.equals("1")? true : false);
    }
}
