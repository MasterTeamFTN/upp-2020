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
@Table(name = "lecturers")
public class Lecturer extends User {

    @OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Suggestion> suggestionSet;



}
