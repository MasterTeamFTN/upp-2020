package rs.ac.uns.ftn.uppservice.service.delegates;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.service.BoardMemberService;

import java.util.List;

import static rs.ac.uns.ftn.uppservice.common.constants.Constants.*;

@RequiredArgsConstructor
@Component
public class NotifyBoardMembersDelegate implements JavaDelegate {

    private final BoardMemberService boardMemberService;

    @Override
    public void execute(DelegateExecution execution) {
        List<User> boardMembers = boardMemberService.notifyBoardMembers(execution.getProcessInstanceId());
        execution.setVariable(BOARD_MEMBERS, boardMembers);
        execution.setVariable(BOARD_MEMBER_DECISIONS, null);
    }
}
