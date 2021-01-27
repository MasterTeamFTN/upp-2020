package rs.ac.uns.ftn.uppservice.service.delegates.publishing;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.service.BookService;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;

@Component
@RequiredArgsConstructor
public class SendBetaReadersCommentsToWriterDelegate implements JavaDelegate {

    private final BookService bookService;
    private final MailSenderService mailSenderService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Book book = (Book) execution.getVariable(Constants.BOOK);
        Book persistedBook = bookService.findById(book.getId());
        mailSenderService.sendBetaReadersCommentsToAuthor(persistedBook);
    }
}
