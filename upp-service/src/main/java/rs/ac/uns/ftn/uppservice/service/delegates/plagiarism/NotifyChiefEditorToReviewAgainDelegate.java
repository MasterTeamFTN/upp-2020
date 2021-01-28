package rs.ac.uns.ftn.uppservice.service.delegates.plagiarism;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.model.Complaint;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;

@Component
@RequiredArgsConstructor
public class NotifyChiefEditorToReviewAgainDelegate implements JavaDelegate {

    private final MailSenderService mailSenderService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Complaint complaint = (Complaint) execution.getVariable(Constants.COMPLAINT);
        mailSenderService.notifyChiefEditorToChooseEditorsAgain(complaint);
    }
}
