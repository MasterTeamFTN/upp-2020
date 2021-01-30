package rs.ac.uns.ftn.uppservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.uppservice.model.CompliantAssignment;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintAssignmentRepository extends JpaRepository<CompliantAssignment, Long> {
    Optional<CompliantAssignment> findByIdAndEditorUsername(Long complaintId, String editorUsername);

    List<CompliantAssignment> findAllByEditorUsername(String editorsUsername);
}
