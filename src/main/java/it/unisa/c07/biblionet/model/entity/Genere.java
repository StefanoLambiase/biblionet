package it.unisa.c07.biblionet.model.entity;

import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Genere {

    @Id
    @NonNull
    @Column(nullable= false, length = 30)
    private String nome;

    @NonNull
    @Column(nullable = false, length = 30)
    private String descrizione;

    @ManyToMany(mappedBy = "generi")
    private List<Lettore>lettori;

    @ManyToMany(mappedBy = "generi")
    private List<Esperto>esperti;

    @ManyToMany(mappedBy = "generi")
    private List<Libro> libri;

    @ManyToMany(mappedBy = "generi")
    private List<ClubDelLibro> clubs;

}
