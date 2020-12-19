package it.unisa.c07.biblionet.model.entity;


import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idLibro;

    @NonNull
    @Column(length = 30)
    private String titolo;

    @NonNull
    @Column(length = 30)
    private String autore;

    @Column(unique = true, length = 13)
    @NonNull
    private String ISBN;

    @Column(nullable = false)
    @NonNull
    private LocalDateTime annoDiPubblicazione;

    @Column(nullable = false, length = 144)
    @NonNull
    private String descrizione;

    @Column(nullable = false, length = 30)
    @NonNull
    private String casaEditrice;

    @OneToMany
    private List<TicketPrestito> tickets;

    @ManyToMany
    private List<Genere> generi;

    @OneToMany
    private List<Evento> eventi;

   //@OneToMany(mappedBy = "libro",cascade = CascadeType.ALL)
   @OneToMany
    private List<Possesso> possessi;
}
