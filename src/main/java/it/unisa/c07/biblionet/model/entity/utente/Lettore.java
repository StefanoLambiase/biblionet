package it.unisa.c07.biblionet.model.entity.utente;

import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.Evento;
import it.unisa.c07.biblionet.model.entity.TicketPrestito;
import it.unisa.c07.biblionet.utils.Length;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.List;

/**
 * Questa classe rappresenta un Lettore.
 * Un Lettore può essere interessato a più generi,
 * può partecipare a più eventi,
 * e far parte di più club.
 */
@Entity
@SuperBuilder
@Data
@NoArgsConstructor
public class Lettore extends UtenteRegistrato implements HaGenere {

    /**
     * Rappresente un lettore sulla piattaforma.
     */
    @NonNull
    @Column(nullable = false, length = Length.LENGTH_30)
    private String username;

    /**
     * Rappresenta il nome del lettore.
     */
    @NonNull
    @Column(nullable = false, length = Length.LENGTH_30)
    private String nome;

    /**
     * Rappresenta il cognome di un lettore.
     */
    @NonNull
    @Column(nullable = false, length = Length.LENGTH_30)
    private String cognome;

    /**
     * Rappresenta i generi che interessano ad un lettore.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Genere> generi;

    /**
     * Rappresenta i clubs a cui il lettore appartiene.
     */
    @ManyToMany
    @ToString.Exclude
    private List<ClubDelLibro> clubs;

    /**
     * Rappresenta gli eveti a cui prende parte.
     */
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @ToString.Exclude
    private List<Evento> eventi;

    /**
     * Rappresenta i tickets a cui è collegato.
     */
    @OneToMany
    @ToString.Exclude
    private List<TicketPrestito> tickets;

    /**
     * Rappresenta il tipo di utente.
     */
    @Transient
    private String tipo = "Lettore";


    /**
     *
     * @param email la email del lettore.
     * @param password la password del lettore.
     * @param provincia la provincia dove vive
     * @param citta la città del lettore.
     * @param via la via dove vive.
     * @param recapitoTelefonico il recapito del lettore.
     * @param username l'usurname del lettore.
     * @param nome il nome del lettore.
     * @param cognome il cognome del lettore.
     */
    public Lettore(final String email, final String password,
                   final String provincia, final String citta,
                   final String via, final String recapitoTelefonico,
                   final String username, final String nome,
                   final String cognome) {
        super(email, password, provincia, citta, via, recapitoTelefonico);
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
    }


}
