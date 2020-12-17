package it.unisa.c07.biblionet.model.entity.utente;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class UtenteRegistrato {

    @Id
    private String email;
    private String password;
    private String provincia;
    private String citta;
    private String via;
    private String recapitoTelefonico;

    public UtenteRegistrato(String email, String password, String provincia, String citta, String via, String recapitoTelefonico) {
        this.email = email;
        this.password = password;
        this.provincia = provincia;
        this.citta = citta;
        this.via = via;
        this.recapitoTelefonico = recapitoTelefonico;
    }
}
