package it.unisa.c07.biblionet.clubDelLibro.service;

import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.Genere;

import java.util.List;

/**
 * @author Viviana Pentangelo, Gianmario Voria
 */
public interface ClubDelLibroService {

    /**
     * Il metodo consente ad un Esperto di creare un Club del Libro.
     * @param club Il Club del Libro da memorizzare
     * @return Il Club del Libro appena creato
     */
    ClubDelLibro creaClubDelLibro(ClubDelLibro club);

    /**
     * Il metodo consente di visualizzare tutti i club del libro.
     * @return La lista dei club
     */
    List<ClubDelLibro> visualizzaClubsDelLibro();

    /**
     * Il metodo serve a recuperare un oggetto
     * della classe genere dato il nome.
     * @param generi Lista dei generi sottoforma di stringa
     * @return Lista dei generi sottoforma di entità
     */
    List<Genere> getGeneri(List<String> generi);

    /**
     * Il metodo serve a modificare ed
     * effettuare l'update di un club.
     * @param club Il club da modificare
     * @return Il club modificato
     */
    ClubDelLibro modificaDatiClub(ClubDelLibro club);

    /**
     * Il metodo serve a recuperare un
     * club dato il suo ID.
     * @param id L'ID del club da recuperare
     * @return Il club recuperato
     */
    ClubDelLibro getClubByID(int id);

}
