package rs.ac.uns.ftn.uppservice.service.delegates.publishing;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.model.Jurisdiction;
import rs.ac.uns.ftn.uppservice.model.Reader;
import rs.ac.uns.ftn.uppservice.repository.ReaderRepository;
import rs.ac.uns.ftn.uppservice.service.BookService;
import rs.ac.uns.ftn.uppservice.service.FileService;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SetBetaReadersDelegate implements JavaDelegate {


    private final ReaderRepository readerRepository;
    private final MailSenderService mailSenderService;
    private final FileService fileService;
    private final BookService bookService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<FormSubmissionDto> formData = (List<FormSubmissionDto>) execution.getVariable(Constants.FORM_DATA);
        List<String> readersIds = (List<String>) formData.get(0).getFieldValue();
        List<String> usernames = new ArrayList<>();

        Book book = (Book) execution.getVariable(Constants.BOOK);
        book.setJurisdiction(Jurisdiction.READERS);
        bookService.save(book);

        readersIds.stream()
            .forEach(id -> {
                Reader reader = readerRepository.findById(Long.parseLong(id)).get();

                if (reader.getIsBetaReader()) {
                    usernames.add(reader.getUsername());
                    reader.getIdsOfBooksToComment().add(book.getId());
                    mailSenderService.sendNotificationAboutHandwrite(book, reader.getEmail(), fileService.getHandwrite(book));
                }
            });
        execution.setVariable(Constants.BETA_READERS, usernames);
    }
}
