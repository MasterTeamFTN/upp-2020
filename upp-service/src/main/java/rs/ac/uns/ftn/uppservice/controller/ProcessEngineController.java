package rs.ac.uns.ftn.uppservice.controller;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.exception.NullValueException;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.uppservice.dto.request.CamundaFormSubmitDTO;
import rs.ac.uns.ftn.uppservice.dto.response.FormFieldsDto;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ApiRequestException;
import rs.ac.uns.ftn.uppservice.service.ProcessEngineService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.uppservice.model.User;

import java.util.List;

@RestController
@RequestMapping(value = "/process", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProcessEngineController {

    private final TaskService taskService;
    private final FormService formService;
    private final RuntimeService runtimeService;
    private final ProcessEngineService processEngineService;
    private final IdentityService identityService;


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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null ) {
            User user = (User) auth.getPrincipal();
            if(user!= null) {
                task.setAssignee(user.getUsername());
                taskService.saveTask(task);
            }
        }

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new ResponseEntity(new FormFieldsDto(task.getId(), properties, processInstanceId), HttpStatus.OK);
    }

    /**
     * Endpoint for submitting all forms.
     * Only authenticated users can use.
     * @param data - form data
     * @return 200 - OK
     */
    @PostMapping(path = "/submit")
    public ResponseEntity submitForm(@RequestBody CamundaFormSubmitDTO data) {
        String processInstanceId = processEngineService.submitForm(data);
        return ResponseEntity.ok().build();
    }
}
