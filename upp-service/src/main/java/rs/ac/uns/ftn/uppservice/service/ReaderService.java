package rs.ac.uns.ftn.uppservice.service;

import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.model.Reader;

import java.util.List;

public interface ReaderService {

    Reader findById(Long id);
    Reader findByUsername(String username);
    Reader add(List<FormSubmissionDto> formData, List<FormSubmissionDto> chooseGenresForm, String processInstanceId);
    void activateAccount(String token);
    void delete(Reader reader);
    void handlePenaltyPoints(String username);
}
