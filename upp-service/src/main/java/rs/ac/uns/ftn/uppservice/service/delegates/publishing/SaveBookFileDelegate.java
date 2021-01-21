package rs.ac.uns.ftn.uppservice.service.delegates.publishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.model.Book;

import java.util.List;

@Component
public class SaveBookFileDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("TODO: Implement saving file");

        List<FormSubmissionDto> formData = (List<FormSubmissionDto>) execution.getVariable("pdf");
        Book book = (Book) execution.getVariable("book");

        // knjige cuvaj u resources/books/username/knjiga+random.uuid.pdf


    }
}
