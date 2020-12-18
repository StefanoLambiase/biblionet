package it.unisa.c07.biblionet.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubDelLibro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idClub;
    private String nome;
    private String descrizione;
    private String emailProprietario;
    @Lob
    private Blob immagineCopertina;

}
