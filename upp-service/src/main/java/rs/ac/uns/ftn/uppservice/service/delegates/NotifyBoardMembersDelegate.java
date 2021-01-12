package rs.ac.uns.ftn.uppservice.service.delegates;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.service.BoardMemberService;

import java.util.List;

@RequiredArgsConstructor
@Component
public class NotifyBoardMembersDelegate implements JavaDelegate {

    private final BoardMemberService boardMemberService;
    private final IdentityService identityService;


    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<User> boardMembers = boardMemberService.notifyBoardMembers(execution.getProcessInstanceId());
//        List<User> boardMembers = (List<User>) boardMemberEmails
//                .stream()
//                .map(boardMemberEmail ->
//                        identityService
//                                .createUserQuery()
//                                .userEmail(boardMemberEmail));
        execution.setVariable("boardMembers", boardMembers);
    }
}
