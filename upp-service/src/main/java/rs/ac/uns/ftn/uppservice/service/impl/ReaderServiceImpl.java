package rs.ac.uns.ftn.uppservice.service.impl;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ApiRequestException;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ResourceNotFoundException;
import rs.ac.uns.ftn.uppservice.model.ConfirmationToken;
import rs.ac.uns.ftn.uppservice.model.Genre;
import rs.ac.uns.ftn.uppservice.model.Reader;
import rs.ac.uns.ftn.uppservice.model.User;
import rs.ac.uns.ftn.uppservice.repository.ConfirmationTokenRepository;
import rs.ac.uns.ftn.uppservice.repository.GenreRepository;
import rs.ac.uns.ftn.uppservice.repository.ReaderRepository;
import rs.ac.uns.ftn.uppservice.repository.UserRepository;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;
import rs.ac.uns.ftn.uppservice.service.ReaderService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private ConfirmationTokenRepository confTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private GenreRepository genreRepository;


    @Override
    public Reader findById(Long id) {
        return readerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reader doesn't exist"));
    }

    @Override
    public Reader findByUsername(String username) {
        return readerRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Reader doesn't exist"));
    }

    @Override
    public Reader add(List<FormSubmissionDto> formData, List<FormSubmissionDto> chooseGenresForm, String processInstanceId) {
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

        if (userRepository.findByUsername(reader.getUsername()) != null) {
            throw new ApiRequestException("User with that username already exist");
        }

        if (userRepository.findByEmail(reader.getEmail()).isPresent()) {
            throw new ApiRequestException("User with that email already exist");
        }

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

        return reader;
    }

    private Set<Genre> getGenresFromList(List<String> genreIds) {
        Set<Genre> genres = new HashSet<>();

        genreIds.stream().forEach(genreId -> {
//            Genre genre = genreRepository.findByName(genreName)
//                    .orElseThrow(() -> new ResourceNotFoundException("Genre with name " + genreName + " doesn't exist."));
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
    public void delete(Reader reader) {
        ConfirmationToken token = confTokenRepository.findByUser(reader);
        confTokenRepository.delete(token);
        identityService.deleteUser(reader.getUsername());
        userRepository.delete(reader);
    }

    @Override
    public void handlePenaltyPoints(String username) {
        Reader reader = readerRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("This reader doesn't exist"));

        reader.setPenaltyPoints(reader.getPenaltyPoints() + 1);

        if (reader.getPenaltyPoints() >= 5) {
            reader.setIsBetaReader(false);
            mailSenderService.notifyReaderLostBetaStatus(reader);
        }

        readerRepository.save(reader);
    }
}
