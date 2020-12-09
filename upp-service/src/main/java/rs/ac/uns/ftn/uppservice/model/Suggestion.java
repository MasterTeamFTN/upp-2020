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
    @JoinColumn(name = "book_id", nullable=false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "lecturer_id", nullable=false)
    private Lecturer lecturer;

    @ManyToOne
    @JoinColumn(name = "chief_editor_id", nullable=false)
    private ChiefEditor chiefEditor;

}
