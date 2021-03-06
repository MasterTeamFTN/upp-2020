package rs.ac.uns.ftn.uppservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.dto.response.PdfResourceDto;
import rs.ac.uns.ftn.uppservice.model.*;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {

    private final JavaMailSender mailSender;

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
    public void sendBoardMemberNotification(List<String> emails, ConfirmationToken confirmationToken, List<PdfResourceDto> userPapers) {
        emails.stream().forEach(email -> send(email, confirmationToken, userPapers));
    }

    @Override
    public void sendDecisionToWriter(String emailTo, MembershipDecision decision, String processInstanceId) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("UPP-App");
            helper.setTo(emailTo);
            helper.setSubject("We have decided about your membership request.");
            message.setContent(getContentByDecision(decision, processInstanceId), "text/html; charset=utf-8");
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String getContentByDecision(MembershipDecision decision, String processInstanceId) {
        String content = "";
        switch (decision) {
            case APPROVE:
                content = new StringBuilder().append("<h1>Thank you for sending us a membership request</h1>\n")
                        .append("<p>\n")
                        .append("We appreciate you taking the time to register on our website and send us two of your best papers.\n")
                        .append("<br>\n")
                        .append("<br>\n")
                        .append("We are happy to inform you that we <b>ACCEPTED</b> your membership request<br>")
                        .append("We are expecting you to make a payment for membership within the set deadline.\t\n")
                        .append("Please visit this <a href='http://localhost:4200/login'>link</a> ")
                        .append("and give us your payment information.\n")
                        .append("</p>\n")
                        .append("<h4>Best regards!</h4>")
                        .toString();
                break;
            case REJECT:
                content = new StringBuilder().append("<h1>Thank you for sending us a membership request</h1>\n")
                        .append("<p>\n")
                        .append("We appreciate you taking the time to register on our website and send us two of your best papers.\n")
                        .append("<br>\n")
                        .append("<br>\n")
                        .append("Unfortunately, our board of members has decided to reject your request. We wish you all the best.\t\n")
                        .append("</p>\n")
                        .append("<h4>Best regards!</h4>")
                        .toString();
                break;
            case NEED_MORE_INFO:
                content = new StringBuilder().append("<h1>Thank you for sending us a membership request</h1>\n")
                        .append("<p>\n")
                        .append("We appreciate you taking the time to register on our website and send us two of your best papers.\n")
                        .append("<br>\n")
                        .append("<br>\n")
                        .append("Your work is interesting, our board of members has decided to take some time and give you a chance to provide us some more papers.\t\n")
                        .append("We expect for you to provide us some more papers within the set deadline.\n")
                        .append("Please visit this <a href='http://localhost:4200/login'>link</a> ")
                        .append("</p>\n")
                        .append("<h4>Best regards!</h4>")
                        .toString();
                break;
            default:
                content = "I dont know what to do with you my man.";
                break;
        }
        return content;
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
    public void notifyWriterToSendNewBook(Book book) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Send new book - UPP");
        message.setFrom("UPP-App");
        message.setTo(book.getWriter().getEmail());
        message.setText("Book - " + book.getTitle() + " was reviewed and you are asked to change it and send new version");
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

    private void send(String email, ConfirmationToken confirmationToken, List<PdfResourceDto> userPapers) {
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
	public void sendChiefEditorPlagiarismNotification(Complaint complaint) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Plagiarism detected");
        message.setFrom("UPP-App");
        message.setTo(complaint.getChiefEditor().getEmail());

        Book plagiarised = complaint.getPlagiarisedBook();
        String author = plagiarised.getWriter().getFirstName() + " " + plagiarised.getWriter().getLastName();

        message.setText("Book - " + plagiarised.getTitle() + " by " + author + " has been reported to be plagiarised. Choose editors");
        mailSender.send(message);
	}

    @Override
    public void sendBetaReadersCommentsToAuthor(Book book) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Beta readers comments - UPP");
        message.setFrom("UPP-App");
        message.setTo(book.getWriter().getEmail());

        StringBuilder comments = new StringBuilder();

        for (BetaReaderComment comment : book.getBetaReadersComments()) {
            comments.append(comment.getReader().getFirstName() + " " + comment.getReader().getLastName() + " --> ");
            comments.append(comment.getComment());
            comments.append("\n");
        }

        message.setText(comments.toString());

        mailSender.send(message);
    }

    @Override
    public void sendChiefEditorNotReview(ChiefEditor chiefEditor) {

    }

    @Override
    public void notifyEditorToReviewPlagiarism(Editor editor, Complaint complaint) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Plagiarism review");
        message.setFrom("UPP-App");
        message.setTo(editor.getEmail());

        Book plagiarised = complaint.getPlagiarisedBook();
        String author = plagiarised.getWriter().getFirstName() + " " + plagiarised.getWriter().getLastName();

        message.setText("Hello " + editor.getFirstName() + " " + editor.getLastName() + ". Book - " + plagiarised.getTitle() + " by " + author + " has been reported to be plagiarised. You are assigned to review it");
        mailSender.send(message);
    }

    @Override
    public void notifyChiefEditorToFindReplacement(String editor, Complaint complaint) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Plagiarism - find replacement for editor");
        message.setFrom("UPP-App");
        message.setTo(complaint.getChiefEditor().getEmail());

        Book plagiarised = complaint.getPlagiarisedBook();
        String author = plagiarised.getWriter().getFirstName() + " " + plagiarised.getWriter().getLastName();

        message.setText("Book - " + plagiarised.getTitle() + " by " + author + " has been reported to be plagiarised. " +
                "Find replacement for editor: " + editor);
        mailSender.send(message);
    }

    @Override
    public void notifyBoardMemberToReviewPlagiarism(User boardMember, Complaint complaint) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Plagiarism - board member review");
        message.setFrom("UPP-App");
        message.setTo(boardMember.getEmail());

        Book plagiarised = complaint.getPlagiarisedBook();
        String author = plagiarised.getWriter().getFirstName() + " " + plagiarised.getWriter().getLastName();

        StringBuilder body = new StringBuilder();
        body.append("Book - " + plagiarised.getTitle() + " by " + author + " has been reported to be plagiarised.\n");
        body.append("You are assigned to review it.\nEditors comments:\n");

        for (CompliantAssignment assignment : complaint.getCompliantAssignments()) {
            body.append(assignment.getEditor().getUsername() + ": " + assignment.getNotes() + "\n");
        }

        message.setText(body.toString());
        mailSender.send(message);
    }

    @Override
    public void notifyAuthorBookIsNotPlagiarised(Complaint complaint) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Plagiarism - book is not plagiarised");
        message.setFrom("UPP-App");
        message.setTo(complaint.getPlagiarisedBook().getWriter().getEmail());

        Book plagiarised = complaint.getPlagiarisedBook();
        String author = plagiarised.getWriter().getFirstName() + " " + plagiarised.getWriter().getLastName();

        message.setText("Book - " + plagiarised.getTitle() + " by " + author + " has been reported to be plagiarised. " +
                "We reviewed it and decided that it is NOT plagiarised!");
        mailSender.send(message);
    }

    @Override
    public void notifyAuthorBookIsPlagiarised(Complaint complaint) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Plagiarism - book is plagiarised");
        message.setFrom("UPP-App");
        message.setTo(complaint.getPlagiarisedBook().getWriter().getEmail());

        Book plagiarised = complaint.getPlagiarisedBook();
        String author = plagiarised.getWriter().getFirstName() + " " + plagiarised.getWriter().getLastName();

        message.setText("Book - " + plagiarised.getTitle() + " by " + author + " has been reported to be plagiarised. " +
                "We reviewed it and decided that it is plagiarised!");
        mailSender.send(message);
    }

    @Override
    public void notifyChiefEditorToChooseEditorsAgain(Complaint complaint) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Plagiarism - choose editors again");
        message.setFrom("UPP-App");
        message.setTo(complaint.getChiefEditor().getEmail());

        Book plagiarised = complaint.getPlagiarisedBook();
        String author = plagiarised.getWriter().getFirstName() + " " + plagiarised.getWriter().getLastName();

        message.setText("Book - " + plagiarised.getTitle() + " by " + author + " has been reported to be plagiarised. " +
                "Board members haven't decided if book is plagiarised.\nYou need to choose editors again.");
        mailSender.send(message);
    }

    public void sendNotificationAboutHandwrite(Book book, String emailTo, PdfResourceDto handwrite) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("UPP-App");
            helper.setTo(emailTo);
            helper.setSubject("Book assignment - UPP");

            helper.setText("You have been assigned to read the book "
                    + book.getTitle()
                    + " by "
                    + book.getWriter().getFirstName()
                    + " "
                    + book.getWriter().getLastName());
            try {
                helper.addAttachment(handwrite.getFileName(), handwrite.getByteArrayResource());
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyEditorAboutFinishedLecturing(User chiefEditor, Book book) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Lecturer has finished - UPP");
        message.setFrom("UPP-App");
        message.setTo(chiefEditor.getEmail());
        String author = book.getWriter().getFirstName() + " " + book.getWriter().getLastName();
        message.setText("Book - " + book.getTitle() + " by " + author + " has passed lecturer review.");
        mailSender.send(message);
    }

}
