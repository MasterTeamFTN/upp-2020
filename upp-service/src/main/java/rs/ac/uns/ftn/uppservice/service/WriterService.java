package rs.ac.uns.ftn.uppservice.service;

import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.model.Writer;

import java.util.List;

public interface WriterService {

    /**
     * Method used to save new writer to the database.
     *
     * @param formData
     * @param processInstanceId
     * @return
     */
    Writer add(List<FormSubmissionDto> formData, String processInstanceId);

    void activateAccount(String token);

    void delete(Writer writer);
}
