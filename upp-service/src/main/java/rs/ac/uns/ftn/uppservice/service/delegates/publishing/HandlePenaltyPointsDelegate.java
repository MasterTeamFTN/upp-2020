package rs.ac.uns.ftn.uppservice.service.delegates.publishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.service.ReaderService;

@Component
public class HandlePenaltyPointsDelegate implements JavaDelegate {

    @Autowired
    private ReaderService readerService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String username = (String) execution.getVariable("betaReaderAssignee");
        System.out.println("Dobijas penalty poene!!!! " + username);
        readerService.handlePenaltyPoints(username);
    }
}
