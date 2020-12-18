package it.unisa.c07.biblionet.model.entity.utente;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
public class Biblioteca extends UtenteRegistrato{
    @NotNull
    @Size(max = 30)
    String nomeBiblioteca;

    public Biblioteca(String email, String password, String provincia, String citta, String via, String recapitoTelefonico, String nomeBiblioteca) {
        super(email, password, provincia, citta, via, recapitoTelefonico);
        this.nomeBiblioteca = nomeBiblioteca;
    }
}
