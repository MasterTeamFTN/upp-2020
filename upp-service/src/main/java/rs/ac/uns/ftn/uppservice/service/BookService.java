package rs.ac.uns.ftn.uppservice.service;

import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.model.Book;

import java.util.List;

public interface BookService {

    Book findById(Long id);
    Book submitInitForm(List<FormSubmissionDto> formData, String processInstanceId);
    void rejectFirstReview(List<FormSubmissionDto> formData, Book book);
    void rejectAfterTimeOut(Book book);
    void markBookAsPlagiarised(Book book);
    Book saveFullBookData(List<FormSubmissionDto> formData, Long bookId);
}
