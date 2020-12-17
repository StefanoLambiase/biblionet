package it.unisa.c07.biblionet.model.entity.utente;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Esperto extends UtenteRegistrato {

    private String nome;
    private String cognome;
    private String username;
}
