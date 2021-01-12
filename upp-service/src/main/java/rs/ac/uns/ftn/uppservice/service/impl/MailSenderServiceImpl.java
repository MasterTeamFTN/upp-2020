package rs.ac.uns.ftn.uppservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.model.ConfirmationToken;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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
    public void sendBoardMemberNotification(List<String> emails, ConfirmationToken confirmationToken, List<ByteArrayResource> userPapers) {
        emails.stream().forEach(email -> send(email, confirmationToken, userPapers));
    }

    private void send(String email, ConfirmationToken confirmationToken, List<ByteArrayResource> userPapers) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("UPP-App");
            helper.setTo(email);
            helper.setSubject(String.join(
                    " ",
                    "Writer",
                    confirmationToken.getUser().getFirstName(),
                    confirmationToken.getUser().getLastName(),
                    " membership request"));

            helper.setText("Go to this page to decide about writers registration request: " +
                    "http://localhost:4200/membershipRequest?processInstanceId="
                    + confirmationToken.getProcessInstanceId());
            int[] idx = { 0 };
            userPapers.stream().forEach(paper -> {
                try {
                    helper.addAttachment("file_" + idx[0] + ".pdf", paper);
                    idx[0]++;
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });


            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
