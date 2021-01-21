package rs.ac.uns.ftn.uppservice.service.delegates.publishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.service.PlagiarismService;

@Component
public class CheckIfPlagiarismDelegate implements JavaDelegate {

    @Autowired
    private PlagiarismService plagiarismService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Book book = (Book) execution.getVariable("book");
        plagiarismService.checkIfPlagiarised(book);
    }
}
