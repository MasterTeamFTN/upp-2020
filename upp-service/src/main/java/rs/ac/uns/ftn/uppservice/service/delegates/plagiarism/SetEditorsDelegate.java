package rs.ac.uns.ftn.uppservice.service.delegates.plagiarism;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.model.Complaint;
import rs.ac.uns.ftn.uppservice.model.CompliantAssignment;
import rs.ac.uns.ftn.uppservice.model.Editor;
import rs.ac.uns.ftn.uppservice.model.User;
import rs.ac.uns.ftn.uppservice.repository.UserRepository;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SetEditorsDelegate implements JavaDelegate {

    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<FormSubmissionDto> formData = (List<FormSubmissionDto>) execution.getVariable(Constants.FORM_DATA);
        List<String> editorsIds = (List<String>) formData.get(0).getFieldValue();
        List<Editor> editors = new ArrayList<>();

        editorsIds.stream()
                .forEach(id -> {
                    User user = userRepository.findById(Long.parseLong(id)).get();
                    editors.add((Editor) user);
                });

        execution.setVariable(
                Constants.EDITORS,
                editors.stream().map(editor -> editor.getUsername())
                        .collect(Collectors.toList())
        );

        execution.setVariable(Constants.NEW_EDITORS, new ArrayList<String>());

        Complaint complaint = (Complaint) execution.getVariable(Constants.COMPLAINT);
        editors.forEach(editor -> mailSenderService.notifyEditorToReviewPlagiarism(editor, complaint));
    }

}
