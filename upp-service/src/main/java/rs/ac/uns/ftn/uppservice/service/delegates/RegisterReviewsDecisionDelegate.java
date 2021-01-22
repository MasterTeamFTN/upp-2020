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
        var boardMemberDecisions = (List<String>) execution.getVariable(BOARD_MEMBER_DECISIONS);
        execution.setVariable(DECISION,
                boardMemberService.registerDecision(boardMemberDecisions, execution.getProcessInstanceId(), execution.getId()));
        int loopCount = 1;
        if (execution.getVariable(LOOP_COUNT) != null) {
            loopCount = (int) execution.getVariable(LOOP_COUNT);
            loopCount++;
        }
        execution.setVariable(LOOP_COUNT, loopCount);
    }
}
