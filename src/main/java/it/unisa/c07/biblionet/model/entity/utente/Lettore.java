package it.unisa.c07.biblionet.model.entity.utente;

import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.Evento;
import it.unisa.c07.biblionet.model.entity.TicketPrestito;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Lettore extends UtenteRegistrato implements HaGenere{

    @NonNull
    @Column(nullable = false, length = 30)
    private String username;

    @NonNull
    @Column(nullable = false, length = 30)
    private String nome;

    @NonNull
    @Column(nullable = false, length = 30)
    private String cognome;

    @ManyToMany
    private List<Genere>generi;

    @ManyToMany
    private List<ClubDelLibro>clubs;

    @ManyToMany
    private List<Evento> eventi;

    @OneToMany
    private List<TicketPrestito> tickets;

    public Lettore(String email, String password, String provincia, String citta, String via, String recapitoTelefonico, String username, String nome, String cognome) {
        super(email, password, provincia, citta, via, recapitoTelefonico);
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
    }


}
