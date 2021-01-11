package it.unisa.c07.biblionet.model.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ClubForm {
    @NonNull
    private String nome;

    @NonNull
    private String descrizione;

    @NonNull
    private List<String> generi;

    private MultipartFile copertina;
}
