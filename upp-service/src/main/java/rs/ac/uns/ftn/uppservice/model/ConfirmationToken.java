package rs.ac.uns.ftn.uppservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "confirmation_tokens")
@Data
@NoArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "used", nullable = false)
    private boolean used;

    @Column(name = "process_instance_id", nullable = false)
    private String processInstanceId;

    @OneToOne
    private User user;

    public ConfirmationToken(User user, String processInstanceId) {
        this.token = UUID.randomUUID().toString();
        this.used = false;
        this.user = user;
        this.processInstanceId = processInstanceId;
    }
}
