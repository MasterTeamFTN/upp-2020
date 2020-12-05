package rs.ac.uns.ftn.uppservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "boardMemberDecisions")
public class BoardMemberDecision {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private BoardMember boardMember;

    @ManyToOne
    private Complaint complaint;

    @Column(name = "isPlagiarized")
    private Boolean isPlagiarized;
}
