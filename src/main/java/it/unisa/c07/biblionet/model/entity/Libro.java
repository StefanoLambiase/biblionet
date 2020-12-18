package it.unisa.c07.biblionet.model.entity;


import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idLibro;

    @NotNull
    @Size(max = 30)
    private String titolo;

    @Size(max = 30)
    private String autore;

    @Column(unique = true)
    @Size(max = 13,min = 13) //Questa cosa serve per definire che l'ISBN deve essere solamente di 13 cifre
    private String ISBN;

    @NotNull
    private LocalDateTime annoDiPubblicazione;

    @Size(max = 144)
    private String descrizione;

    @NotNull
    @Size(max = 30)
    private String casaEditrice;

    @OneToMany
    private List<TicketPrestito> tickets;

    @ManyToMany
    private List<Genere> generi;





}
