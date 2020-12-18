package it.unisa.c07.biblionet.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

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

    private int idLibro;

    @NotNull
    private int idClub;

    @ManyToOne
    private ClubDelLibro club;

}
