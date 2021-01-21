package rs.ac.uns.ftn.uppservice.service.delegates.publishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.service.BookService;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;

@Component
public class BookIsPlagiarisedDelegate implements JavaDelegate {

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private BookService bookService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Book book = (Book) execution.getVariable("book");
        bookService.markBookAsPlagiarised(book);
        String reason = (String) execution.getVariable("FormField_reason");
        mailSenderService.notifyUserBookIsPlagiarised(book, reason);
    }
}
