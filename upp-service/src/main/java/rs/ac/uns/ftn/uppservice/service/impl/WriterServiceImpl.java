package rs.ac.uns.ftn.uppservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.IdentityService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ApiRequestException;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ResourceNotFoundException;
import rs.ac.uns.ftn.uppservice.model.ConfirmationToken;
import rs.ac.uns.ftn.uppservice.model.Genre;
import rs.ac.uns.ftn.uppservice.model.User;
import rs.ac.uns.ftn.uppservice.model.Writer;
import rs.ac.uns.ftn.uppservice.repository.ConfirmationTokenRepository;
import rs.ac.uns.ftn.uppservice.repository.GenreRepository;
import rs.ac.uns.ftn.uppservice.repository.UserRepository;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;
import rs.ac.uns.ftn.uppservice.service.WriterService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class WriterServiceImpl implements WriterService {

    private final PasswordEncoder passwordEncoder;
    private final GenreRepository genreRepository;
    private final UserRepository userRepository;
    private final IdentityService identityService;
    private final ConfirmationTokenRepository confTokenRepository;
    private final MailSenderService mailSenderService;

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
}
