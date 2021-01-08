package rs.ac.uns.ftn.uppservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.model.ConfirmationToken;
import rs.ac.uns.ftn.uppservice.model.Reader;
import rs.ac.uns.ftn.uppservice.model.User;
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

    @Override
    public void sendChiefEditorNewBookNotification(User chiefEditor, Book book) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("New book has been added - UPP");
        message.setFrom("UPP-App");
        message.setTo(chiefEditor.getEmail());
        String author = book.getWriter().getFirstName() + " " + book.getWriter().getLastName();
        message.setText("New book - " + book.getTitle() + " by " + author + " has been added." +
                "\nTitle: " + book.getTitle() +
                "\nGenre: " + book.getGenre().getName() +
                "\nSynopsis: " + book.getSynopsis());
        mailSender.send(message);
    }

    @Override
    public void sendWriterRejectBook(Book book, String reason) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Book rejected - UPP");
        message.setFrom("UPP-App");
        message.setTo(book.getWriter().getEmail());
        message.setText("Book - " + book.getTitle() + " has been rejected. \nReason: " + reason);
        mailSender.send(message);
    }

    @Override
    public void notifyWriterToSendFullBook(Book book) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Book accepted - UPP");
        message.setFrom("UPP-App");
        message.setTo(book.getWriter().getEmail());
        message.setText("Book - " + book.getTitle() + " has been accepted. \nPlease send full version");
        mailSender.send(message);
    }

    @Override
    public void notifyWriterFullBookTimedOut(Book book) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Book rejected - UPP");
        message.setFrom("UPP-App");
        message.setTo(book.getWriter().getEmail());
        message.setText("Book - " + book.getTitle() + " has been rejected. You didn't send the full version on time");
        mailSender.send(message);
    }

    @Override
    public void notifyUserBookIsPlagiarised(Book book, String reason) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Book rejected - plagiarised - UPP");
        message.setFrom("UPP-App");
        message.setTo(book.getWriter().getEmail());
        message.setText("Book - " + book.getTitle() + " has been rejected because it's plagiarised." +
                "\nReason: " + reason);
        mailSender.send(message);
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

    @Override
    public void notifyReaderLostBetaStatus(Reader reader) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("You lost beta-reader status - UPP");
        message.setFrom("UPP-App");
        message.setTo(reader.getEmail());
        message.setText(reader.getFirstName() + ", you lost beta reader status because you have " +
                reader.getPenaltyPoints() + " penalty points");
        mailSender.send(message);
    }

}
