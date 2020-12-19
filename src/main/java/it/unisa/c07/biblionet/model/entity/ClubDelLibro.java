package it.unisa.c07.biblionet.model.entity;

import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import java.sql.Blob;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class ClubDelLibro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idClub;

    @NonNull
    @Column(nullable = false, length = 30)
    private String nome;

    @NonNull
    @Column(nullable = false, length = 30)
    private String descrizione;


    @Lob //L'annotazione serve ad Hibernate, poichè Blob è una interfaccia, e di conseguenza non saprebbe come mapparla all'interno di un DB,
         // tramite l'utilizzo di questa annotazione, l'immagine viene salvata come un Large OBject
    private Blob immagineCopertina;

    @NonNull
    @ManyToOne
    @JoinColumn(nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Esperto esperto;

    @ManyToMany
    private List<Lettore>lettori;

    @ManyToMany
    private List<Genere> generi;

    @OneToMany
    private List<Evento> eventi;

}
