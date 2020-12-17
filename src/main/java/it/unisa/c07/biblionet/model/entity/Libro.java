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
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idLibro;
    private String titolo;
    private String autore;
    private int ISBN;
    private String descrizione;
    private String casaEditrice;
    private LocalDateTime annoDiPubblicazione;

}
