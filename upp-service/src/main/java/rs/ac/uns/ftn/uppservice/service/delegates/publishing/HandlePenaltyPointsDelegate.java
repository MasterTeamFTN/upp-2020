package rs.ac.uns.ftn.uppservice.service.delegates.publishing;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.service.ReaderService;

@Component
@RequiredArgsConstructor
public class HandlePenaltyPointsDelegate implements JavaDelegate {

    private final ReaderService readerService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String username = (String) execution.getVariable(Constants.BETA_READER_ASSIGNEE);
        readerService.handlePenaltyPoints(username);
    }
}
