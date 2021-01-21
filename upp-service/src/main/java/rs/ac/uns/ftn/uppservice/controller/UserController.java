package rs.ac.uns.ftn.uppservice.controller;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.uppservice.dto.request.CamundaFormSubmitDTO;
import rs.ac.uns.ftn.uppservice.dto.request.PaperDto;
import rs.ac.uns.ftn.uppservice.dto.response.UserDTO;
import rs.ac.uns.ftn.uppservice.dto.response.WriterDto;
import rs.ac.uns.ftn.uppservice.service.ProcessEngineService;
import rs.ac.uns.ftn.uppservice.service.UserService;
import rs.ac.uns.ftn.uppservice.util.Mapper;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userService;
    private final RuntimeService runtimeService;
    private final ProcessEngineService processEngineService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> user(@PathVariable Long id) {
        return new ResponseEntity<>(Mapper.map(userService.findById(id), UserDTO.class), HttpStatus.OK);
    }

    @GetMapping("/{id}/asWriter")
    public ResponseEntity<WriterDto> writer(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getWriter(id), HttpStatus.OK);
    }
}
