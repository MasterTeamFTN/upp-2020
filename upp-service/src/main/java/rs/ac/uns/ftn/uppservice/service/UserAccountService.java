package rs.ac.uns.ftn.uppservice.service;

import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.model.Reader;
import rs.ac.uns.ftn.uppservice.model.User;
import rs.ac.uns.ftn.uppservice.model.Writer;

import java.util.List;

/**
 * Class used to reduce duplicated code by providing registration and other services that are in common for
 * both readers and writers.
 */
public interface UserAccountService {

    User add(List<FormSubmissionDto> formData, List<FormSubmissionDto> chooseGenresForm, String processInstanceId, boolean isReader);
    void activateAccount(String token);
    void delete(Reader reader);
    void delete(Writer writer);
}
