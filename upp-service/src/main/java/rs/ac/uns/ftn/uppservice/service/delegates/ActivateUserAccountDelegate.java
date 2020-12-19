package rs.ac.uns.ftn.uppservice.service.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.service.ReaderService;

@Component
public class ActivateUserAccountDelegate implements JavaDelegate {

    @Autowired
    private ReaderService readerService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String token = (String) delegateExecution.getVariable("token");
        readerService.activateAccount(token);
    }
}
