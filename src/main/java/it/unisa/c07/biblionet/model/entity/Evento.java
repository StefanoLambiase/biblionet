package it.unisa.c07.biblionet.model.entity;

import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idEvento;

    @NonNull
    @Column(nullable = false, length = 30)
    private String nomeEvento;

    @NonNull
    @Column(nullable = false, length = 255)
    private String descrizione;

    @NonNull
    private LocalDateTime dataOra;

    @ManyToMany(mappedBy = "eventi")
    private List<Lettore> lettori;

    @NonNull
    @Column(nullable = false)
    @ManyToOne
    private ClubDelLibro club;
    
    @ManyToOne
    private Libro libro;

}
