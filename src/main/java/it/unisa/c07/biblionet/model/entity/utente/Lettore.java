package it.unisa.c07.biblionet.model.entity.utente;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Lettore extends UtenteRegistrato{

    private String username;
    private String nome;
    private String cognome;
}
