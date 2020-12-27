package it.unisa.c07.biblionet.prenotazioneLibri.service;

import it.unisa.c07.biblionet.model.entity.Libro;
import it.unisa.c07.biblionet.model.entity.Possesso;
import it.unisa.c07.biblionet.model.entity.TicketPrestito;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;

import java.util.List;

/**
 * Implementa l'interfaccia service
 * per il sottosistema PrenotazioneLibri.
 * @author Viviana Pentangelo, Gianmario Voria
 */
public interface PrenotazioneLibriService {

    /**
     * Implementa la funzionalità che permette
     * di visualizzare la lista completa dei libri
     * prenotabili sulla piattaforma.
     * @return La lista di libri
     */
    List<Libro> visualizzaListaLibriCompleta();

    /**
     * Implementa la funzionalità che permette
     * di visualizzare una lista di libri prenotabili
     * filtrata per titolo.
     * @param titolo Stringa che deve essere contenuta
     * nel titolo
     * @return La lista di libri
     */
    List<Libro> visualizzaListaLibriPerTitolo(String titolo);

    /**
     * Implementa la funzionalità che permette
     * di visualizzare la lista completa dei libri
     * prenotabili da una determinata biblioteca.
     * @param nomeBiblioteca Il nome della biblioteca
     * @return La lista di libri
     */
    List<Libro> visualizzaListaLibriPerBiblioteca(String nomeBiblioteca);

    /**
     * Implementa la funzionalità che permette
     * di richiedere un prestito per un libro
     * da una biblioteca.
     * @param lettore Il lettore che lo richiede
     * @param idBiblioteca id della biblioteca
     * @param idLibro id del libro
     * @return Il ticket aperto in attesa di approvazione
     */
    TicketPrestito richiediPrestito(Lettore lettore, String idBiblioteca, int idLibro);

    /**
     * Implementa la funzionalità che permette
     * di ottenere la lista delle biblioteche
     * che posseggono un dato libro
     * @param libro Il libro di cui estrarre le biblioteche
     * @return La lista delle biblioteche che possiedono il libro
     */
    List<Biblioteca> getBibliotecheLibro(Libro libro);

    /**
     * Implementa la funzionalità che permette
     * di ottenere un libro dato il suo ID
     * @param id L'ID del libro da ottenere
     * @return Il libro da ottenere
     */
    Libro getLibroByID(int id);


}
