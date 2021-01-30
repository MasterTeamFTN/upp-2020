package rs.ac.uns.ftn.uppservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.uppservice.model.User;
import rs.ac.uns.ftn.uppservice.service.ComplaintAssignmentService;

@RestController
@RequestMapping(value = "/editorComplaints")
@RequiredArgsConstructor
public class EditorComplaintController {

    private final ComplaintAssignmentService complaintAssignmentService;

    @GetMapping("/byEditor")
    public ResponseEntity getComplaintsByEditor() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new ResponseEntity<>(complaintAssignmentService.getComplaintsByEditorsAssignment(user.getUsername()), HttpStatus.OK);
    }
}
