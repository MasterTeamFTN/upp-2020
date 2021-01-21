package rs.ac.uns.ftn.uppservice.service.delegates.publishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.service.BookService;

import java.util.List;

@Component
public class SaveUpdatedBookDelegate implements JavaDelegate {

    @Autowired
    private BookService bookService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<FormSubmissionDto> form = (List<FormSubmissionDto>) execution.getVariable("formData");

        // TODO: dodati jos logiku za updateovanje fajla

        for (FormSubmissionDto field : form) {
            if (field.getFieldId().equals("FormField_makeChanges")) {
                // Writer doesn't want to make changes
                if (!Boolean.parseBoolean((String) field.getFieldValue())) {
                    System.out.println("Writer doesn't want to make changes");
                    return;
                }
            }
        }
    }
}
