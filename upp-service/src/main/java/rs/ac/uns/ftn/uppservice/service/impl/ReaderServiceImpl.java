package rs.ac.uns.ftn.uppservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.dto.response.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.model.ConfirmationToken;
import rs.ac.uns.ftn.uppservice.model.Reader;
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


    @Override
    public Reader add(List<FormSubmissionDto> formData) {
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

        reader = userRepository.save(reader);

        ConfirmationToken token = new ConfirmationToken(reader);
        confTokenRepository.save(token);

        mailSenderService.sendRegistrationMail(token);

        return reader;
    }
}
