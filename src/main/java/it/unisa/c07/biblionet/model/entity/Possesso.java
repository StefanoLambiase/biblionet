package it.unisa.c07.biblionet.model.entity;

import it.unisa.c07.biblionet.model.entity.compositeKey.PossessoId;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * @author Antonio Della Porta
 *
 * Questa classe rappresenta la relazione molti a molti fra una biblioteca
 * e un libro. È identificata univocamente da una chiave composta PossessoID
 * e contiene il numero di copie di quel libro possedute dalla biblioteca.
 * @see PossessoId
 * @see Serializable
 */
@Entity
@Data
@NoArgsConstructor
public class Possesso implements Serializable {

    /**
     * Rappresenta l'ID del possesso.
     */
    @EmbeddedId
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PossessoId possessoID;

    /**
     * Rappresenta il numero di copie di un libro posseduto da una biblioteca.
     */
    @NonNull
    @Column(nullable = false)
    private int numeroCopie;

    /**
     * Questo costruttore prende come argomento due parametri.
     * @param possessoId la chiave composta della tabella.
     * @param numeroCopie il numero di copie del libro.
     *
     * <br><br>
     *
     * Per inserire correttamente la tupla nel database è necessario avere:
     *  <ul>
     *      <li>La chiave primaria della biblioteca in cui il libro
     *                   deve essere inserito: <strong>pk_biblio</strong></li>
     *      <li>La chiave primaria del libro: <strong>pk_libro</strong></li>
     *  </ul>
     *
     *
     *   Esempio di costruzione:<br>
     *   new Possesso( new PossessoId ( <strong>pk_biblio</strong> ,
     *                    <strong>pk_libro</strong>) ,
     *                    <strong>numero_copie</strong> );
     *   @see PossessoId
     */
    public Possesso(final PossessoId possessoId, final int numeroCopie) {
        this.possessoID = possessoId;
        this.numeroCopie = numeroCopie;
    }
}
