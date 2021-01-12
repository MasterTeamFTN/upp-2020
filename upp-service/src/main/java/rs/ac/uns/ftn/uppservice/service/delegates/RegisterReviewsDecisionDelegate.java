package rs.ac.uns.ftn.uppservice.service.delegates;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.service.BoardMemberService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RegisterReviewsDecisionDelegate implements JavaDelegate {

    private final BoardMemberService boardMemberService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // ovde dodje tek kad svi submituju, ekstraaaaaaaaaaa

        var boardMemberDecision = (List<String>) execution.getVariable("boardMemberDecision");
        execution.setVariable("decision",
                boardMemberService.registerDecision(boardMemberDecision, execution.getProcessInstanceId(), execution.getId()));
    }
}
