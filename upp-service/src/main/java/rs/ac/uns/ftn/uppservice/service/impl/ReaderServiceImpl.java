package rs.ac.uns.ftn.uppservice.service.impl;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.dto.response.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ApiRequestException;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ResourceNotFoundException;
import rs.ac.uns.ftn.uppservice.model.ConfirmationToken;
import rs.ac.uns.ftn.uppservice.model.Reader;
import rs.ac.uns.ftn.uppservice.model.User;
import rs.ac.uns.ftn.uppservice.repository.ConfirmationTokenRepository;
import rs.ac.uns.ftn.uppservice.repository.UserRepository;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;
import rs.ac.uns.ftn.uppservice.service.ReaderService;

import java.util.List;

@Service
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenRepository confTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private TaskService taskService;


    @Override
    public Reader add(List<FormSubmissionDto> formData, String processInstanceId) {
        Reader reader = new Reader();

        for (FormSubmissionDto field : formData) {
            // TODO: Add data validation

            if(field.getFieldId().equals("FormField_username")) reader.setUsername(field.getFieldValue());
            if(field.getFieldId().equals("FormField_password")) reader.setPassword(passwordEncoder.encode(field.getFieldValue()));
            if(field.getFieldId().equals("FormField_firstName")) reader.setFirstName(field.getFieldValue());
            if(field.getFieldId().equals("FormField_lastName")) reader.setLastName(field.getFieldValue());
            if(field.getFieldId().equals("FormField_email")) reader.setEmail(field.getFieldValue());
            if(field.getFieldId().equals("FormField_isBetaReader")) reader.setIsBetaReader(Boolean.parseBoolean(field.getFieldValue()));
            if(field.getFieldId().equals("FormField_cityCountry")) reader.setCityCountry(field.getFieldValue());

            // TODO: fix adding genres
            //if(field.getFieldId().equals("FormField_genres")) reader.setGenres(field.getFieldValue());
        }

        reader.setEnabled(false);

        reader = userRepository.save(reader);

        ConfirmationToken token = new ConfirmationToken(reader, processInstanceId);
        confTokenRepository.save(token);

        mailSenderService.sendRegistrationMail(token);

        return reader;
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

        Task confirmationTask = taskService.createTaskQuery().processInstanceId(
                confirmationToken.getProcessInstanceId())
                .active().singleResult();

        taskService.complete(confirmationTask.getId());
    }
}
