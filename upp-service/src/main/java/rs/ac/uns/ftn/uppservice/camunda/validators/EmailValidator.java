package rs.ac.uns.ftn.uppservice.camunda.validators;

import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class EmailValidator implements FormFieldValidator {

    @Override
    public boolean validate(Object o, FormFieldValidatorContext formFieldValidatorContext) {
        String email = (String) o;

        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        } catch (AddressException ex) {
            return false;
        }

        return true;
    }
}
