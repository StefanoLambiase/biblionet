package it.unisa.c07.biblionet.model.entity.utente;

import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.Genere;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Esperto extends UtenteRegistrato implements HaGenere{

    @NotNull
    @Size(max = 30)
    private String username;

    @NotNull
    @Size(max = 30)
    private String nome;

    @NotNull
    @Size(max = 30)
    private String cognome;

    @ManyToOne
    private Biblioteca biblioteca;

    @ManyToMany
    private List<Genere>generi;

    @OneToMany
    private List<ClubDelLibro>clubs;

    //Da Cambiare
    public Esperto(String email, String password, String provincia, String citta, String via, String recapitoTelefonico, String username, String nome, String cognome) {
        super(email, password, provincia, citta, via, recapitoTelefonico);
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
    }

}
