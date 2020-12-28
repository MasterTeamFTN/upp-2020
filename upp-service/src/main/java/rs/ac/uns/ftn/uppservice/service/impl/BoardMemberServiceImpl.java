package rs.ac.uns.ftn.uppservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ApiRequestException;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ResourceNotFoundException;
import rs.ac.uns.ftn.uppservice.model.BoardMember;
import rs.ac.uns.ftn.uppservice.model.ConfirmationToken;
import rs.ac.uns.ftn.uppservice.repository.BoardMemberRepository;
import rs.ac.uns.ftn.uppservice.repository.ConfirmationTokenRepository;
import rs.ac.uns.ftn.uppservice.service.BoardMemberService;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardMemberServiceImpl implements BoardMemberService {

    private final BoardMemberRepository boardMemberRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final MailSenderService mailSenderService;
    private final IdentityService identityService;

    @Override
    public void notifyBoardMembers(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);

        if (confirmationToken == null) {
            throw new ResourceNotFoundException("Confirmation token doesn't exist.");
        }

        if (confirmationToken.isUsed()) {
            throw new ApiRequestException("This token has been already used.");
        }

        List<BoardMember> boardMembers = boardMemberRepository.findAll();
        List<String> emails = boardMembers
                .stream()
                .map(BoardMember::getEmail)
                .collect(Collectors.toList());

        mailSenderService.sendBoardMemberNotification(emails, confirmationToken);

        // runtimeService
        identityService.saveGroup((Group) boardMembers);
    }
}
