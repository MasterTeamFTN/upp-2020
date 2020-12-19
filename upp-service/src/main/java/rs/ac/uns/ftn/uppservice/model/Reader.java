package rs.ac.uns.ftn.uppservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "readers")
public class Reader extends User implements Serializable {

    @Column(name = "isBetaReader", nullable = false)
    private Boolean isBetaReader;

    @Column(name = "penaltyPoints", nullable = false)
    private int penaltyPoints;

    @ManyToMany
    @JoinTable(
            name = "reader_genre",
            joinColumns = @JoinColumn(name = "reader_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;

    @ManyToMany
    @JoinTable(
            name = "reader_beta_genre",
            joinColumns = @JoinColumn(name = "reader_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> betaGenres;

}
