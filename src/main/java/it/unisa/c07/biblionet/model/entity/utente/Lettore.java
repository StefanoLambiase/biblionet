package it.unisa.c07.biblionet.model.entity.utente;

import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.Evento;
import it.unisa.c07.biblionet.model.entity.TicketPrestito;
import it.unisa.c07.biblionet.utils.Length;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@SuperBuilder
@Data
@NoArgsConstructor
public class Lettore extends UtenteRegistrato implements HaGenere{

    @NonNull
    @Column(nullable = false, length = Length.LENGTH_30)
    private String username;

    @NonNull
    @Column(nullable = false, length = Length.LENGTH_30)
    private String nome;

    @NonNull
    @Column(nullable = false, length = Length.LENGTH_30)
    private String cognome;

    @ManyToMany
    private List<Genere>generi;

    @ManyToMany
    private List<ClubDelLibro>clubs;

    @ManyToMany
    private List<Evento> eventi;

    @OneToMany
    private List<TicketPrestito> tickets;

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
