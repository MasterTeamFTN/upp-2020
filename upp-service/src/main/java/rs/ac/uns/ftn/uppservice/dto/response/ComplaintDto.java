package rs.ac.uns.ftn.uppservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintDto {

    private Long id;

    private String plagiarisedBook;
    private String originalBook;
    private Set<BoardMemberDecisionDto> boardMemberDecisionDtos = new HashSet<>();
    private Set<CompliantAssignmentDto> compliantAssignmentDtos = new HashSet<>();
    private String processInstanceId;
    private String jurisdiction;


}
