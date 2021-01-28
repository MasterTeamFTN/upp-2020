package rs.ac.uns.ftn.uppservice.service.delegates.plagiarism;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;

import java.util.ArrayList;

public class PrepareVotingDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        execution.setVariable(Constants.BOARD_MEMBERS_VOTES, new ArrayList<String>());
    }
}
