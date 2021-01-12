package rs.ac.uns.ftn.uppservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.common.mapper.CamundaUserMapper;
import rs.ac.uns.ftn.uppservice.model.BoardMember;
import rs.ac.uns.ftn.uppservice.model.ConfirmationToken;
import rs.ac.uns.ftn.uppservice.model.User;
import rs.ac.uns.ftn.uppservice.repository.BoardMemberRepository;
import rs.ac.uns.ftn.uppservice.repository.ConfirmationTokenRepository;
import rs.ac.uns.ftn.uppservice.service.BoardMemberService;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BoardMemberServiceImpl implements BoardMemberService {

    private final BoardMemberRepository boardMemberRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final MailSenderService mailSenderService;
    private final IdentityService identityService;
    private final RuntimeService runtimeService;
    private final CamundaUserMapper camundaUserMapper;

    @Override
    public List<org.camunda.bpm.engine.identity.User> notifyBoardMembers(String processInstanceId) {
        User user = (User) runtimeService.getVariable(processInstanceId, "requestedMember");

        ConfirmationToken confirmationToken = new ConfirmationToken(user, processInstanceId);
        confirmationTokenRepository.save(confirmationToken);

        List<BoardMember> boardMembers = boardMemberRepository.findAll();
        List<org.camunda.bpm.engine.identity.User> camundaUsers =
                boardMembers.stream().map(boardMember -> {
                    org.camunda.bpm.engine.identity.User camundaUser = camundaUserMapper.entityToDto(boardMember);
                    identityService.saveUser(camundaUser);
                    return camundaUser;
                }).collect(Collectors.toList());

        List<String> emails = boardMembers
                .stream()
                .map(BoardMember::getEmail)
                .collect(Collectors.toList());

        mailSenderService.sendBoardMemberNotification(emails, confirmationToken, getUserPapers(user));

        // runtimeService
        runtimeService.setVariable(confirmationToken.getProcessInstanceId(), "boardMembers", boardMembers);
        runtimeService.setVariable(confirmationToken.getProcessInstanceId(), "decision", "reject"); // initial value

        return camundaUsers;
    }

    /**
     * U slučaju da polovina ili više članova odbora smatra da pisac nije podoban za članstvo, pisac se notificira i proces se završava.
     * U slučaju da je bar jedan član odbora tražio još materijala, pisac se notificira i zadaje mu se rok u kom je dužan da dostavi još svojih radova.
     *
     * @param decisions
     * @param processInstanceId
     */
    @Override
    public String registerDecision(List<String> decisions, String processInstanceId, String executionId) {
        //Todo: logic for decision such as, how many rejections, need more info etc
        Map<String, Long> count =
                decisions.stream().collect(Collectors.groupingBy(decision -> decision, Collectors.counting()));

        Map<String, Long> decisionMap = Stream.of(new Object[][] {
                { "reject", count.containsKey("Reject") ? count.get("Reject") : 0L },
                { "approve", count.containsKey("Approve") ? count.get("Approve") : 0L },
                { "needMoreInfo", count.containsKey("NeedMoreInfo") ? count.get("NeedMoreInfo") : 0L },
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Long) data[1]));


        String finalDecision = (decisionMap.get("reject") >= decisions.size() / 2.0) ? "reject" : "approve";
        finalDecision = (decisionMap.get("needMoreInfo") >= 1) ? "needMoreInfo" : finalDecision;

        runtimeService.setVariable(processInstanceId, "decision", finalDecision);
        removeBoardMembers(executionId);
        return finalDecision;
    }

    private void removeBoardMembers(String executionId) {
        List<org.camunda.bpm.engine.identity.User> boardMembers = (List<org.camunda.bpm.engine.identity.User>)
                runtimeService.getVariable(executionId, "boardMembers");

        boardMembers.stream().peek(boardMember -> {
            identityService.deleteUser(boardMember.getId());

        });
    }

    /**
     * Todo: read all files from papers/{username}
     *
     * @param user
     * @return
     */
    private List<ByteArrayResource> getUserPapers(User user) {
        List<ByteArrayResource> userPapers = new ArrayList<ByteArrayResource>();

        user.getRegistrationPapers().stream().forEach(path -> {
            try {
                byte[] pdf = Files.readAllBytes(Paths.get(path));
                String[] tokens = path.split("/");
                String fileName = tokens[tokens.length - 1];

                userPapers.add(new ByteArrayResource(pdf, fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return userPapers;
    }


}
