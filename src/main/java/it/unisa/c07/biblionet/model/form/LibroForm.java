package it.unisa.c07.biblionet.model.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class LibroForm {
    @NonNull
    private String titolo;

    private String isbn;

    private String descrizione;

    @NonNull
    private String casaEditrice;

    @NonNull
    private String autore;

    private List<String> generi;

    private MultipartFile immagineLibro;
}
