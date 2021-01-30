package rs.ac.uns.ftn.uppservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BoardMemberDecisionDto {

    private Long id;
    private BoardMemberDto boardMember;
    private Boolean isPlagiarized;
}
