package it.unisa.c07.biblionet.model.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Classe che rappresenta il form per la creazione di un club del libro.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ClubForm {

    /**
     * Nome del club.
     */
    @NonNull
    private String nome;

    /**
     * Descrizione del club.
     */
    @NonNull
    private String descrizione;

    /**
     * Lista di generi del club.
     */
    @NonNull
    private List<String> generi;

    /**
     * Copertina del club.
     */
    private MultipartFile copertina;
}
