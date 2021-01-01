package rs.ac.uns.ftn.uppservice.service.delegates;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.service.UserAccountService;
import rs.ac.uns.ftn.uppservice.service.WriterService;

import java.util.List;

@RequiredArgsConstructor
@Component
public class WriterRegistrationValidateDelegate implements JavaDelegate {

    private final WriterService writerService;
    private final UserAccountService userAccountService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        var listOfFields = (List<FormSubmissionDto>) execution.getVariable("registrationFormData");
        execution.setVariable("writer",
                writerService.add(
                        listOfFields,
                        execution.getProcessInstanceId()));
    }


}
