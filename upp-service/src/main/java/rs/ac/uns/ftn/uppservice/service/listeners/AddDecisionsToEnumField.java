package rs.ac.uns.ftn.uppservice.service.listeners;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddDecisionsToEnumField implements TaskListener {


    @Override
    public void notify(DelegateTask delegateTask) {
//        TaskFormData formData = delegateTask
//                .getExecution()
//                .getProcessEngineServices()
//                .getFormService()
//                .getTaskFormData(delegateTask.getId());
//
//        FormField decisionField = formData.getFormFields().stream()
//                .filter(formField -> formField.getId().equals("decision"))
//                .findFirst()
//                .get();
//
//        EnumFormType fieldType = (EnumFormType) decisionField.getType();
//
//        List<Decision> decisions = decisionRepository.findAll();
//        decisions.forEach(decision -> fieldType.getValues().put(decision.getId().toString(), decision.getName()));
    }
}
