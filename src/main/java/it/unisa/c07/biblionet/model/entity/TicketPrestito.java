package it.unisa.c07.biblionet.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketPrestito {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idTicket;
    private byte stato;
    private String emailBiblioteca;
    private String emailLettore;
    private LocalDateTime dataRichiesta;
    private LocalDateTime dataRestituzione;


}
