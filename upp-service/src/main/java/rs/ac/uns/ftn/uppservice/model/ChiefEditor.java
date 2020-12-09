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
@Table(name = "chiefEditors")
public class ChiefEditor extends User {

    @OneToMany(mappedBy = "chiefEditor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Suggestion> suggestions;

    @OneToMany(mappedBy = "chiefEditor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Complaint> complaints;

}
