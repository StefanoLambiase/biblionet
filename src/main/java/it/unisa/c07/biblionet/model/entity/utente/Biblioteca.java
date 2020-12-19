package it.unisa.c07.biblionet.model.entity.utente;

import it.unisa.c07.biblionet.model.entity.TicketPrestito;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@SuperBuilder
@Data
@NoArgsConstructor
public class Biblioteca extends UtenteRegistrato{

    @Column(nullable = false, length = 30)
    @NonNull
    String nomeBiblioteca;

    @OneToMany
    private List<Esperto>esperti;

    @OneToMany
    private List<TicketPrestito>tickets;

  /*  @OneToMany(mappedBy = "biblioteca")
    private Set<Possesso> possessi;*/

    public Biblioteca(String email, String password, String provincia, String citta, String via, String recapitoTelefonico, String nomeBiblioteca) {
        super(email, password, provincia, citta, via, recapitoTelefonico);
        this.nomeBiblioteca = nomeBiblioteca;
    }

}
