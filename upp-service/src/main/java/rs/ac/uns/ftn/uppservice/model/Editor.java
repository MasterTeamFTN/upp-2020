package rs.ac.uns.ftn.uppservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "editors")
public class Editor extends User {

    @OneToMany(mappedBy = "editor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CompliantAssignment> compliantAssignments;
}
