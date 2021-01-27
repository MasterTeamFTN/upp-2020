package rs.ac.uns.ftn.uppservice.service.delegates;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.model.Reader;
import rs.ac.uns.ftn.uppservice.service.ReaderService;

import static rs.ac.uns.ftn.uppservice.common.constants.Constants.READER;

@Component
@RequiredArgsConstructor
public class RegistrationTimedOutDelegate implements JavaDelegate {

    private final ReaderService readerService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Reader reader = (Reader) delegateExecution.getVariable(READER);
        readerService.delete(reader);
    }

}
