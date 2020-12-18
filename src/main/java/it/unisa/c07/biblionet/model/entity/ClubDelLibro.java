package it.unisa.c07.biblionet.model.entity;

import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Blob;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubDelLibro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private int idClub;

    @NotNull
    @Size(max = 30)
    private String nome;

    @NotNull
    @Size (max = 255)
    private String descrizione;

    @NotNull
    @Size(max = 30)
    private String emailProprietario;

    @Lob
    private Blob immagineCopertina;

    @ManyToOne
    private Esperto esperto;

    @ManyToMany
    private List<Lettore>lettori;
}
