package rs.ac.uns.ftn.uppservice.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.exception.NullValueException;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.uppservice.dto.request.CamundaFormSubmitDTO;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ApiRequestException;
import rs.ac.uns.ftn.uppservice.model.User;
import rs.ac.uns.ftn.uppservice.service.ProcessEngineService;

@RestController
@RequestMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    @Autowired
    private ProcessEngineService processEngineService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @GetMapping(path = "/publish-start-process")
    @PreAuthorize("hasRole('ROLE_WRITER')")
    public ResponseEntity<String> startProcess() {
        ProcessInstance pi;

        try {
            pi = runtimeService.startProcessInstanceByKey("Process_BookPublishing");
        } catch (NullValueException e) {
            throw new ApiRequestException("Process with name doesn't exist!");
        }

//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        runtimeService.setVariable(pi.getId(), "writerAssignee", user.getUsername());

        return new ResponseEntity<>(pi.getId(), HttpStatus.OK);
    }

    @PostMapping(path = "/init-book-submit")
    @PreAuthorize("hasRole('ROLE_WRITER')")
    public ResponseEntity submitReaderRegistrationData(@RequestBody CamundaFormSubmitDTO data) {
        String processInstanceId = processEngineService.submitForm(data);

        // U processEngine.submitForm sam stavio setovanje varijable jer je bilo problema
        // da se varijabla zatrazi pre nego sto stigne da se upise
        // tj. prvo se izvrsi submit i on odma ide na sledeci task kome treba forma
        // a nije stigao do ove linije gde se setuje ta forma
        // runtimeService.setVariable(processInstanceId, "initBookFormData", data.getFormData());

        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/first-review")
    @PreAuthorize("hasRole('ROLE_CHIEF_EDITOR')")
    public ResponseEntity submitFirstReview(@RequestBody CamundaFormSubmitDTO data) {
//        Task task = taskService.createTaskQuery().taskId(data.getTaskId()).singleResult();
//        ako zatreba ovde ces claim-ovati task
//        dobavis korisnika iz runtimeservice i assignujes ga preko taskService
        String processInstanceId = processEngineService.submitForm(data);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/submit-full-book")
    @PreAuthorize("hasRole('ROLE_WRITER')")
    public ResponseEntity submitFullBook(@RequestBody CamundaFormSubmitDTO data) {
        String processInstanceId = processEngineService.submitForm(data);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/submit-plagiarism-form")
    @PreAuthorize("hasRole('ROLE_CHIEF_EDITOR')")
    public ResponseEntity submitPlagiarismForm(@RequestBody CamundaFormSubmitDTO data) {
        String processInstanceId = processEngineService.submitForm(data);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/submit-send-to-beta")
    @PreAuthorize("hasRole('ROLE_CHIEF_EDITOR')")
    public ResponseEntity submitSendToBetaReadersForm(@RequestBody CamundaFormSubmitDTO data) {
        String processInstanceId = processEngineService.submitForm(data);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/submit-comment")
    @PreAuthorize("hasRole('ROLE_READER')")
    public ResponseEntity submitComment(@RequestBody CamundaFormSubmitDTO data) {
        String processInstanceId = processEngineService.submitForm(data);
        return ResponseEntity.ok().build();
    }
}
