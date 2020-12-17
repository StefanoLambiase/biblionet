package it.unisa.c07.biblionet.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idEvento;
    private String nomeEvento;
    private String descrizione;
    private String dataOra;
    private int idLibro;
    private int idClub;



}
