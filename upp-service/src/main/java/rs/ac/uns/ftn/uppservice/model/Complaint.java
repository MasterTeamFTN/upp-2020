package rs.ac.uns.ftn.uppservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "complaints")
public class Complaint {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Book plagiarisedBook;

    @OneToOne
    private Book originalBook;

    @OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<BoardMemberDecision> boardMemberDecisions = new HashSet<>();

    @OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CompliantAssignment> compliantAssignments = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "chiefEditor_id", nullable = false)
    private ChiefEditor chiefEditor;

    @Column(name = "processInstanceId")
    private String processInstanceId;

    @Column(length = 32, columnDefinition = "varchar(32) default 'EDITORS'")
    @Enumerated(value = EnumType.STRING)
    private Jurisdiction jurisdiction;
}
