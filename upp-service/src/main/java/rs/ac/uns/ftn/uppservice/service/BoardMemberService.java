package rs.ac.uns.ftn.uppservice.service;

import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.model.BoardMember;

import java.util.List;

public interface BoardMemberService {

    List<org.camunda.bpm.engine.identity.User> notifyBoardMembers(String processInstanceId);
    String registerDecision(List<String> decision, String processInstanceId, String executionId);
}
