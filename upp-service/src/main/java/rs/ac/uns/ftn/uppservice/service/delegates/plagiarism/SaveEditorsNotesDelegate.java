package rs.ac.uns.ftn.uppservice.service.delegates.plagiarism;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.model.Complaint;
import rs.ac.uns.ftn.uppservice.service.BookService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SaveEditorsNotesDelegate implements JavaDelegate {

    private final BookService bookService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<FormSubmissionDto> commentsForm = (List<FormSubmissionDto>) execution.getVariable(Constants.FORM_DATA);
        String comment = (String) commentsForm.get(0).getFieldValue();
        String editorUsername = (String) execution.getVariable(Constants.EDITORS_ASSIGNEE);
        Complaint complaint = (Complaint) execution.getVariable(Constants.COMPLAINT);

        complaint = bookService.addEditorsNotesComments(complaint.getId(), editorUsername, comment);

        // update complaint
        execution.setVariable(Constants.COMPLAINT, complaint);
    }
}
