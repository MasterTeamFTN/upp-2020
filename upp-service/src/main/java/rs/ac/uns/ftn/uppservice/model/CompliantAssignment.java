package rs.ac.uns.ftn.uppservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "complaintAssignments")
public class CompliantAssignment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "notes")
    private String notes;

    @ManyToOne
    @JoinColumn(name="editor_id", nullable=false)
    private Editor editor;

    @ManyToOne
    @JoinColumn(name="complaint_id", nullable=false)
    private Complaint complaint;
}
