package rs.ac.uns.ftn.uppservice.service;

import rs.ac.uns.ftn.uppservice.model.BoardMember;

import java.util.List;

public interface BoardMemberService {

    List<BoardMember> findAll();

    /**
     * Method used to send notifications to all board members about user membership request
     *
     * @param processInstanceId
     * @return
     */
    List<org.camunda.bpm.engine.identity.User> notifyBoardMembers(String processInstanceId);

    /**
     * Method used to calculate final decision based on list of board member decisions.
     *
     * @param decision
     * @param processInstanceId
     * @param executionId
     * @return
     */
    String registerDecision(List<String> decision, String processInstanceId, String executionId);
}
