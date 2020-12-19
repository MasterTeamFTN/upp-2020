package rs.ac.uns.ftn.uppservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormSubmissionDto implements Serializable {

    private String fieldId;
    private Object fieldValue;

}
