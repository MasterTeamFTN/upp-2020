package rs.ac.uns.ftn.uppservice.service.delegates;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.model.Writer;
import rs.ac.uns.ftn.uppservice.service.WriterService;

@RequiredArgsConstructor
@Component
public class WriterRegistrationTimedOutDelegate implements JavaDelegate {

    private WriterService writerService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Writer writer = (Writer) delegateExecution.getVariable("writer");
        writerService.delete(writer);
    }

}