package rs.ac.uns.ftn.uppservice.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.uppservice.dto.request.CamundaFormSubmitDTO;
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

}
