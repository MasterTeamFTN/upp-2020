package rs.ac.uns.ftn.uppservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private Long id;
    private String title;
    private String authorsName;
    private String genre;
    private Boolean isPublished;
    private String processInstanceId;
    private String handwritePath;
    private Set<BetaReaderCommentDto> betaReadersComments;

    private String jurisdiction;

    /*This field is null until second book form is submitted*/
    private String cityCountry;
}
