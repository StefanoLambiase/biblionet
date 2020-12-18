package it.unisa.c07.biblionet.model.entity;

import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
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
public class TicketPrestito {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idTicket;

    @NotNull
    private byte stato;

    @NotNull
    private LocalDateTime dataRichiesta;

    @NotNull
    private LocalDateTime dataRestituzione;

    @NotNull
    @ManyToOne
    private Libro libro;

    @NotNull
    @ManyToOne
    private Biblioteca biblioteca;

    @NotNull
    @ManyToOne
    private Lettore lettore;

}
