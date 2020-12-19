package it.unisa.c07.biblionet.model.entity;

import it.unisa.c07.biblionet.model.entity.compositeKey.PossessoId;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
/*
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(PossessoId.class)*/
public class Possesso implements Serializable {

    /*@Id
    @ManyToOne*/
    private Biblioteca biblioteca;

    /*@Id
    @ManyToOne*/
    private Libro libro;

    /*@NonNull
    @Column(nullable = false)*/
    private int numeroCopie;
}
