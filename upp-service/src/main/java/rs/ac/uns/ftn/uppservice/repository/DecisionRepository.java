package rs.ac.uns.ftn.uppservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.uppservice.model.Decision;
import rs.ac.uns.ftn.uppservice.model.MembershipDecision;

@Repository
public interface DecisionRepository extends JpaRepository<Decision, Long> {
}
