package it.unisa.c07.biblionet.model.entity;

import it.unisa.c07.biblionet.utils.Length;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;

import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Questa classe rappresenta un libro.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Libro {

    /**
     * Rappresenta l'ID autogenerato di un libro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idLibro;

    //Cambiare nell'SDD
    /**
     * Rappresenta il titolo di un libro.
     */
    @NonNull
    @Column(length = Length.LENGTH_90)
    private String titolo;

    //Cambiare nell'SDD
    /**
     * Rappresenta l'autore di un libro.
     */
    @NonNull
    @Column(length = Length.LENGTH_60)
    private String autore;

    /**
     * Rappresenta il codice ISBN di un libro se presente.
     */
    @Column(unique = true, length = Length.LENGTH_13)
    @NonNull
    private String isbn;

    /**
     * Rappresenta l'anno di pubblicazione di un libro.
     */
    @Column(nullable = false)
    @NonNull
    private LocalDateTime annoDiPubblicazione;

    /**
     * Rappresenta la descrione di un libro.
     */
    @Column(nullable = false, length = Length.LENGTH_144)
    @NonNull
    private String descrizione;

    /**
     * Rappresenta la casa editrice di un libro.
     */
    @Column(nullable = false, length = Length.LENGTH_30)
    @NonNull
    private String casaEditrice;

    /**
     * Rappresenta i tickets di cui fa parte il libro.
     */
    @OneToMany(mappedBy = "libro")
    private List<TicketPrestito> tickets;

    /**
     * Rappresenta i generi di un libro.
     */
    @ManyToMany
    private List<Genere> generi;

    /**
     * Rappresente gli eventi di cui un libro è parte.
     */
    @OneToMany(mappedBy = "libro")
    private List<Evento> eventi;

    /**
     * Rappresenta la relazione di possesso con una blioteca.
     */
    @OneToMany(mappedBy = "possessoID.libroID")
    private List<Possesso> possessi;
}
