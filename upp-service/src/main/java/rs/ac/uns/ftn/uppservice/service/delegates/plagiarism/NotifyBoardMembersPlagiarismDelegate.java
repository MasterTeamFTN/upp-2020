package rs.ac.uns.ftn.uppservice.service.delegates.plagiarism;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.model.BoardMember;
import rs.ac.uns.ftn.uppservice.model.Complaint;
import rs.ac.uns.ftn.uppservice.service.BoardMemberService;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NotifyBoardMembersPlagiarismDelegate implements JavaDelegate {

    private final BoardMemberService boardMemberService;
    private final MailSenderService mailSenderService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Complaint complaint = (Complaint) execution.getVariable(Constants.COMPLAINT);
        List<BoardMember> boardMembers = boardMemberService.findAll();

        boardMembers.stream()
                .forEach(member -> mailSenderService.notifyBoardMemberToReviewPlagiarism(member, complaint));

        List<String> boardMemberUsernames = boardMembers.stream().map(member -> member.getUsername())
                .collect(Collectors.toList());

        execution.setVariable(Constants.BOARD_MEMBERS_REVIEW, boardMemberUsernames);
    }
}
