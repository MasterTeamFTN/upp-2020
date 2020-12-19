package rs.ac.uns.ftn.uppservice.camunda.validators;

import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;

import java.util.List;

public class CheckOneOrMoreMultiSelectValidator implements FormFieldValidator {

    @Override
    public boolean validate(Object o, FormFieldValidatorContext formFieldValidatorContext) {
        List<String> genreNames = (List<String>) o;
        return genreNames.size() > 0;
    }
}
