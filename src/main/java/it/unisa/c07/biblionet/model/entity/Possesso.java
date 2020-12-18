package it.unisa.c07.biblionet.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
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
    @Column(length = 30)
    private String nomeBiblioteca;

    @Id
    private int idLibro;

    @NonNull
    @Column(nullable = false)
    private int numeroCopie;
}
