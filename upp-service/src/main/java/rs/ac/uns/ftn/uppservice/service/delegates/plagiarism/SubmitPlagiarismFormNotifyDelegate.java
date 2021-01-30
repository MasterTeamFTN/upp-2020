package rs.ac.uns.ftn.uppservice.service.delegates.plagiarism;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.model.Complaint;
import rs.ac.uns.ftn.uppservice.service.BookService;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SubmitPlagiarismFormNotifyDelegate implements JavaDelegate {

    private final BookService bookService;
    private final MailSenderService mailSenderService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<FormSubmissionDto> formData = (List<FormSubmissionDto>) execution.getVariable(Constants.FORM_DATA);
        Complaint complaint = bookService.submitPlagiarismForm(formData, execution.getProcessInstanceId());
        execution.setVariable(Constants.COMPLAINT, complaint);

        mailSenderService.sendChiefEditorPlagiarismNotification(complaint);
    }

}
