package it.unisa.c07.biblionet.model.entity;

import it.unisa.c07.biblionet.model.entity.utente.Lettore;
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
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idEvento;

    @NotNull
    @Size(max = 30)
    private String nomeEvento;

    @NotNull
    @Size(max = 255)
    private String descrizione;

    @NotNull
    private LocalDateTime dataOra;

    @ManyToMany(mappedBy = "eventi")
    private List<Lettore> lettori;

    @NotNull
    @ManyToOne
    private ClubDelLibro club;

    @ManyToOne
    private Libro libro;

}
