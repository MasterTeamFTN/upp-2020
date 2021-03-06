package rs.ac.uns.ftn.uppservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardMemberDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
}
