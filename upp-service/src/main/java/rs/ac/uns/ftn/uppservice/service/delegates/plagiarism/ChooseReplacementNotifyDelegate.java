package rs.ac.uns.ftn.uppservice.service.delegates.plagiarism;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.model.Complaint;
import rs.ac.uns.ftn.uppservice.model.Jurisdiction;
import rs.ac.uns.ftn.uppservice.service.ComplaintService;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;

@Component
@RequiredArgsConstructor
public class ChooseReplacementNotifyDelegate implements JavaDelegate {

    private final MailSenderService mailSenderService;
    private final ComplaintService complaintService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String editorUsername = (String) execution.getVariable(Constants.EDITORS_ASSIGNEE);
        Complaint complaint = (Complaint) execution.getVariable(Constants.COMPLAINT);
        complaint.setJurisdiction(Jurisdiction.EDITORS);
        complaintService.save(complaint);
        execution.setVariable(Constants.COMPLAINT, complaint);

        mailSenderService.notifyChiefEditorToFindReplacement(editorUsername, complaint);
    }
}
