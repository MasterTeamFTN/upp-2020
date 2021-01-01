package rs.ac.uns.ftn.uppservice.service;

import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.uppservice.dto.request.CamundaFormSubmitDTO;

import java.io.File;

public interface ProcessEngineService {

    String submitForm(CamundaFormSubmitDTO data);

    /**
     * Method used to submit file and record that submission in camunda process
     * @param taskId
     * @param file
     * @return
     */
    String submitFile(String taskId, MultipartFile file, File convertedFile);
}
