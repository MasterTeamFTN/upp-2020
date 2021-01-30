package rs.ac.uns.ftn.uppservice.service;

import rs.ac.uns.ftn.uppservice.dto.response.ComplaintDto;

import java.util.List;

public interface ComplaintAssignmentService {

    List<ComplaintDto> getComplaintsByEditorsAssignment(String editorsUsername);
}
