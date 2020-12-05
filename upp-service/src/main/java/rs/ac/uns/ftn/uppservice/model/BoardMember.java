package rs.ac.uns.ftn.uppservice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "boardMembers")
public class BoardMember extends User {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<BoardMemberDecision> boardMemberDecisions;

}
