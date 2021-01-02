package it.unisa.c07.biblionet.model.entity.utente;

import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.utils.Length;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.Transient;

import java.util.List;

/**
 * Questa classe rappresenta un Esperto
 * Un esperto possiede un username ,un nome e un cognome.
 * Un esperto ha una biblioteca per cui lavora,
 * una lista di generi di cui è esperto,
 * e una lista di club che gestisce.
 */
@Entity
@SuperBuilder
@Data
@NoArgsConstructor
public class Esperto extends UtenteRegistrato implements HaGenere {

    /**
     * Rappresenta l'username dell'esperto.
     */
    @NonNull
    @Column(nullable = false, length = Length.LENGTH_30)
    private String username;

    /**
     * Rappresenta il nome dell'esperto.
     */
    @NonNull
    @Column(nullable = false, length = Length.LENGTH_30)
    private String nome;

    /**
     * Rappresenta il cognome dell'esperto.
     */
    @NonNull
    @Column(nullable = false, length = Length.LENGTH_30)
    private String cognome;

    /**
     * Rappresenta la bibloteca dove lavora l'esperto.
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Biblioteca biblioteca;

    /**
     * Rappresenta la lista di generi di cui un esperto è esperto.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Genere> generi;

    /**
     * Rappresenta la lista di club gestiti dall'esperto.
     */
    @OneToMany
    @ToString.Exclude
    private List<ClubDelLibro> clubs;

    /**
     * Rappresenta il tipo di utente.
     */
    @Transient
    String tipo = "Esperto";

    /**
     *
     * @param email È la mail dell'esperto.
     * @param password È la password di accesso dell'esperto.
     * @param provincia È la provincia in cui lavora l'esperto.
     * @param citta È la città in cui lavora l'esperto.
     * @param via È l'indirizzo in cui lavora l'esperto.
     * @param recapitoTelefonico È il numero di telefono dell'esperto.
     * @param username È l'username dell'esperto.
     * @param nome È il nome dell'esperto.
     * @param cognome È il cognome dell'esperto.
     * @param biblioteca È la biblioteca dove lavora l'esperto.
     */
    public Esperto(final String email, final String password,
                   final String provincia, final String citta,
                   final String via,
                   final String recapitoTelefonico, final String username,
                   final String nome, final String cognome,
                   final Biblioteca biblioteca) {
        super(email, password, provincia, citta, via, recapitoTelefonico);
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.biblioteca = biblioteca;
    }

}
