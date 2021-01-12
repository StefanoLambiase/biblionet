package it.unisa.c07.biblionet.model.entity.utente;

import it.unisa.c07.biblionet.model.entity.Possesso;
import it.unisa.c07.biblionet.model.entity.TicketPrestito;
import it.unisa.c07.biblionet.utils.Length;
import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.List;

/**
 * Questa classe rappresenta una Biblioteca.
 * Una Biblioteca possiede un nome, la lista degli esperti
 * che lavorano presso di essa, la lista di libri che possiede
 * che quindi può prestare ad un lettore,
 * e una lista di ticket che rappresentano le richieste di prestito dei lettori.
 */
@Entity
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Biblioteca extends UtenteRegistrato {


    /**
     * Rappresenta il nome della biblioteca.
     */
    @Column(nullable = false, length = Length.LENGTH_60)
    @NonNull
    private String nomeBiblioteca;

    /**
     * Rappresenta la lista di esperti che lavorano nella biblioteca.
     */
    @OneToMany(mappedBy = "biblioteca")
    @ToString.Exclude
    private List<Esperto> esperti;

    /**
     * Rappresenta la lista di ticket riguardanti le richieste di prestito.
     */
    @OneToMany
    @ToString.Exclude
    private List<TicketPrestito> tickets;

    /**
     * Rappresenta la lista di libri posseduti dalla biblioteca.
     */
    @OneToMany(mappedBy = "possessoID.bibliotecaID")
    @ToString.Exclude
    private List<Possesso> possessi;

    /**
     * Rappresenta il tipo di utente.
     */
    @Transient
    private String tipo = "Biblioteca";

    /**
     *
     * @param email È la mail della biblioteca.
     * @param password È la password di accesso della biblioteca.
     * @param provincia È la provincia in cui ha sede la biblioteca.
     * @param citta È la città in cui ha sede la biblioteca.
     * @param via È l'indirizzo in cui ha sede la biblioteca.
     * @param recapitoTelefonico È il numero di telefono della biblioteca.
     * @param nomeBiblioteca È il nome della biblioteca.
     */
    public Biblioteca(final String email, final String password,
                      final String provincia, final String citta,
                      final String via, final String recapitoTelefonico,
                      final String nomeBiblioteca) {

        super(email, password, provincia, citta, via, recapitoTelefonico);
        this.nomeBiblioteca = nomeBiblioteca;
    }

}
