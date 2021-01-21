package rs.ac.uns.ftn.uppservice.service.delegates.publishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.service.BookService;

import java.util.List;

@Component
public class SaveBetaReadersCommentsDelegate implements JavaDelegate {

    @Autowired
    private BookService bookService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<FormSubmissionDto> commentsForm = (List<FormSubmissionDto>) execution.getVariable("formData");
        String comment = (String) commentsForm.get(0).getFieldValue();
        String readerUsername = (String) execution.getVariable("betaReaderAssignee");
        Book book = (Book) execution.getVariable("book");

        bookService.addBetaReadersComments(book.getId() ,readerUsername, comment);
    }
}
