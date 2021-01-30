package rs.ac.uns.ftn.uppservice.service.delegates.plagiarism;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;

import java.util.List;

public class CountVotesDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        var votes = (List<String>) execution.getVariable(Constants.BOARD_MEMBERS_VOTES);

        int yesVotes = 0;
        int noVotes = 0;

        for (String vote : votes) {
            if (vote.equals("1")) {
                ++yesVotes;
            } else {
                ++noVotes;
            }
        }

        execution.setVariable(Constants.VOTES_TOTAL, votes.size());
        execution.setVariable(Constants.NUM_YES_VOTES, yesVotes);
        execution.setVariable(Constants.NUM_NO_VOTES, noVotes);
    }
}
