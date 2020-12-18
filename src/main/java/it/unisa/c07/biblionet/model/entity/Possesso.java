package it.unisa.c07.biblionet.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Possesso {
    @Id
    @Size(max = 30)
    @NotNull
    private String nomeBiblioteca;

    @Id
    @NotNull
    private int idLibro;

    @NotNull
    private int numeroCopie;
}
