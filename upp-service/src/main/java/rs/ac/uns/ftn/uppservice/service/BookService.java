package rs.ac.uns.ftn.uppservice.service;

import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.dto.response.BookDto;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.model.Suggestion;

import java.util.List;

public interface BookService {

    Book findById(Long id);
    Book submitInitForm(List<FormSubmissionDto> formData, String processInstanceId);
    void rejectFirstReview(List<FormSubmissionDto> formData, Book book);
    void rejectAfterTimeOut(Book book);
    void reject(Long bookId);
    void markBookAsPlagiarised(Book book);
    Book saveFullBookData(List<FormSubmissionDto> formData, Long bookId);
    void addBetaReadersComments(Long bookId, String readerUsername, String comment);
    Suggestion addLecturersComments(Long bookId, String comment);
    Suggestion addChiefEditorsComments(Long bookId, String comment);
    void publish(Long bookId);

    /**
     * Method provides all books from database
     * @return
     */
    List<BookDto> getAll();

    /**
     * Method used to set isReviewSubmitted flag to true
     */
    Book registerReviewSubmission(Book book);

    /**
     * Method provides all books that have author with passed username
     *
     * @param username
     * @return
     */
    List<BookDto> getMyBooks(String username);

}
