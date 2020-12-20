package it.unisa.c07.biblionet.model.entity.utente;

import it.unisa.c07.biblionet.utils.Length;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity
@SuperBuilder
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class UtenteRegistrato {

    @Id
    @Column(nullable = false, length = Length.LENGTH_320)
    @NonNull
    private String email;

    @Column(nullable = false, length = Length.LENGTH_32)
    @NonNull
    private byte[] password;

    @Column(nullable = false, length = Length.LENGTH_30)
    @NonNull
    private String provincia;

    @Column(nullable = false, length = Length.LENGTH_30)
    @NonNull
    private String citta;

    @Column(nullable = false, length = Length.LENGTH_30)
    @NonNull
    private String via;

    @Column(nullable = false, length = Length.LENGTH_30)
    @NonNull
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
