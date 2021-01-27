package rs.ac.uns.ftn.uppservice.service;

import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.uppservice.dto.request.CamundaFormSubmitDTO;

import java.io.File;
import java.io.IOException;

public interface ProcessEngineService {

    /**
     * Method used to submit registration form data with additional information about type of data submission
     *
     * @param data
     * @param betaGenres - true if data contains only genres for beta readers, false if not
     * @return
     */
    String submitForm(CamundaFormSubmitDTO data, boolean betaGenres);

    String submitForm(CamundaFormSubmitDTO data);

    /**
     * Method used to submit file and record that submission in camunda process
     *
     * @param taskId
     * @param file
     * @return
     */
    String submitFile(String taskId, MultipartFile file) throws IOException;

    /**
     * Method used to record decision about writer membership submitted by a board member.
     * @param data
     * @return
     */
    String submitDecision(CamundaFormSubmitDTO data);
}
