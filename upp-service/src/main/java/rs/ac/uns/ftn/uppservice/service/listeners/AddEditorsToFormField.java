package rs.ac.uns.ftn.uppservice.service.listeners;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.stereotype.Component;

import rs.ac.uns.ftn.uppservice.camunda.formfields.MultiSelectFormFieldType;
import rs.ac.uns.ftn.uppservice.model.User;
import rs.ac.uns.ftn.uppservice.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class AddEditorsToFormField implements TaskListener {

    private final UserRepository userRepository;

    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData formData = delegateTask
                .getExecution()
                .getProcessEngineServices()
                .getFormService()
                .getTaskFormData(delegateTask.getId());

        FormField field = formData.getFormFields().stream()
                .filter(formField -> formField.getId().equals("FormField_editors"))
                .findFirst()
                .get();

        MultiSelectFormFieldType fieldType = (MultiSelectFormFieldType) field.getType();
        List<User> editors = userRepository.findAllEditors();

        editors.forEach(editor -> fieldType.getValues()
                .put(editor.getId().toString(), editor.getFirstName() + " " + editor.getLastName() + ", " + editor.getUsername())
        );
    }
}
