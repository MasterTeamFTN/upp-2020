package rs.ac.uns.ftn.uppservice.service.listeners;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.model.Decision;
import rs.ac.uns.ftn.uppservice.repository.DecisionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddDecisionsToEnumField implements TaskListener {

    private final DecisionRepository decisionRepository;

    @Override
    public void notify(DelegateTask delegateTask) {

        String taskId = delegateTask.getId();
        FormService formService = delegateTask.getProcessEngineServices().getFormService();
        TaskFormData formData = formService.getTaskFormData(taskId);

//        TaskFormData formData = delegateTask
//                .getExecution()
//                .getProcessEngineServices()
//                .getFormService()
//                .getTaskFormData(delegateTask.getId());


        FormField decisionField = formData.getFormFields().stream()
                .filter(formField -> formField.getId().equals("decision"))
                .findFirst()
                .get();


        EnumFormType fieldType = (EnumFormType) decisionField.getType();

        List<Decision> decisions = decisionRepository.findAll();
        decisions.forEach(decision -> fieldType.getValues().put(decision.getId().toString(), decision.getName()));
    }
}
