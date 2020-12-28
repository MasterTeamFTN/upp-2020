package rs.ac.uns.ftn.uppservice.camunda.formfields;

import org.camunda.bpm.engine.impl.form.type.SimpleFormFieldType;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.FileValue;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.io.File;

public class PdfFileFormFieldType extends SimpleFormFieldType {

    public final static String FORM_TYPE = "PdfFile";

    @Override
    public String getName() {
        return FORM_TYPE;
    }

    @Override
    protected TypedValue convertValue(TypedValue propertyValue) {
        if(propertyValue instanceof FileValue) {
            return propertyValue;
        } else {
            Object value = propertyValue.getValue();
            if(value == null) {
                return Variables.fileValue("null").create();
            } else {
                return Variables.fileValue((File) value);
            }
        }
    }


    @Override
    public Object convertFormValueToModelValue(Object o) {
        return null;
    }

    @Override
    public String convertModelValueToFormValue(Object o) {
        return null;
    }
}
