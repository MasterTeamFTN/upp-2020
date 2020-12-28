package rs.ac.uns.ftn.uppservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.model.ConfirmationToken;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;

import java.util.List;

@Service
public class MailSenderServiceImpl implements MailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendRegistrationMail(ConfirmationToken token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Account verification - UPP");
        message.setFrom("UPP-App");
        message.setTo(token.getUser().getEmail());
        message.setText("Go to this page to activate your account http://localhost:4200/verify?token=" + token.getToken());
        mailSender.send(message);
    }

    @Override
    public void sendBoardMemberNotification(List<String> emails, ConfirmationToken confirmationToken) {
        emails.stream().forEach(email -> send(email, confirmationToken));
    }

    private void send(String email, ConfirmationToken confirmationToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(String.join(
                " ",
                "Writer",
                confirmationToken.getUser().getFirstName(),
                confirmationToken.getUser().getLastName(),
                " registration request"));

        message.setFrom("UPP-App");
        message.setTo(email);
        message.setText("Go to this page to decide about writers registration request: " +
                "http://localhost:4200/registrationRequest/"
                + confirmationToken.getProcessInstanceId());
        mailSender.send(message);
    }
}
