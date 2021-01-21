package rs.ac.uns.ftn.uppservice.service.delegates.publishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SaveBookFileDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        // TODO: implement this
        // Bilo bi dobro kad bi mogao samo 1 delegat da se koristi za svako cuvanje knjige
        System.out.println("TODO: Implement saving file");
    }
}
