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
public class TicketPrestito {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idTicket;

    @NotNull
    private byte stato;

    @NotNull
    @Size(max = 30)
    private String emailBiblioteca;

    @NotNull
    @Size(max = 30)
    private String emailLettore;

    @NotNull
    private LocalDateTime dataRichiesta;

    @NotNull
    private LocalDateTime dataRestituzione;

    @ManyToOne
    private Libro libro;


}
