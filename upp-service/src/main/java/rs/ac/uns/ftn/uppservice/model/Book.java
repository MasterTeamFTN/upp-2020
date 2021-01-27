package rs.ac.uns.ftn.uppservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "isbn")
    private String isbn;

    @ManyToOne
    @JoinColumn(name="writer_id", nullable=false)
    private Writer writer;

    @ElementCollection(targetClass=String.class)
    private Set<String> coWriters;

    @ManyToOne
    @JoinColumn(name="genre_id", nullable=false)
    private Genre genre;

    @ElementCollection(targetClass=String.class)
    private Set<String> keywords;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "year")
    private int year;

    @Column(name = "cityCountry")
    private String cityCountry;

    @Column(name = "synopsis", nullable = false)
    private String synopsis;

    @Column(name = "numOfPages")
    private Integer numOfPages;

    @Column(name = "isPlagiarized", nullable = false)
    private Boolean isPlagiarized = false;

    @Column(name = "isPublished")
    private Boolean isPublished;

    @Column(name = "isRejected", nullable = false)
    private Boolean isRejected = false;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Suggestion> suggestions;

    @ManyToOne
    @JoinColumn(name="complaint_id")
    private Complaint complaint;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<BetaReaderComment> betaReadersComments;
}
