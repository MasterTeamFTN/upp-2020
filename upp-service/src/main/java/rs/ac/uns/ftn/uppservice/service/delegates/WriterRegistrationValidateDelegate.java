package rs.ac.uns.ftn.uppservice.service.delegates;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.service.UserAccountService;
import rs.ac.uns.ftn.uppservice.service.WriterService;

import java.util.List;

@RequiredArgsConstructor
public class WriterRegistrationValidateDelegate implements JavaDelegate {

    private final WriterService writerService;
    private final UserAccountService userAccountService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        execution.setVariable("writer",
                writerService.add(
                        (List<FormSubmissionDto>) execution.getVariable("registrationFormData"),
                        (List<FormSubmissionDto>) execution.getVariable("chooseGenresFormData"),
                        execution.getProcessInstanceId()));
    }


}
