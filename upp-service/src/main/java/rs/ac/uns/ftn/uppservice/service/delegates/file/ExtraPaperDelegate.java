package rs.ac.uns.ftn.uppservice.service.delegates.file;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static rs.ac.uns.ftn.uppservice.common.constants.Constants.WORK_COUNT;

@Component
public class ExtraPaperDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        execution.setVariable(WORK_COUNT, 2);
    }
}
