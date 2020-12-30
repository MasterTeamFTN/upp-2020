package rs.ac.uns.ftn.uppservice.service.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;

@Component
public class SendFullBookNotificationDelegate implements JavaDelegate {

    @Autowired
    private MailSenderService mailSenderService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Book book = (Book) execution.getVariable("book");
        mailSenderService.notifyWriterToSendFullBook(book);
    }
}
