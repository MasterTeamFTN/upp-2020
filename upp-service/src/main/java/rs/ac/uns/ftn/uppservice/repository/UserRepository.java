package rs.ac.uns.ftn.uppservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.uppservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    Optional<User> findByEmail(String email);

    Optional<User> findOneByUsername(String username);

    @Query("SELECT u FROM User u JOIN u.authorities a WHERE a.name LIKE 'ROLE_CHIEF_EDITOR'")
    Optional<User> findChiefEditor();

    @Query("SELECT u FROM User u JOIN u.authorities a WHERE a.name LIKE 'ROLE_EDITOR'")
    List<User> findAllEditors();

    @Query("SELECT u FROM User u JOIN u.authorities a WHERE a.name LIKE 'ROLE_LECTURER'")
    Optional<User> findLecturer();

    @Query("SELECT u FROM User u JOIN u.authorities a WHERE a.name LIKE 'ROLE_BOARD_MEMBER'")
    List<User> findBoardMembers();
}
