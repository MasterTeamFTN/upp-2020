package rs.ac.uns.ftn.uppservice.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.uppservice.dto.response.FormSubmissionDto;

import java.util.List;

@Data
@NoArgsConstructor
public class ReaderRegistrationSubmitDTO {

    private String taskId;
    private List<FormSubmissionDto> formData;
}
