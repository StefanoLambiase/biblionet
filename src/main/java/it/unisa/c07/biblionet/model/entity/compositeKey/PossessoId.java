package it.unisa.c07.biblionet.model.entity.compositeKey;

import lombok.Data;

import java.io.Serializable;

@Data
public class PossessoId implements Serializable {
    private String biblioteca;
    private int libro;

}
