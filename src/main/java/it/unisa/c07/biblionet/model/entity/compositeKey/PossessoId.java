package it.unisa.c07.biblionet.model.entity.compositeKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Basic;
import javax.persistence.Embeddable;
import java.io.Serializable;


/**
 * @author Antonio Della Porta
 *
 * Questa classe rappresenta la chiave composta di Possesso.
 * Sono presenti due campi:
 * <ul>
 *     <li> <br>bibliotecaID</br> è la mail della biblioteca dove il libro è conservato</li>
 *     <li> <br>libroID</br> è l'ID del libro</li>
 * </ul>
 *
 * @see it.unisa.c07.biblionet.model.entity.Possesso
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PossessoId implements Serializable {

    @Basic
    private String bibliotecaID;
    @Basic
    private int libroID;



}
