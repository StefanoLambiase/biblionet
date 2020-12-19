package it.unisa.c07.biblionet.model.entity;

import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idEvento;

    @NonNull
    @Column(nullable = false, length = 30)
    private String nomeEvento;

    @NonNull
    @Column(nullable = false, length = 255)
    private String descrizione;

    @NonNull
    private LocalDateTime dataOra;

    @ManyToMany(mappedBy = "eventi")
    private List<Lettore> lettori;

    @NonNull
    @ManyToOne
    private ClubDelLibro club;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Libro libro;

}
