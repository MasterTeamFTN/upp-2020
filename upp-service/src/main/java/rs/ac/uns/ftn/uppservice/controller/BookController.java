package rs.ac.uns.ftn.uppservice.controller;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.exception.NullValueException;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.dto.request.CamundaFormSubmitDTO;
import rs.ac.uns.ftn.uppservice.dto.response.BookDto;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ApiRequestException;
import rs.ac.uns.ftn.uppservice.model.User;
import rs.ac.uns.ftn.uppservice.service.BookService;
import rs.ac.uns.ftn.uppservice.service.ProcessEngineService;

import java.util.List;

@RestController
@RequestMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BookController {

    private final ProcessEngineService processEngineService;
    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final IdentityService identityService;

    private final BookService bookService;


    @GetMapping(path = "/publish-start-process")
    public ResponseEntity<String> startProcess() {
        ProcessInstance pi;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            identityService.setAuthenticatedUserId(user.getUsername());
            pi = runtimeService.startProcessInstanceByKey(Constants.PROCESS_PUBLISH_BOOK);
        } catch (NullValueException e) {
            throw new ApiRequestException("Process with name doesn't exist!");
        }

//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        runtimeService.setVariable(pi.getId(), "writerAssignee", user.getUsername());

        return new ResponseEntity<>(pi.getId(), HttpStatus.OK);
    }

    @GetMapping(path = "/plagiarism-start-process")
    @PreAuthorize("hasRole('ROLE_WRITER')")
    public ResponseEntity<String> startPlagiarismProcess() {
        ProcessInstance pi;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            identityService.setAuthenticatedUserId(user.getUsername());
            pi = runtimeService.startProcessInstanceByKey(Constants.PROCESS_PLAGIARISM);
        } catch (NullValueException e) {
            throw new ApiRequestException("Process with name doesn't exist!");
        }

        return new ResponseEntity<>(pi.getId(), HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<BookDto>> allBooks() {
        return new ResponseEntity<>(bookService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/myBooks")
    public ResponseEntity<List<BookDto>> myBooks() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new ResponseEntity<>(bookService.getMyBooks(user.getUsername()), HttpStatus.OK);
    }

}
