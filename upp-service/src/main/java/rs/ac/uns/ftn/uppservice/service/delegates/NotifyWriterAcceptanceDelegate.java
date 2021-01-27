package rs.ac.uns.ftn.uppservice.service.delegates;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.service.WriterService;

@Component
@RequiredArgsConstructor
public class NotifyWriterAcceptanceDelegate implements JavaDelegate {

    private final WriterService writerService;

    @Override
    public void execute(DelegateExecution execution) {
        writerService.notifyAboutAcceptance(execution.getProcessInstanceId());
    }
}
