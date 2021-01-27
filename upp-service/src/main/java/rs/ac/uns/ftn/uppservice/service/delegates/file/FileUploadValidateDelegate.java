package rs.ac.uns.ftn.uppservice.service.delegates.file;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static rs.ac.uns.ftn.uppservice.common.constants.Constants.WORK_COUNT;

@Component
public class FileUploadValidateDelegate implements JavaDelegate {


    @Override
    public void execute(DelegateExecution execution) {
        int workCount = 1;
        if (execution.getVariable(WORK_COUNT) != null) {
            workCount = (int) execution.getVariable(WORK_COUNT);
            workCount++;
        }
        execution.setVariable(WORK_COUNT, workCount);
    }
}