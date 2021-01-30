package rs.ac.uns.ftn.uppservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.uppservice.model.BoardMemberDecision;

import java.util.Optional;

@Repository
public interface BoardMemberDecisionRepository extends JpaRepository<BoardMemberDecision, Long> {
    Optional<BoardMemberDecision> findByIdAndBoardMemberUsername(Long complaintId, String boardMembersUsername);
}
