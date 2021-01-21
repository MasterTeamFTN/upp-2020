package rs.ac.uns.ftn.uppservice.service.impl;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.service.PlagiarismService;

@Service
public class PlagiarismServiceImpl implements PlagiarismService {

    @Override
    public boolean checkIfPlagiarised(Book book) {
        return false;
    }
}
