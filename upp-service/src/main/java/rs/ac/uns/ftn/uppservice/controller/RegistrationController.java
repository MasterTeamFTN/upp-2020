package rs.ac.uns.ftn.uppservice.controller;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.FormService;
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
import rs.ac.uns.ftn.uppservice.service.*;

import java.util.List;

@RestController
@RequestMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RegistrationController {

    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final FormService formService;
    private final ReaderService readerService;
    private final ConfirmationTokenService confTokenService;
    private final ProcessEngineService processEngineService;
    private final PaymentService paymentService;
    private final WriterService writerService;


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
        String processInstanceId = processEngineService.submitForm(data, false);
        runtimeService.setVariable(processInstanceId, "registrationFormData", data.getFormData());
        runtimeService.setVariable(processInstanceId, "chooseGenresFormData", null);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/public/reader-genres-submit")
    public ResponseEntity submitBetaReadersGenres(@RequestBody CamundaFormSubmitDTO data) {
        String processInstanceId = processEngineService.submitForm(data, true);
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
        String processInstanceId = processEngineService.submitForm(data, false);
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

    @PostMapping(path = "/public/answer-membership-request")
    public ResponseEntity answerRequest(@RequestBody CamundaFormSubmitDTO data) {
        String processInstanceId = processEngineService.submitDecision(data);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/public/submit-payment")
    public ResponseEntity submitPayment(@RequestBody CamundaFormSubmitDTO data) {
        Task task = taskService.createTaskQuery().processInstanceId(data.getTaskId()).list().get(0);    // data.getTaskId contains processInstanceId here (it is 5:32 in the morning.)
        writerService.activateMembership(data.getTaskId());
        paymentService.submitPayment(data, task.getId());

        return ResponseEntity.ok().build();
    }
}
