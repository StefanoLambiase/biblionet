package it.unisa.c07.biblionet.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Possesso {
    @Id
    private String nomeBiblioteca;
    @Id
    private int idLibro;
    private int numeroCopie;
}
