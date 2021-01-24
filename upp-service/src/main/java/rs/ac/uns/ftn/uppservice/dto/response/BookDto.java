package rs.ac.uns.ftn.uppservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private String title;
    private String authorsName;
    private String genre;
    private Boolean isPublished;
    private String processInstanceId;
    private Boolean isReviewSubmitted = false;

    /*This field is null until second book form is submitted*/
    private String cityCountry;
}
