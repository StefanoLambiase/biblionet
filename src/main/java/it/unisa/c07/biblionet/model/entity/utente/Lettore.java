package it.unisa.c07.biblionet.model.entity.utente;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
public class Lettore extends UtenteRegistrato{

    @NotNull
    @Size(max=30)
    private String username;

    @NotNull
    @Size(max=30)
    private String nome;

    @NotNull
    @Size(max=30)
    private String cognome;

    public Lettore(String email, String password, String provincia, String citta, String via, String recapitoTelefonico, String username, String nome, String cognome) {
        super(email, password, provincia, citta, via, recapitoTelefonico);
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
    }
}
