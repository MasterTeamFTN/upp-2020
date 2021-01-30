package rs.ac.uns.ftn.uppservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.common.mapper.ComplaintMapper;
import rs.ac.uns.ftn.uppservice.dto.response.ComplaintDto;
import rs.ac.uns.ftn.uppservice.model.Complaint;
import rs.ac.uns.ftn.uppservice.repository.ComplaintAssignmentRepository;
import rs.ac.uns.ftn.uppservice.repository.ComplaintRepository;
import rs.ac.uns.ftn.uppservice.service.ComplaintAssignmentService;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComplaintAssignmentServiceImpl implements ComplaintAssignmentService {

    private final ComplaintRepository complaintRepository;
    private final ComplaintAssignmentRepository complaintAssignmentRepository;
    private final ComplaintMapper complaintMapper;

    @Override
    public List<ComplaintDto> getComplaintsByEditorsAssignment(String editorsUsername) {

        return complaintAssignmentRepository.findAllByEditorUsername(editorsUsername)
                .stream().map(compliantAssignment -> {
                    Complaint complaint = complaintRepository.findById(compliantAssignment.getComplaint().getId()).orElseThrow(EntityExistsException::new);
                    return complaintMapper.entityToDto(complaint);
                }).collect(Collectors.toList());

    }
}
