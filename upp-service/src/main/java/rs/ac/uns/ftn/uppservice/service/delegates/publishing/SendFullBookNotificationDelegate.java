package rs.ac.uns.ftn.uppservice.service.delegates.publishing;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;

@Component
@RequiredArgsConstructor
public class SendFullBookNotificationDelegate implements JavaDelegate {

    private final MailSenderService mailSenderService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Book book = (Book) execution.getVariable(Constants.BOOK);
        mailSenderService.notifyWriterToSendFullBook(book);
    }
}
