package rs.ac.uns.ftn.uppservice.service.delegates;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.service.BoardMemberService;

import java.util.List;

import static rs.ac.uns.ftn.uppservice.common.constants.Constants.*;

@Component
@RequiredArgsConstructor
public class RegisterReviewsDecisionDelegate implements JavaDelegate {

    private final BoardMemberService boardMemberService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        var boardMemberDecision = (List<String>) execution.getVariable(BOARD_MEMBER_DECISIONS);
        execution.setVariable(DECISION,
                boardMemberService.registerDecision(boardMemberDecision, execution.getProcessInstanceId(), execution.getId()));
    }
}
