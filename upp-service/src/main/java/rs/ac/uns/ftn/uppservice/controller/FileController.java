package rs.ac.uns.ftn.uppservice.controller;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.uppservice.dto.response.UserFileDto;
import rs.ac.uns.ftn.uppservice.service.FileService;
import rs.ac.uns.ftn.uppservice.service.ProcessEngineService;

import java.io.IOException;

import static rs.ac.uns.ftn.uppservice.common.constants.Constants.REQUESTED_MEMBER;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/file")
public class FileController {

    private final ProcessEngineService processEngineService;

    @PostMapping("/upload")
    public ResponseEntity<String> handleBookFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("taskId") String taskId
    ) throws IOException {
        processEngineService.submitFile(taskId, file);
        return new ResponseEntity<>("File successfully uploaded.", HttpStatus.OK);
    }

}
