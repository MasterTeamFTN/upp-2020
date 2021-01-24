package rs.ac.uns.ftn.uppservice.service.delegates.publishing;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.service.BookService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SaveBetaReadersCommentsDelegate implements JavaDelegate {

    private final BookService bookService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<FormSubmissionDto> commentsForm = (List<FormSubmissionDto>) execution.getVariable(Constants.FORM_DATA);
        String comment = (String) commentsForm.get(0).getFieldValue();
        String readerUsername = (String) execution.getVariable(Constants.BETA_READER_ASSIGNEE);
        Book book = (Book) execution.getVariable(Constants.BOOK);

        bookService.addBetaReadersComments(book.getId() ,readerUsername, comment);
    }
}
