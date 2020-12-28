package rs.ac.uns.ftn.uppservice.service.delegates;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.model.Reader;
import rs.ac.uns.ftn.uppservice.service.ReaderService;
import rs.ac.uns.ftn.uppservice.service.UserAccountService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReaderRegistrationValidateDelegate implements JavaDelegate {

    private final ReaderService readerService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<FormSubmissionDto> registrationForm = (List<FormSubmissionDto>) execution.getVariable("registrationFormData");
        List<FormSubmissionDto> chooseGenresForm = (List<FormSubmissionDto>) execution.getVariable("chooseGenresFormData");

        Reader reader = readerService.add(registrationForm, chooseGenresForm, execution.getProcessInstanceId());
        execution.setVariable("reader", reader);
    }

}
