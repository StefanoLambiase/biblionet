package it.unisa.c07.biblionet.prenotazioneLibri.service;

import it.unisa.c07.biblionet.model.entity.Libro;

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


}
