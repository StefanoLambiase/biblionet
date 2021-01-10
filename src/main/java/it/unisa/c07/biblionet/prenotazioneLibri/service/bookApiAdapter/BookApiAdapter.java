package it.unisa.c07.biblionet.prenotazioneLibri.service.bookApiAdapter;

import it.unisa.c07.biblionet.model.entity.Libro;

/**
 * Rappresenta l'interfaccia dello Adapter usata
 * dalle classi di BiblioNet per la ricerca di un
 * libro, tramite ISBN, attraverso l'uso di API esterne
 */
public interface BookApiAdapter {
    Libro getLibroDaBookApi(String isbn);
}
