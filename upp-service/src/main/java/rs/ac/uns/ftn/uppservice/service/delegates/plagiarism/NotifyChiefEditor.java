package rs.ac.uns.ftn.uppservice.service.delegates.plagiarism;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.service.BookService;

import java.util.List;

@Component
public class NotifyChiefEditor implements JavaDelegate {

    @Autowired
    private BookService bookService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        
    }

}
