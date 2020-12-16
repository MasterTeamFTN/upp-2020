package rs.ac.uns.ftn.uppservice.camunda.formfields;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.HashMap;
import java.util.List;

public class MultiSelectFormFieldType extends EnumFormType {

    public static final String TYPE_NAME = "MultiSelect";

    public MultiSelectFormFieldType() {
        super(new HashMap<>());
    }

    @Override
    public String getName() {
        return TYPE_NAME;
    }

    @Override
    public TypedValue convertValue(TypedValue propertyValue) {
        Object value = propertyValue.getValue();

        return value == null
                ? Variables.stringValue(null, propertyValue.isTransient())
                : Variables.stringValue(value.toString(), propertyValue.isTransient());
    }

    @Override
    public Object convertFormValueToModelValue(Object propertyValue) {
        return propertyValue;
    }

    @Override
    public String convertModelValueToFormValue(Object modelValue) {
        if (modelValue != null) {
            if (!(modelValue instanceof List)) {
                throw new ProcessEngineException("Model value should be a List");
            }
        }

        return modelValue.toString();
    }
}
