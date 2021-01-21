package rs.ac.uns.ftn.uppservice.service.delegates.file;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static rs.ac.uns.ftn.uppservice.common.constants.Constants.WORK_COUNT;

@RequiredArgsConstructor
@Component
public class FileUploadValidateDelegate implements JavaDelegate {


    @Override
    public void execute(DelegateExecution delegateExecution) {
        int workCount = 1;
        if (delegateExecution.getVariable(WORK_COUNT) != null) {
            workCount = (int) delegateExecution.getVariable(WORK_COUNT);
            workCount++;
        }
        delegateExecution.setVariable(WORK_COUNT, workCount);
    }
}