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
@Table(name = "suggestions")
public class Suggestion {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(targetClass=String.class)
    private Set<String> foundErrors;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Lecturer lecturer;

    @ManyToOne
    private ChiefEditor chiefEditor;

}
