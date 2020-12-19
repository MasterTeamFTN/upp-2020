package rs.ac.uns.ftn.uppservice.config;

import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.spring.boot.starter.configuration.impl.AbstractCamundaConfiguration;
import org.springframework.context.annotation.Configuration;
import rs.ac.uns.ftn.uppservice.camunda.formfields.MultiSelectFormFieldType;
import rs.ac.uns.ftn.uppservice.camunda.formfields.PasswordFormFieldType;

@Configuration
public class CamundaConfiguration extends AbstractCamundaConfiguration {

    @Override
    public void preInit(SpringProcessEngineConfiguration config) {
        config.getCustomFormTypes().add(new MultiSelectFormFieldType());
        config.getCustomFormTypes().add(new PasswordFormFieldType());
    }
}
