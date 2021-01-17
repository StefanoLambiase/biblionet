package it.unisa.c07.biblionet.model.entity.compositeKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Basic;
import javax.persistence.Embeddable;
import java.io.Serializable;


/**
 * Questa classe rappresenta la chiave composta di Possesso.
 * Sono presenti due campi:
 * <ul>
 *     <li> <strong>bibliotecaID</strong> è la mail della
 *              biblioteca dove il libro è conservato</li>
 *     <li> <strong>libroID</strong> è l'ID del libro</li>
 * </ul>
 *
 * @author Antonio Della Porta
 * @see it.unisa.c07.biblionet.model.entity.Possesso
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PossessoId implements Serializable {

    /**
     * Rappresenta la chiave primaria di una biblioteca.
     */
    @Basic
    private String bibliotecaID;

    /**
     * Rappresenta la chiave primaria di un libro.
     */
    @Basic
    private int libroID;



}
