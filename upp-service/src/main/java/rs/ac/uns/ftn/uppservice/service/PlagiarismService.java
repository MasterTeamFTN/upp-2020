package rs.ac.uns.ftn.uppservice.service;

import rs.ac.uns.ftn.uppservice.model.Book;

public interface PlagiarismService {

    boolean checkIfPlagiarised(Book book);
}
