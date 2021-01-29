package rs.ac.uns.ftn.uppservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.model.*;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ApiRequestException;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ResourceNotFoundException;
import rs.ac.uns.ftn.uppservice.repository.AuthorityRepository;
import rs.ac.uns.ftn.uppservice.repository.ConfirmationTokenRepository;
import rs.ac.uns.ftn.uppservice.repository.GenreRepository;
import rs.ac.uns.ftn.uppservice.repository.UserRepository;
import rs.ac.uns.ftn.uppservice.service.FileService;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;
import rs.ac.uns.ftn.uppservice.service.WriterService;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static rs.ac.uns.ftn.uppservice.common.constants.Constants.REQUESTED_MEMBER;

@RequiredArgsConstructor
@Service
public class WriterServiceImpl implements WriterService {

    private final PasswordEncoder passwordEncoder;
    private final GenreRepository genreRepository;
    private final UserRepository userRepository;
    private final IdentityService identityService;
    private final ConfirmationTokenRepository confTokenRepository;
    private final MailSenderService mailSenderService;
    private final FileService fileService;
    private final AuthorityRepository authorityRepository;

    private final RuntimeService runtimeService;
    private final TaskService taskService;

    @Override
    public Writer add(List<FormSubmissionDto> formData, String processInstanceId) {
        Writer writer = new Writer();
        String password = "";

        for (FormSubmissionDto field : formData) {
            if (field.getFieldId().equals("FormField_username")) writer.setUsername((String) field.getFieldValue());
            if (field.getFieldId().equals("FormField_password")) {
                password = (String) field.getFieldValue();
                writer.setPassword(passwordEncoder.encode(password));
            }

            if (field.getFieldId().equals("FormField_firstName")) writer.setFirstName((String) field.getFieldValue());
            if (field.getFieldId().equals("FormField_lastName")) writer.setLastName((String) field.getFieldValue());
            if (field.getFieldId().equals("FormField_email")) writer.setEmail((String) field.getFieldValue());
            if (field.getFieldId().equals("FormField_cityCountry"))
                writer.setCityCountry((String) field.getFieldValue());

            if (field.getFieldId().equals("FormField_genres")) {
                writer.setGenres(getGenresFromList((List<String>) field.getFieldValue()));
            }
        }

        if (userRepository.findByUsername(writer.getUsername()) != null) {
            throw new ApiRequestException("User with that username already exist");
        }

        if (userRepository.findByEmail(writer.getEmail()).isPresent()) {
            throw new ApiRequestException("User with that email already exist");
        }

        writer.setEnabled(false);
        writer.setMember(false);
        writer.setMembershipDecision(MembershipDecision.NOT_SUBMITTED_YET);

        Authority writer_authority = authorityRepository.findByName("ROLE_WRITER").orElseThrow(EntityNotFoundException::new);
        writer.setAuthorities(Arrays.asList(writer_authority));
        writer = userRepository.save(writer);

        org.camunda.bpm.engine.identity.User camundaUser = identityService.newUser(writer.getUsername());
        camundaUser.setEmail(writer.getEmail());
        camundaUser.setFirstName(writer.getFirstName());
        camundaUser.setLastName(writer.getLastName());
        camundaUser.setPassword(password);
        identityService.saveUser(camundaUser);

        ConfirmationToken token = new ConfirmationToken(writer, processInstanceId);
        confTokenRepository.save(token);

        mailSenderService.sendRegistrationMail(token);

        return writer;
    }

    private Set<Genre> getGenresFromList(List<String> genresNames) {
        Set<Genre> genres = new HashSet<>();

        genresNames.stream().forEach(genreId -> {
            Long id = Long.valueOf(genreId);
            Genre genre = genreRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Genre with id " + genreId + " doesn't exist."));
            genres.add(genre);
        });

        return genres;
    }

    @Override
    public void activateAccount(String token) {
        ConfirmationToken confirmationToken = confTokenRepository.findByToken(token);

        if (confirmationToken == null) {
            throw new ResourceNotFoundException("Confirmation token doesn't exist.");
        }

        if (confirmationToken.isUsed()) {
            throw new ApiRequestException("This token has been already used.");
        }

        User user = confirmationToken.getUser();

        user.setEnabled(true);
        userRepository.save(user);
        confirmationToken.setUsed(true);
        confTokenRepository.save(confirmationToken);
    }

    @Override
    public void delete(Writer writer) {
        ConfirmationToken token = confTokenRepository.findByUser(writer);
        confTokenRepository.delete(token);
        identityService.deleteUser(writer.getUsername());
        userRepository.delete(writer);
    }

    @Override
    public void notifyAboutRejection(String processInstanceId) {
        Writer writer = (Writer) runtimeService.getVariable(processInstanceId, REQUESTED_MEMBER);
        writer.setMember(false);
        writer.setMembershipDecision(MembershipDecision.REJECT);
        userRepository.save(writer);
        mailSenderService.sendDecisionToWriter(writer.getEmail(), MembershipDecision.REJECT, processInstanceId);
    }

    @Override
    public void notifyAboutAcceptance(String processInstanceId) {
        Writer writer = (Writer) runtimeService.getVariable(processInstanceId, REQUESTED_MEMBER);
        writer.setMembershipDecision(MembershipDecision.APPROVE);
        writer = userRepository.save(writer);
        runtimeService.setVariable(processInstanceId, REQUESTED_MEMBER, writer);
        mailSenderService.sendDecisionToWriter(writer.getEmail(), MembershipDecision.APPROVE, processInstanceId);
    }

    @Override
    public void notifyAboutMoreInfo(String processInstanceId) throws IOException {
        Writer writer = (Writer) runtimeService.getVariable(processInstanceId, REQUESTED_MEMBER);
        writer.setMembershipDecision(MembershipDecision.NEED_MORE_INFO);
        writer.getRegistrationPapers().clear();
        writer = userRepository.save(writer);
        runtimeService.setVariable(processInstanceId, REQUESTED_MEMBER, writer);
        fileService.removeFiles(writer.getUsername());

        mailSenderService.sendDecisionToWriter(writer.getEmail(), MembershipDecision.NEED_MORE_INFO, processInstanceId);
    }

    @Override
    public void activateMembership(String processInstanceId) {
        Writer writer = (Writer) runtimeService.getVariable(processInstanceId, REQUESTED_MEMBER);
        writer.setMember(true);
        userRepository.save(writer);
    }


}
