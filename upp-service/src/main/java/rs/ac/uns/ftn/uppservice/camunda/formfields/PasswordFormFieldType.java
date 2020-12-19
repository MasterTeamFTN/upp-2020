package rs.ac.uns.ftn.uppservice.camunda.formfields;

import lombok.NoArgsConstructor;
import org.camunda.bpm.engine.impl.form.type.StringFormType;

@NoArgsConstructor
public class PasswordFormFieldType extends StringFormType {

    public static final String TYPE_NAME = "password";

    @Override
    public String getName() {
        return TYPE_NAME;
    }

}
