package rs.ac.uns.ftn.uppservice.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CamundaFormSubmitDTO {

    private String taskId;
    private List<FormSubmissionDto> formData;
}
