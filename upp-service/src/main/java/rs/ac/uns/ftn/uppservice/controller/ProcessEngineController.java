package rs.ac.uns.ftn.uppservice.controller;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.exception.NullValueException;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.uppservice.dto.request.CamundaFormSubmitDTO;
import rs.ac.uns.ftn.uppservice.dto.response.FormFieldsDto;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ApiRequestException;
import rs.ac.uns.ftn.uppservice.service.ProcessEngineService;

import java.util.List;

@RestController
@RequestMapping(value = "/process", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProcessEngineController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ProcessEngineService processEngineService;


    /**
     * Start new process for given name
     * @param name of the process
     * @return process id
     */
    @GetMapping(path = "/public/start/{name}")
    public ResponseEntity<String> startProcess(@PathVariable String name) {
        ProcessInstance pi;

        try {
            pi = runtimeService.startProcessInstanceByKey(name);
        } catch (NullValueException e) {
            throw new ApiRequestException("Process with name " + name + " doesn't exist!");
        }

        return new ResponseEntity<>(pi.getId(), HttpStatus.OK);
    }

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

    @PostMapping(path = "/public/submit")
    public ResponseEntity submitForm(@RequestBody CamundaFormSubmitDTO data) {
        String processInstanceId = processEngineService.submitForm(data);
        return ResponseEntity.ok().build();
    }
}
