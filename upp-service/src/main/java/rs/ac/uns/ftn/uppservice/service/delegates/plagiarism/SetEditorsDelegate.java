package rs.ac.uns.ftn.uppservice.service.delegates.plagiarism;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.model.*;
import rs.ac.uns.ftn.uppservice.repository.UserRepository;
import rs.ac.uns.ftn.uppservice.service.BookService;
import rs.ac.uns.ftn.uppservice.service.ComplaintService;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SetEditorsDelegate implements JavaDelegate {

    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;
    private final BookService bookService;
    private final ComplaintService complaintService;

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
        complaint.setJurisdiction(Jurisdiction.SIMPLE_EDITORS);
        complaintService.save(complaint);
        execution.setVariable(Constants.COMPLAINT, complaint);

        editors.forEach(editor -> {
            bookService.addEditorsNotesComments(complaint.getId(), editor.getUsername(), null);
            mailSenderService.notifyEditorToReviewPlagiarism(editor, complaint);});
    }

}
