package rs.ac.uns.ftn.uppservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
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
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    @ManyToOne
    @JoinColumn(name = "chief_editor_id")
    private ChiefEditor chiefEditor;

}
