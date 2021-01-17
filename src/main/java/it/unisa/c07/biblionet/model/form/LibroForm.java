package it.unisa.c07.biblionet.model.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Rappresenta il Form utilizzato
 * per la creazione di un Libro.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class LibroForm {
    /**
     * Rappresenta il titolo di un libro.
     */
    @NonNull
    private String titolo;

    /**
     * Rappresenta il codice ISBN di un libro se presente.
     */
    private String isbn;

    /**
     * Rappresenta la descrizione di un libro.
     */
    private String descrizione;

    /**
     * Rappresenta la casa editrice di un libro.
     */
    @NonNull
    private String casaEditrice;

    /**
     * Rappresenta l'autore di un libro.
     */
    @NonNull
    private String autore;

    /**
     * Rappresenta i generi di un libro.
     */
    private List<String> generi;

    /**
     * Rappresenta l'immagine di copertina del libro.
     */
    private MultipartFile immagineLibro;
}
