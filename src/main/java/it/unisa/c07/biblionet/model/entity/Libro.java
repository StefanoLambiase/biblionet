package it.unisa.c07.biblionet.model.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idLibro;

    //Cambiare nell'SDD
    @NonNull
    @Column(length = 90)
    private String titolo;

    @NonNull
    @Column(length = 60)
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

    @OneToMany(mappedBy = "libro")
    private List<TicketPrestito> tickets;

    @ManyToMany
    private List<Genere> generi;

    @OneToMany(mappedBy = "libro")
    private List<Evento> eventi;

    @OneToMany(mappedBy = "possessoID.libroID")
    private List<Possesso> possessi;
}
