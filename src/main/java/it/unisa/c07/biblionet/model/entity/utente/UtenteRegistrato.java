package it.unisa.c07.biblionet.model.entity.utente;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class UtenteRegistrato {

    @Id
    @NotNull
    @Size(max = 320)
    private String email;

    @NotNull
    private byte[] password;

    @NotNull
    @Size(max = 30)
    private String provincia;

    @NotNull
    @Size(max = 30)
    private String citta;

    @NotNull
    @Size(max = 30)
    private String via;

    @NotNull
    @Size(max = 30)
    private String recapitoTelefonico;

    public UtenteRegistrato(String email, String password, String provincia, String citta, String via, String recapitoTelefonico) {

        this.email = email;
        this.provincia = provincia;
        this.citta = citta;
        this.via = via;
        this.recapitoTelefonico = recapitoTelefonico;

        /*
        Questo blocco di codice serve per l'hashing della password, utilizzando l'algoritmo SHA-256.
        Una volta concluso, setta la password come byte array correttamente
        */
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-256");
            byte arr[]=md.digest(password.getBytes());
            this.password=arr;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
