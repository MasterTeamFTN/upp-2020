package rs.ac.uns.ftn.uppservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.uppservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
