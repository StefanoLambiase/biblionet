package it.unisa.c07.biblionet.model.entity;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.utils.Length;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import java.util.List;

/**
 * Questa classe rappresenta un Genere letterario.
 * Un genere possiede un nome ed una descrizione.
 * Un genere ha una lista di esperti che lo conoscono,
 * una lista di lettori a cui piace,
 * una lista di libri di quel genere
 * e una lista di club incentrati su di esso.
 */
@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Genere {

    /**
     * Rappresenta il nome nome del genere,
     * nonchè il suo identificativo.
     */
    @Id
    @NonNull
    @Column(nullable = false, length = Length.LENGTH_30)
    private String nome;

    /**
     * Rappresenta la descrizione del genere.
     */
    @NonNull
    @Column(nullable = false, length = Length.LENGTH_255)
    private String descrizione;

    /**
     * Rappresenta la lista di lettori
     * a cui piace questo genere.
     */
    @ManyToMany(mappedBy = "generi")
    private List<Lettore> lettori;

    /**
     * Rappresenta la lista degli esperti in questo genere.
     */
    @ManyToMany(mappedBy = "generi")
    private List<Esperto> esperti;

    /**
     * Rappresenta la lista di libri aventi questo genere.
     */
    @ManyToMany(mappedBy = "generi")
    private List<Libro> libri;

    /**
     * Rappresenta la lista di Club del libro
     * riguardanti questo genere.
     */
    @ManyToMany(mappedBy = "generi")
    private List<ClubDelLibro> clubs;

}
