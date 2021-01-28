package rs.ac.uns.ftn.uppservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "editors")
public class Editor extends User {

    @OneToMany(mappedBy = "editor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CompliantAssignment> compliantAssignments;
}
