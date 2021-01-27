package rs.ac.uns.ftn.uppservice.service.delegates.plagiarism;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.model.Complaint;
import rs.ac.uns.ftn.uppservice.service.BookService;

import java.util.List;

@Component
public class SaveEditorsNotesDelegate implements JavaDelegate {

    @Autowired
    private BookService bookService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<FormSubmissionDto> commentsForm = (List<FormSubmissionDto>) execution.getVariable("formData");
        System.out.println(commentsForm.size());
        String comment = (String) commentsForm.get(0).getFieldValue();
        String editorUsername = (String) execution.getVariable("editorAssignee");
        Book original = (Book) execution.getVariable("originalBook");
        Book plagiat = (Book) execution.getVariable("plagiatBook");
        Complaint complaint = (Complaint) execution.getVariable("complaint");
        bookService.addEditorsNotesComments(plagiat.getId(), original.getId(),editorUsername, comment);
    }
}
