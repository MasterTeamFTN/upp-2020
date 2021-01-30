package rs.ac.uns.ftn.uppservice.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "boardMembers")
public class BoardMember extends User {

    @OneToMany(mappedBy = "boardMember", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<BoardMemberDecision> boardMemberDecisions;

}
