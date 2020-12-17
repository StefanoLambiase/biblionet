package it.unisa.c07.biblionet.model.entity.utente;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Lettore extends UtenteRegistrato{

    private String username;
    private String nome;
    private String cognome;
}
