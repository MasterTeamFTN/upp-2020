package rs.ac.uns.ftn.uppservice.service.delegates.plagiarism;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;

import java.util.List;

public class SetNewEditorsDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<String> newEditors = (List<String>) execution.getVariable(Constants.NEW_EDITORS);
        List<String> editors = (List<String>) execution.getVariable(Constants.EDITORS);

        newEditors.stream().forEach(System.out::println);

        editors = newEditors;

        // todo: jel treba setovati opet editore ?
    }
}
