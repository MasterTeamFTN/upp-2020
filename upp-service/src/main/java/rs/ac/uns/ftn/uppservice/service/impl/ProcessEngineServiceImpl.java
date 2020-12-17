package rs.ac.uns.ftn.uppservice.service.impl;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorException;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.dto.request.CamundaFormSubmitDTO;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ApiRequestException;
import rs.ac.uns.ftn.uppservice.service.ProcessEngineService;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProcessEngineServiceImpl implements ProcessEngineService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    /**
     * Sends the form to the Camunda and validates it.
     * @param data
     * @return process instance id
     */
    @Override
    public String submitForm(CamundaFormSubmitDTO data) {
        Map<String, Object> map = new HashMap<>();

        for(FormSubmissionDto temp : data.getFormData()){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        Task task = taskService.createTaskQuery().taskId(data.getTaskId()).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        try {
            formService.submitTaskForm(data.getTaskId(), map);
        } catch (FormFieldValidatorException e) {
            throw new ApiRequestException("Failed validation");
        }

        return processInstanceId;
    }
}
