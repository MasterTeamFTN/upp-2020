package rs.ac.uns.ftn.uppservice.model;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "writers")
public class Writer extends User {

    @ManyToMany
    @JoinTable(
            name = "writer_genre",
            joinColumns = @JoinColumn(name = "writer_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Book> books;

    @Column(name = "isMember", columnDefinition = "boolean default false")
    private boolean isMember;

    @Column(length = 32, columnDefinition = "varchar(32) default 'NOT_SUBMITTED_YET'")
    @Enumerated(value = EnumType.STRING)
    private MembershipDecision membershipDecision = MembershipDecision.NEED_MORE_INFO;


}
