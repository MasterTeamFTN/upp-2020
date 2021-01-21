package rs.ac.uns.ftn.uppservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.uppservice.model.User;

import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFileDto {
    private User user;
    private File file;
}
