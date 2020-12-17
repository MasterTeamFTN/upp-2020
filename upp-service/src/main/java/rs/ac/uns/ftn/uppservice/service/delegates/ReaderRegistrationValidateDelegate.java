package rs.ac.uns.ftn.uppservice.service.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.service.ReaderService;

import java.util.List;

@Component
public class ReaderRegistrationValidateDelegate implements JavaDelegate {

    @Autowired
    private ReaderService readerService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<FormSubmissionDto> registrationForm = (List<FormSubmissionDto>) execution.getVariable("registrationFormData");
        readerService.add(registrationForm, execution.getProcessInstanceId());
    }

}
