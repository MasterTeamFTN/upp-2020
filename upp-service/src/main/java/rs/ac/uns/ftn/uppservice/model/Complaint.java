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
@Table(name = "complaints")
public class Complaint {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Book questionedBook;

    @OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Book> originalBooks;

    @OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<BoardMemberDecision> boardMemberDecisions;

    @OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CompliantAssignment> compliantAssignments;

    @ManyToOne
    @JoinColumn(name = "chiefEditor_id", nullable = false)
    private ChiefEditor chiefEditor;
}
