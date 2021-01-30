package rs.ac.uns.ftn.uppservice.common.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.dto.response.CompliantAssignmentDto;
import rs.ac.uns.ftn.uppservice.model.CompliantAssignment;

@Component
@RequiredArgsConstructor
public class ComplaintAssignmentMapper implements CustomMapper<CompliantAssignment, CompliantAssignmentDto> {

    private final EditorMapper editorMapper;

    @Override
    public CompliantAssignmentDto entityToDto(CompliantAssignment compliantAssignment) {
        CompliantAssignmentDto complaintAssignmentDto = new CompliantAssignmentDto();

        complaintAssignmentDto.setId(compliantAssignment.getId());
        complaintAssignmentDto.setEditor(editorMapper.entityToDto(compliantAssignment.getEditor()));
        complaintAssignmentDto.setNotes(compliantAssignment.getNotes());

        return complaintAssignmentDto;
    }
}
