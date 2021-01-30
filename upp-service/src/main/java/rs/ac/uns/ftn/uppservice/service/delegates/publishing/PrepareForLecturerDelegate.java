package rs.ac.uns.ftn.uppservice.service.delegates.publishing;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.model.Jurisdiction;
import rs.ac.uns.ftn.uppservice.model.User;
import rs.ac.uns.ftn.uppservice.repository.BookRepository;
import rs.ac.uns.ftn.uppservice.service.FileService;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;
import rs.ac.uns.ftn.uppservice.service.UserService;

@Component
@RequiredArgsConstructor
public class PrepareForLecturerDelegate implements JavaDelegate {

    private final BookRepository bookRepository;
    private final UserService userService;
    private final MailSenderService mailSenderService;
    private final FileService fileService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Book book = (Book) execution.getVariable(Constants.BOOK);
        User lecturer = userService.getLecturer();

        book.setJurisdiction(Jurisdiction.LECTURERS);
        bookRepository.save(book);
        execution.setVariable(Constants.BOOK, book);

        mailSenderService.sendNotificationAboutHandwrite(book, lecturer.getEmail(), fileService.getHandwrite(book));
    }
}
