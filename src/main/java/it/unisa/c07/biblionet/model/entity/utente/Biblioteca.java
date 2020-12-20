package it.unisa.c07.biblionet.model.entity.utente;

import it.unisa.c07.biblionet.model.entity.Possesso;
import it.unisa.c07.biblionet.model.entity.TicketPrestito;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@SuperBuilder
@Data
@NoArgsConstructor
public class Biblioteca extends UtenteRegistrato{
    //cambiare nell'SDD
    @Column(nullable = false, length = 60)
    @NonNull
    String nomeBiblioteca;

    @OneToMany(mappedBy = "biblioteca")
    private List<Esperto>esperti;

    @OneToMany
    private List<TicketPrestito>tickets;

    @OneToMany(mappedBy = "possessoID.bibliotecaID")
    private List<Possesso> possessi;

    public Biblioteca(String email, String password, String provincia, String citta, String via, String recapitoTelefonico, String nomeBiblioteca) {
        super(email, password, provincia, citta, via, recapitoTelefonico);
        this.nomeBiblioteca = nomeBiblioteca;
    }

}
