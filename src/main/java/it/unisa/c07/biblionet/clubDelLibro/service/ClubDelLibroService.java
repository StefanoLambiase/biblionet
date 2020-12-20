package it.unisa.c07.biblionet.clubDelLibro.service;

import it.unisa.c07.biblionet.model.entity.ClubDelLibro;

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



}
