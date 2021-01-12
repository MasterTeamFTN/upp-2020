package rs.ac.uns.ftn.uppservice.service;

import org.springframework.core.io.ByteArrayResource;
import rs.ac.uns.ftn.uppservice.model.ConfirmationToken;

import java.util.List;

public interface MailSenderService {

    void sendRegistrationMail(ConfirmationToken token);

    void sendBoardMemberNotification(List<String> emails, ConfirmationToken token, List<ByteArrayResource> userPapers);
}
