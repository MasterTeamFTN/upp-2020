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
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "isbn", nullable = false)
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

    @Column(name = "publisher", nullable = false)
    private String publisher;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "cityCountry", nullable = false)
    private String cityCountry;

    @Column(name = "synopsis", nullable = false)
    private String synopsis;

    @Column(name = "numOfPages", nullable = false)
    private Integer numOfPages;

    @Column(name = "isPlagiarized", nullable = false)
    private Boolean isPlagiarized;

    @Column(name = "isPublished", nullable = false)
    private Boolean isPublished;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Suggestion> suggestions;

    @ManyToOne
    @JoinColumn(name="complaint_id", nullable=false)
    private Complaint complaint;


}
