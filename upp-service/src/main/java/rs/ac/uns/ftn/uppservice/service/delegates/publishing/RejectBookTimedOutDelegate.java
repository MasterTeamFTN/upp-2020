package rs.ac.uns.ftn.uppservice.service.delegates.publishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.service.BookService;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;

@Component
public class RejectBookTimedOutDelegate implements JavaDelegate {

    @Autowired
    private BookService bookService;

    @Autowired
    private MailSenderService mailSenderService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("TIMED OUT");
        Book book = (Book) execution.getVariable("book");
        bookService.reject(book.getId());
        mailSenderService.sendRejectBook(book);
    }
}
