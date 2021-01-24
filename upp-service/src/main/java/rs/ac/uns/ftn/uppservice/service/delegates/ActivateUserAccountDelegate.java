package rs.ac.uns.ftn.uppservice.service.delegates;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.service.ReaderService;

import static rs.ac.uns.ftn.uppservice.common.constants.Constants.TOKEN;

@Component
@RequiredArgsConstructor
public class ActivateUserAccountDelegate implements JavaDelegate {

    private final ReaderService readerService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String token = (String) delegateExecution.getVariable(TOKEN);
        readerService.activateAccount(token);
    }
}
