package rs.ac.uns.ftn.uppservice.common.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.dto.response.ComplaintDto;
import rs.ac.uns.ftn.uppservice.model.Complaint;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ComplaintMapper implements CustomMapper<Complaint, ComplaintDto> {

    private final ComplaintAssignmentMapper complaintAssignmentMapper;
    private final BoardMemberDecisionMapper boardMemberDecisionMapper;

    @Override
    public ComplaintDto entityToDto(Complaint complaint) {
        ComplaintDto complaintDto = new ComplaintDto();

        complaintDto.setId(complaint.getId());
        complaintDto.setOriginalBook(complaint.getOriginalBook().getTitle());
        complaintDto.setPlagiarisedBook(complaint.getPlagiarisedBook().getTitle());
        complaintDto.setCompliantAssignmentDtos(complaint.getCompliantAssignments()
                .stream()
                .map(complaintAssignmentMapper::entityToDto).
                        collect(Collectors.toSet()));
        complaintDto.setBoardMemberDecisionDtos(complaint.getBoardMemberDecisions()
                .stream()
                .map(boardMemberDecisionMapper::entityToDto)
                .collect(Collectors.toSet()));
        complaintDto.setProcessInstanceId(complaint.getProcessInstanceId());
        complaintDto.setJurisdiction(complaint.getJurisdiction().getLabel());

        return complaintDto;
    }
}
