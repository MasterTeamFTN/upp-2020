package rs.ac.uns.ftn.uppservice.service.delegates.file;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FileUploadValidateDelegate implements JavaDelegate {

    private static final String WORK_COUNT = "workCount";

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String token = (String) delegateExecution.getVariable("token");

        int workCount = 1;
        if (delegateExecution.getVariable(WORK_COUNT) != null) {
            workCount = (int) delegateExecution.getVariable(WORK_COUNT);
            workCount++;
        }
        delegateExecution.setVariable(WORK_COUNT, workCount);

    }
}