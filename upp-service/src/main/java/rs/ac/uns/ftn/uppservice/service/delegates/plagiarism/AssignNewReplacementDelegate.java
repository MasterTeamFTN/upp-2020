package rs.ac.uns.ftn.uppservice.service.delegates.plagiarism;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.model.Complaint;
import rs.ac.uns.ftn.uppservice.model.Editor;
import rs.ac.uns.ftn.uppservice.repository.UserRepository;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;
import rs.ac.uns.ftn.uppservice.service.UserService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AssignNewReplacementDelegate implements JavaDelegate {

    private final UserService userService;
    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<FormSubmissionDto> form = (List<FormSubmissionDto>) execution.getVariable(Constants.FORM_DATA);
        Complaint complaint = (Complaint) execution.getVariable(Constants.COMPLAINT);
        String newEditorId = (String) form.get(0).getFieldValue();

        String newEditorUsername = userService.findById(Long.parseLong(newEditorId)).getUsername();

        execution.setVariable(Constants.EDITORS_ASSIGNEE, newEditorUsername);
        Editor editor = (Editor) userRepository.findByUsername(newEditorUsername);
        mailSenderService.notifyEditorToReviewPlagiarism(editor, complaint);
    }
}
