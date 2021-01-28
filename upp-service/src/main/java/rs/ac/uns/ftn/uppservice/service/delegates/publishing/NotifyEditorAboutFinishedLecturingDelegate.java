package rs.ac.uns.ftn.uppservice.service.delegates.publishing;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.model.BookPublishingJurisdiction;
import rs.ac.uns.ftn.uppservice.model.ChiefEditor;
import rs.ac.uns.ftn.uppservice.service.BookService;
import rs.ac.uns.ftn.uppservice.service.FileService;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;
import rs.ac.uns.ftn.uppservice.service.UserService;

@Component
@RequiredArgsConstructor
public class NotifyEditorAboutFinishedLecturingDelegate implements JavaDelegate {

    private final UserService userService;
    private final MailSenderService mailSenderService;
    private final FileService fileService;
    private final BookService bookService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Book book = (Book) execution.getVariable(Constants.BOOK);

        book.setJurisdiction(BookPublishingJurisdiction.EDITORS);
        bookService.save(book);
        execution.setVariable(Constants.BOOK, book);

        ChiefEditor chiefEditor = userService.getChiefEditor();

        mailSenderService.notifyEditorAboutFinishedLecturing(chiefEditor, book);

    }
}
