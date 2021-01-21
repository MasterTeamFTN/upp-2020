package rs.ac.uns.ftn.uppservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WriterDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private boolean enabled;
    private boolean isMember;
    private Set<String> registrationPapers;
}