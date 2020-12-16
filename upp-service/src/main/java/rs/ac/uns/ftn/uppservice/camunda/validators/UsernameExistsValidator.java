package rs.ac.uns.ftn.uppservice.camunda.validators;

import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;
import rs.ac.uns.ftn.uppservice.config.SpringContext;
import rs.ac.uns.ftn.uppservice.model.User;
import rs.ac.uns.ftn.uppservice.repository.UserRepository;

public class UsernameExistsValidator implements FormFieldValidator {

    private UserRepository userRepository;

    public UsernameExistsValidator() {
        this.userRepository = SpringContext.getBean(UserRepository.class);
    }

    // Returns true if form field is valid
    @Override
    public boolean validate(Object o, FormFieldValidatorContext formFieldValidatorContext) {
        String username = (String) o;
        User user = userRepository.findByUsername(username);
        return user == null;
    }
}
