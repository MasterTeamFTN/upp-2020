package rs.ac.uns.ftn.uppservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.uppservice.model.ConfirmationToken;
import rs.ac.uns.ftn.uppservice.model.User;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    ConfirmationToken findByToken(String token);
    ConfirmationToken findByUser(User user);
}
