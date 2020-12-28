package rs.ac.uns.ftn.uppservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ApiRequestException;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ResourceNotFoundException;
import rs.ac.uns.ftn.uppservice.model.*;
import rs.ac.uns.ftn.uppservice.repository.ConfirmationTokenRepository;
import rs.ac.uns.ftn.uppservice.repository.GenreRepository;
import rs.ac.uns.ftn.uppservice.repository.UserRepository;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;
import rs.ac.uns.ftn.uppservice.service.UserAccountService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;
    private final TaskService taskService;
    private final IdentityService identityService;
    private final GenreRepository genreRepository;

    @Override
    public User add(List<FormSubmissionDto> formData, List<FormSubmissionDto> chooseGenresForm, String processInstanceId, boolean isReader) {
        Reader reader = new Reader();
        Writer writer = new Writer();
        String password = "";
        String username = "";
        String email = "";

        if (isReader) {
            reader = mapToReader(formData, chooseGenresForm);
            username = reader.getUsername();
            email = reader.getEmail();
        } else {
            writer = mapToWriter(formData);
            username = writer.getUsername();
            email = writer.getEmail();
        }

        if (userRepository.findByUsername(username) != null) {
            throw new ApiRequestException("User with that username already exist");
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new ApiRequestException("User with that email already exist");
        }
        if(isReader) {
            reader.setEnabled(false);
            reader = userRepository.save(reader);

            // Add user to the Camunda table
            org.camunda.bpm.engine.identity.User camundaUser = identityService.newUser(reader.getUsername());
            camundaUser.setEmail(reader.getEmail());
            camundaUser.setFirstName(reader.getFirstName());
            camundaUser.setLastName(reader.getLastName());
            camundaUser.setPassword(password);
            identityService.saveUser(camundaUser);

            ConfirmationToken token = new ConfirmationToken(reader, processInstanceId);
            confTokenRepository.save(token);

            mailSenderService.sendRegistrationMail(token);
        } else {
            writer.setEnabled(false);
            writer = userRepository.save(writer);

            // Add user to the Camunda table
            org.camunda.bpm.engine.identity.User camundaUser = identityService.newUser(writer.getUsername());
            camundaUser.setEmail(writer.getEmail());
            camundaUser.setFirstName(writer.getFirstName());
            camundaUser.setLastName(writer.getLastName());
            camundaUser.setPassword(password);
            identityService.saveUser(camundaUser);

            ConfirmationToken token = new ConfirmationToken(writer, processInstanceId);
            confTokenRepository.save(token);

            mailSenderService.sendRegistrationMail(token);

        }
        return isReader? reader: writer;
    }


    private Reader mapToReader(List<FormSubmissionDto> formData, List<FormSubmissionDto> chooseGenresForm) {
        Reader reader = new Reader();
        String password = "";
        for (FormSubmissionDto field : formData) {
            if(field.getFieldId().equals("FormField_username")) reader.setUsername((String) field.getFieldValue());
            if(field.getFieldId().equals("FormField_password")) {
                password = (String) field.getFieldValue();
                reader.setPassword(passwordEncoder.encode(password));
            }

            if(field.getFieldId().equals("FormField_firstName")) reader.setFirstName((String) field.getFieldValue());
            if(field.getFieldId().equals("FormField_lastName")) reader.setLastName((String) field.getFieldValue());
            if(field.getFieldId().equals("FormField_email")) reader.setEmail((String) field.getFieldValue());
            if(field.getFieldId().equals("FormField_isBetaReader")) reader.setIsBetaReader((Boolean) field.getFieldValue());
            if(field.getFieldId().equals("FormField_cityCountry")) reader.setCityCountry((String) field.getFieldValue());

            if(field.getFieldId().equals("FormField_genres")) {
                reader.setGenres(getGenresFromList((List<String>) field.getFieldValue()));
            }
        }
        // Set beta genres if user is beta-reader
        if (chooseGenresForm != null) {
            FormSubmissionDto field = chooseGenresForm.get(0); // We know that there is only one element (1 multi select form field)
            List<String> genreNames = (List<String>) field.getFieldValue();
            genreNames.stream().forEach(System.out::println);
            reader.setBetaGenres(getGenresFromList((List<String>) field.getFieldValue()));
        }
        return reader;
    }

    private Writer mapToWriter(List<FormSubmissionDto> formData) {
        Writer writer = new Writer();
        String password = "";
        for (FormSubmissionDto field : formData) {
            if(field.getFieldId().equals("FormField_username")) writer.setUsername((String) field.getFieldValue());
            if(field.getFieldId().equals("FormField_password")) {
                password = (String) field.getFieldValue();
                writer.setPassword(passwordEncoder.encode(password));
            }

            if(field.getFieldId().equals("FormField_firstName")) writer.setFirstName((String) field.getFieldValue());
            if(field.getFieldId().equals("FormField_lastName")) writer.setLastName((String) field.getFieldValue());
            if(field.getFieldId().equals("FormField_email")) writer.setEmail((String) field.getFieldValue());
            if(field.getFieldId().equals("FormField_cityCountry")) writer.setCityCountry((String) field.getFieldValue());

            if(field.getFieldId().equals("FormField_genres")) {
                writer.setGenres(getGenresFromList((List<String>) field.getFieldValue()));
            }
        }
        return writer;
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
    public void delete(Reader reader) {
        ConfirmationToken token = confTokenRepository.findByUser(reader);
        confTokenRepository.delete(token);
        identityService.deleteUser(reader.getUsername());
        userRepository.delete(reader);
    }

    @Override
    public void delete(Writer writer) {
        ConfirmationToken token = confTokenRepository.findByUser(writer);
        confTokenRepository.delete(token);
        identityService.deleteUser(writer.getUsername());
        userRepository.delete(writer);
    }

    private Set<Genre> getGenresFromList(List<String> genresNames) {
        Set<Genre> genres = new HashSet<>();

        genresNames.stream().forEach(genreName -> {
            Genre genre = genreRepository.findByName(genreName)
                    .orElseThrow(() -> new ResourceNotFoundException("Genre with name " + genreName + " doesn't exist."));
            genres.add(genre);
        });

        return genres;
    }
}
