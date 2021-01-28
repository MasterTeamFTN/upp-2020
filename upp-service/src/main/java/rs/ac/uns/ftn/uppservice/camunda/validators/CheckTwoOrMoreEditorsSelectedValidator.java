package rs.ac.uns.ftn.uppservice.camunda.validators;

import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;

import java.util.List;

public class CheckTwoOrMoreEditorsSelectedValidator implements FormFieldValidator {

    @Override
    public boolean validate(Object o, FormFieldValidatorContext formFieldValidatorContext) {
        List<String> editors = (List<String>) o;
        return editors.size() > 1;
    }
}
