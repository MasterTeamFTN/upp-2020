package rs.ac.uns.ftn.uppservice.service.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class NotifyPendingWriterDelegate implements JavaDelegate {


    @Override
    public void execute(DelegateExecution execution) throws Exception {

    }
}