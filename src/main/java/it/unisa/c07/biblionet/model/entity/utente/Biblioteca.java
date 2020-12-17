package it.unisa.c07.biblionet.model.entity.utente;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Biblioteca extends UtenteRegistrato{
    String nomeBiblioteca;

}
