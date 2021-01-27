package rs.ac.uns.ftn.uppservice.service.delegates.publishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.model.Reader;
import rs.ac.uns.ftn.uppservice.repository.ReaderRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class SetBetaReadersDelegate implements JavaDelegate {

    @Autowired
    private ReaderRepository readerRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<FormSubmissionDto> formData = (List<FormSubmissionDto>) execution.getVariable(Constants.FORM_DATA);
        List<String> readersIds = (List<String>) formData.get(0).getFieldValue();
        List<String> usernames = new ArrayList<>();

        readersIds.stream()
            .forEach(id -> {
                Reader reader = readerRepository.findById(Long.parseLong(id)).get();
                if (reader.getIsBetaReader())
                    usernames.add(reader.getUsername());
            });

        execution.setVariable(Constants.BETA_READERS, usernames);
    }

}
