package it.unisa.c07.biblionet.model.entity;

import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class TicketPrestito {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idTicket;

    public enum stati{CREATO,IN_ATTESA_DI_CONFERMA,CONFERMATO,IN_ATTESA_DI_RESTITUZIONE,CHIUSO};

    @NonNull
    @Column(nullable = false)
    private stati stato;

    @NonNull
    @Column(nullable = false)
    private LocalDateTime dataRichiesta;

    @NonNull
    @Column(nullable = false)
    private LocalDateTime dataRestituzione;

    @NonNull
    //@Column(nullable = false)
    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Libro libro;

    @NonNull
    //@Column(nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private Biblioteca biblioteca;

    @NonNull
    //@Column(nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private Lettore lettore;

}
