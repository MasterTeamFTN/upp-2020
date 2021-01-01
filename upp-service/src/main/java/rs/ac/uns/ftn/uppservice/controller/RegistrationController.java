package rs.ac.uns.ftn.uppservice.controller;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.exception.NullValueException;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorException;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.uppservice.dto.request.CamundaFormSubmitDTO;
import rs.ac.uns.ftn.uppservice.dto.response.FormFieldsDto;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ApiRequestException;
import rs.ac.uns.ftn.uppservice.service.ConfirmationTokenService;
import rs.ac.uns.ftn.uppservice.service.ProcessEngineService;
import rs.ac.uns.ftn.uppservice.service.ReaderService;

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

    @Autowired
    private ReaderService readerService;

    @Autowired
    private ConfirmationTokenService confTokenService;

    @Autowired
    private ProcessEngineService processEngineService;


    // NOTE: Don't use this endpoint. Instead use /process/public/start/{name}
    // TODO: remove later
    /**
     * Call this endpoint when you want to start reader registration.
     * @return Returns form fields that frontend app needs to generate forms
     */
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

    /**
     * When user fills out the from on the frontend and wants to submit it, then you call this endpoint.
     * @param data - Data from the from
     * @return 200 - OK
     */
    @PostMapping(path = "/public/reader-submit")
    public ResponseEntity submitReaderRegistrationData(@RequestBody CamundaFormSubmitDTO data) {
        String processInstanceId = processEngineService.submitForm(data);
        runtimeService.setVariable(processInstanceId, "registrationFormData", data.getFormData());
        runtimeService.setVariable(processInstanceId, "chooseGenresFormData", null);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/public/reader-genres-submit")
    public ResponseEntity submitBetaReadersGenres(@RequestBody CamundaFormSubmitDTO data) {
        String processInstanceId = processEngineService.submitForm(data);
        runtimeService.setVariable(processInstanceId, "chooseGenresFormData", data.getFormData());
        return ResponseEntity.ok().build();
    }

    /**
     * When user fills out the from on the frontend and wants to submit it, then you call this endpoint.
     * @param data - Data from the from
     * @return 200 - OK
     */
    @PostMapping(path = "/public/writer-submit")
    public ResponseEntity submitWriterRegistrationData(@RequestBody CamundaFormSubmitDTO data) {
        String processInstanceId = processEngineService.submitForm(data);
        runtimeService.setVariable(processInstanceId, "registrationFormData", data.getFormData());
        return ResponseEntity.ok().build();
    }

    /**
     * This endpoint is used for activating user's account after registration.
     * Send token value as URL path variable.
     * @param token
     * @return 200 - OK
     */
    @GetMapping(path = "/public/verify-account/{token}")
    public ResponseEntity verifyAccount(@PathVariable String token) {
        String processInstanceId = confTokenService.getProcessInstanceId(token);

        Task currentTask = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .active()
                .singleResult();

        try {
            runtimeService.setVariable(processInstanceId, "token", token);
        } catch (NullValueException e) {
            throw new ApiRequestException("Confirmation token timed out.");
        }

        taskService.complete(currentTask.getId());

        return ResponseEntity.ok().build();
    }
}
