package it.unisa.c07.biblionet.model.entity;

import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;

/**
 * Questa classe rappresenta un Ticket Prestito.
 * Un ticket di prestito posside un ID che lo identifica,
 * uno stato che rapprenseta lo stato del prestito
 * e uan data di richiesta e restituzione del libro.
 * Un ticket è correlato al libro di cui si richiede il prestito,
 * alla biblioteca da cui si prende in prestito,
 * e il lettore che lo prende.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class TicketPrestito {

    /**
     * Rappresenta l'ID autogenerato di un ticket.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idTicket;

    /**
     * Rappresenta i vari stati che può avere un ticket.
     */
    public enum Stati { CREATO, IN_ATTESA_DI_CONFERMA, CONFERMATO, IN_ATTESA_DI_RESTITUZIONE, CHIUSO };

    /**
     * Rappresenta lo stato del ticket.
     */
    @NonNull
    @Column(nullable = false)
    private Stati stato;

    /**
     * Rappresenta la data in cui è stata fatta la richiesta di prestito.
     */
    @NonNull
    @Column(nullable = false)
    private LocalDateTime dataRichiesta;

    /**
     * Rappresenta la data di restituzione del libro.
     */
    @NonNull
    @Column(nullable = false)
    private LocalDateTime dataRestituzione;

    /**
     * Rappresenta il libro che si prende il prestito.
     */
    @NonNull
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Libro libro;
    /**
     * Rappresenta la biblioteca da cui si prende il prestito il libro.
     */
    @NonNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private Biblioteca biblioteca;

    /**
     * Rappresenta il lettore che prende in prestito il libro.
     */
    @NonNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private Lettore lettore;

}
