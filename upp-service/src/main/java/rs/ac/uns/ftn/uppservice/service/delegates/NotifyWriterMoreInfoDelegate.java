package rs.ac.uns.ftn.uppservice.service.delegates;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.service.WriterService;

import java.io.IOException;

import static rs.ac.uns.ftn.uppservice.common.constants.Constants.WORK_COUNT;

@Component
@RequiredArgsConstructor
public class NotifyWriterMoreInfoDelegate implements JavaDelegate {

    private final WriterService writerService;

    @Override
    public void execute(DelegateExecution execution) throws IOException {
        writerService.notifyAboutMoreInfo(execution.getProcessInstanceId());
        execution.setVariable(WORK_COUNT, 0);
    }
}
