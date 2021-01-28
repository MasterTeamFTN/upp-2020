package rs.ac.uns.ftn.uppservice.service;

import rs.ac.uns.ftn.uppservice.model.*;
import rs.ac.uns.ftn.uppservice.model.MembershipDecision;
import rs.ac.uns.ftn.uppservice.dto.response.WriterPaperResourceDto;

import java.util.List;

public interface MailSenderService {

    void sendRegistrationMail(ConfirmationToken token);

    void sendChiefEditorNewBookNotification(User chiefEditor, Book book);

    void sendWriterRejectBook(Book book, String reason);

    void notifyWriterToSendFullBook(Book book);

    void notifyWriterFullBookTimedOut(Book book);

    void notifyUserBookIsPlagiarised(Book book, String reason);

    void notifyReaderLostBetaStatus(Reader reader);

    void sendLecturersCommentsToAuthor(Book book, Suggestion suggestion);

    void sendChiefEditorCommentsToAuthor(Book book, Suggestion suggestion);

    void notifyBookIsPublished(Book book);

    void sendRejectBook(Book book);

    void sendBoardMemberNotification(List<String> emails, ConfirmationToken token, List<WriterPaperResourceDto> userPapers);

	void submitPlagiarismForm(ChiefEditor chiefEditor, Book originalBook, Book plagiat);

	void sendChiefEditorPlagiarismNotification(Complaint complaint);

    void sendDecisionToWriter(String emailTo, MembershipDecision decision, String processInstanceId);

    void sendBetaReadersCommentsToAuthor(Book book);

    void sendChiefEditorNotReview(ChiefEditor chiefEditor);

    void notifyEditorToReviewPlagiarism(Editor editor, Complaint complaint);

    void notifyChiefEditorToFindReplacement(String editor, Complaint complaint);

    void notifyBoardMemberToReviewPlagiarism(User boardMember, Complaint complaint);

    void notifyAuthorBookIsNotPlagiarised(Complaint complaint);

    void notifyAuthorBookIsPlagiarised(Complaint complaint);

    void notifyChiefEditorToChooseEditorsAgain(Complaint complaint);
}
