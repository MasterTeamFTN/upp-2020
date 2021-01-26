package rs.ac.uns.ftn.uppservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.model.*;
import rs.ac.uns.ftn.uppservice.dto.response.WriterPaperResourceDto;
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
    public void sendBoardMemberNotification(List<String> emails, ConfirmationToken confirmationToken, List<WriterPaperResourceDto> userPapers) {
        emails.stream().forEach(email -> send(email, confirmationToken, userPapers));
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

    private void send(String email, ConfirmationToken confirmationToken, List<WriterPaperResourceDto> userPapers) {
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
            userPapers.stream().forEach(paper -> {
                try {
                    helper.addAttachment(paper.getFileName(), paper.getByteArrayResource());
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });


            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
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

    @Override
    public void sendLecturersCommentsToAuthor(Book book, Suggestion suggestion) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Lecturers comments - UPP");
        message.setFrom("UPP-App");
        message.setTo(book.getWriter().getEmail());

        StringBuilder body = new StringBuilder();
        body.append("Lecturer finished review. This is the list with his comments\n");

        for (String error : suggestion.getFoundErrors()) {
            body.append(error);
        }

        message.setText(body.toString());
        mailSender.send(message);
    }

    @Override
    public void sendChiefEditorCommentsToAuthor(Book book, Suggestion suggestion) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Chief editors comments - UPP");
        message.setFrom("UPP-App");
        message.setTo(book.getWriter().getEmail());

        StringBuilder body = new StringBuilder();
        body.append("Chief editor finished review. This is the list with his comments\n");

        for (String error : suggestion.getFoundErrors()) {
            body.append(error);
        }

        message.setText(body.toString());
        mailSender.send(message);
    }

    @Override
    public void notifyBookIsPublished(Book book) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Book published - UPP");
        message.setFrom("UPP-App");
        message.setTo(book.getWriter().getEmail());
        message.setText("Your book " + book.getTitle() + " has been published!");
        mailSender.send(message);
    }

    @Override
    public void sendRejectBook(Book book) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Book rejected - UPP");
        message.setFrom("UPP-App");
        message.setTo(book.getWriter().getEmail());
        message.setText("Your book " + book.getTitle() + " has been rejected! You didn't make changes in time.");
        mailSender.send(message);
    }

	@Override
	public void submitPlagiarismForm(ChiefEditor chiefEditor, Book originalBook, Book plagiat) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendChiefEditorPlagiarismNotification(ChiefEditor chiefEditor, Book originalBook, Book plagiat) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("New report has been added");
        message.setFrom("UPP-App");
        message.setTo(chiefEditor.getEmail());
        String author = plagiat.getWriter().getFirstName() + " " + plagiat.getWriter().getLastName();
        message.setText("Book - " + plagiat.getTitle() + " by " + author + " has been reported as plagiat.");
        System.out.println(message);
        //mailSender.send(message);
	}

}
