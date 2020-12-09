package rs.ac.uns.ftn.uppservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.camunda.bpm.engine.form.FormField;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormFieldsDto {

    private String taskId;
    private List<FormField> formFields;
    private String processInstanceId;
}
