package rs.ac.uns.ftn.uppservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.uppservice.model.BoardMember;

@Repository
public interface BoardMemberRepository extends JpaRepository<BoardMember, Long> {

}
