package rs.ac.uns.ftn.uppservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.uppservice.model.BoardMember;

public interface BoardMemberRepository extends JpaRepository<BoardMember, Long> {

}
