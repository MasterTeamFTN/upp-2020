package rs.ac.uns.ftn.uppservice.service.delegates.publishing;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.service.BookService;

@Component
@RequiredArgsConstructor
public class SendFullBookTimedOutDelegate implements JavaDelegate {

    private final BookService bookService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Book book = (Book) execution.getVariable(Constants.BOOK);
        bookService.rejectAfterTimeOut(book);
    }
}
