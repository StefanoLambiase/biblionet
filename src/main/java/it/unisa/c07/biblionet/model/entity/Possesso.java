package it.unisa.c07.biblionet.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Possesso implements Serializable {
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
