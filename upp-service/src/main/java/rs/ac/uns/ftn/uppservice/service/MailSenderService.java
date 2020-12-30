package rs.ac.uns.ftn.uppservice.service;

import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.model.ConfirmationToken;
import rs.ac.uns.ftn.uppservice.model.User;

import java.util.List;

public interface MailSenderService {

    void sendRegistrationMail(ConfirmationToken token);

    void sendBoardMemberNotification(List<String> emails, ConfirmationToken token);

    void sendChiefEditorNewBookNotification(User chiefEditor, Book book);

    void sendWriterRejectBook(Book book, String reason);

    void notifyWriterToSendFullBook(Book book);

    void notifyWriterFullBookTimedOut(Book book);
}
