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

import static rs.ac.uns.ftn.uppservice.common.constants.Constants.*;

@Component
@RequiredArgsConstructor
public class ReaderRegistrationValidateDelegate implements JavaDelegate {

    private final ReaderService readerService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<FormSubmissionDto> registrationForm = (List<FormSubmissionDto>) execution.getVariable(REGISTRATION_FORM_DATA);
        List<FormSubmissionDto> chooseGenresForm = (List<FormSubmissionDto>) execution.getVariable(CHOOSE_GENRES_FORM_DATA);

        Reader reader = readerService.add(registrationForm, chooseGenresForm, execution.getProcessInstanceId());
        execution.setVariable(READER, reader);
    }

}
