package rs.ac.uns.ftn.uppservice.controller;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.uppservice.dto.response.FormFieldsDto;

import java.util.List;

@RestController
@RequestMapping(value = "/process", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProcessEngineController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;


    /**
     * Get the form for the currently activated task for specified process instance id
     * @param processInstanceId
     * @return
     */
    @GetMapping(path = "/public/form/{processInstanceId}")
    public ResponseEntity<FormFieldsDto> getFormForCurrentActiveTask(@PathVariable String processInstanceId) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new ResponseEntity(new FormFieldsDto(task.getId(), properties, processInstanceId), HttpStatus.OK);
    }
}
