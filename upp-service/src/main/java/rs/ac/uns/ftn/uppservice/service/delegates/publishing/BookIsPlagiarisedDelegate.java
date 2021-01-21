package rs.ac.uns.ftn.uppservice.service.delegates.publishing;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.service.BookService;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;

@Component
@RequiredArgsConstructor
public class BookIsPlagiarisedDelegate implements JavaDelegate {

    private final MailSenderService mailSenderService;
    private final BookService bookService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Book book = (Book) execution.getVariable("book");
        bookService.markBookAsPlagiarised(book);
        String reason = (String) execution.getVariable("FormField_reason");
        mailSenderService.notifyUserBookIsPlagiarised(book, reason);
    }
}
