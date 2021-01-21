package rs.ac.uns.ftn.uppservice.service.delegates.publishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.model.Suggestion;
import rs.ac.uns.ftn.uppservice.service.BookService;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;

import java.util.List;

@Component
public class SaveChiefEditorsReportAndNotifyDelegate implements JavaDelegate {

    @Autowired
    private BookService bookService;

    @Autowired
    private MailSenderService mailSenderService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<FormSubmissionDto> form = (List<FormSubmissionDto>) execution.getVariable("formData");
        String comment = (String) form.get(0).getFieldValue();
        Book book = (Book) execution.getVariable("book");

        Suggestion suggestion = bookService.addChiefEditorsComments(book.getId(), comment);

        Book savedBook = bookService.findById(book.getId());
        mailSenderService.sendChiefEditorCommentsToAuthor(savedBook, suggestion);
    }
}
