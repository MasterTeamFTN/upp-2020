package rs.ac.uns.ftn.uppservice.controller;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.uppservice.dto.request.ReaderRegistrationSubmitDTO;
import rs.ac.uns.ftn.uppservice.dto.response.FormFieldsDto;
import rs.ac.uns.ftn.uppservice.dto.response.FormSubmissionDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistrationController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;


    @GetMapping(path = "/public/reader-start")
    public ResponseEntity<FormFieldsDto> startReaderRegistration() {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("Process_ReaderRegistration");
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new ResponseEntity(new FormFieldsDto(task.getId(), properties, pi.getId()), HttpStatus.OK);
    }

    @PostMapping(path = "/public/reader-submit")
    public ResponseEntity submitReaderRegistrationData(@RequestBody ReaderRegistrationSubmitDTO data) {
        Map<String, Object> map = new HashMap<>();

        for(FormSubmissionDto temp : data.getFormData()){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        Task task = taskService.createTaskQuery().taskId(data.getTaskId()).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "registrationFormData", data.getFormData());
        formService.submitTaskForm(data.getTaskId(), map);

        return ResponseEntity.ok().build();
    }
}
