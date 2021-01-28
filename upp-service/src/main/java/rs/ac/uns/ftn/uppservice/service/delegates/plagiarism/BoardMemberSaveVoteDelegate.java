package rs.ac.uns.ftn.uppservice.service.delegates.plagiarism;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;

import java.util.List;

public class BoardMemberSaveVoteDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        var votes = (List<String>) execution.getVariable(Constants.BOARD_MEMBERS_VOTES);
        var form = (List<FormSubmissionDto>) execution.getVariable(Constants.FORM_DATA);
        String vote = (String) form.get(0).getFieldValue();
        votes.add(vote);
    }
}
