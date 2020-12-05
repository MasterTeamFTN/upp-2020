package rs.ac.uns.ftn.uppservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reader")
public class Reader extends User {

    @Column(name = "isBetaReader", nullable = false)
    private Boolean isBetaReader;

    @Column(name = "penaltyPoints", nullable = false)
    private int penaltyPoints;

    @Enumerated
    @ElementCollection(targetClass=Genre.class)
    private Set<Genre> genres;
}
