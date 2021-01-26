package rs.ac.uns.ftn.uppservice.service.delegates.plagiarism;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.model.Reader;
import rs.ac.uns.ftn.uppservice.model.User;
import rs.ac.uns.ftn.uppservice.repository.ReaderRepository;
import rs.ac.uns.ftn.uppservice.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class SetEditorsDelegate implements JavaDelegate {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<FormSubmissionDto> formData = (List<FormSubmissionDto>) execution.getVariable("formData");
        List<String> readersIds = (List<String>) formData.get(0).getFieldValue();
        List<String> usernames = new ArrayList<>();

        readersIds.stream()
                .forEach(id -> {
                    User user = userRepository.findById(Long.parseLong(id)).get();
                    usernames.add(user.getUsername());
                    System.out.println(user.getUsername());
                });

        execution.setVariable("editors", usernames);
    }

}
