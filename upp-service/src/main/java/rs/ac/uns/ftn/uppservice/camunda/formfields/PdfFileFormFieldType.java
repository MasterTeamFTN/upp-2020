package rs.ac.uns.ftn.uppservice.camunda.formfields;

import lombok.SneakyThrows;
import org.apache.catalina.core.ApplicationPart;
import org.apache.commons.io.FileUtils;
import org.camunda.bpm.engine.impl.form.type.AbstractFormFieldType;
import org.camunda.bpm.engine.impl.form.type.SimpleFormFieldType;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.FileValue;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PdfFileFormFieldType extends MultiSelectFormFieldType {
    public static final String FORM_TYPE = "PdfFile";

    @Override
    public String getName() {
        return FORM_TYPE;
    }

}




//public class PdfFileFormFieldType extends SimpleFormFieldType {
//
//    public final static String FORM_TYPE = "PdfFile";
//    private final Path PAPERS_LOCATION = Paths.get("src/main/resources/temp");
//
//    @Override
//    public String getName() {
//        return FORM_TYPE;
//    }
//
//    @Override
//    protected TypedValue convertValue(TypedValue propertyValue) {
//        if (propertyValue instanceof FileValue) {
//            return propertyValue;
//        } else {
//            Object value = propertyValue.getValue();
//            if (value == null) {
//                return Variables.fileValue("uploadedPdf").create();
//            } else {
//                MultipartFile multipartFile = (MultipartFile) value;
//                File convertedFile = new File("");
//                try {
//                    Files.copy(multipartFile.getInputStream(), this.PAPERS_LOCATION.resolve(multipartFile.getOriginalFilename()));
//                    convertedFile = new File(this.PAPERS_LOCATION.toString(), multipartFile.getOriginalFilename());
//                    FileUtils.writeByteArrayToFile(convertedFile, multipartFile.getBytes());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                return Variables.fileValue(convertedFile);
//            }
//        }
//    }
//
//
//    @Override
//    public Object convertFormValueToModelValue(Object o) {
//        return null;
//    }
//
//    @Override
//    public String convertModelValueToFormValue(Object o) {
//        return null;
//    }
//}




//public class PdfFileFormFieldType extends AbstractFormFieldType {
//
//    public final static String FORM_TYPE = "PdfFile";
//
//    @Override
//    public String getName() {
//        return FORM_TYPE;
//    }
//
//    @Override
//    public TypedValue convertToFormValue(TypedValue propertyValue) {
//        if(propertyValue instanceof FileValue) {
//            return propertyValue;
//        } else {
//            Object value = propertyValue.getValue();
//            if(value == null) {
//                return Variables.fileValue("null").create();
//            } else {
//                return Variables.fileValue((File) value);
//            }
//        }
//    }
//
//    @Override
//    public TypedValue convertToModelValue(TypedValue typedValue) {
//        return null;
//    }
//
//    @Override
//    public Object convertFormValueToModelValue(Object o) {
//        return null;
//    }
//
//    @Override
//    public String convertModelValueToFormValue(Object o) {
//        return null;
//    }
//}