package rs.ac.uns.ftn.uppservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "writers")
public class Writer extends User {

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass=Genre.class)
    private Set<Genre> genres;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Book> books;
}
