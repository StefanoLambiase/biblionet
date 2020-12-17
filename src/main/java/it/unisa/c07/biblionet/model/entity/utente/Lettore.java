package it.unisa.c07.biblionet.model.entity.utente;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
public class Lettore extends UtenteRegistrato{

    private String username;
    private String nome;
    private String cognome;

    public Lettore(String email, String password, String provincia, String citta, String via, String recapitoTelefonico, String username, String nome, String cognome) {
        super(email, password, provincia, citta, via, recapitoTelefonico);
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
    }
}
