package it.unisa.c07.biblionet.model.entity.utente;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class UtenteRegistrato {

    @Id
    private String email;
    private String password;
    private String provincia;
    private String citta;
    private String via;
    private String recapitoTelefonico;
}
