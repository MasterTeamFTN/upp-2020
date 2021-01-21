package rs.ac.uns.ftn.uppservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "beta_reader_comment")
public class BetaReaderComment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment", nullable = false)
    private String comment;

    @OneToOne
    private Reader reader;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable=false)
    private Book book;
}
