package it.unisa.c07.biblionet.comunicazioneEsperto.service;

import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;

import java.util.List;

/**
 * @author Alessio Casolaro
 * @author Antonio Della Porta
 */
public interface ComunicazioneEspertoService {

    /**
     * Implementa la funzionalità di cercare degli esperti che hanno
     * come generi preferiti quelli passati.
     * @param generi i generi da cercare
     * @return la lista di esperti
     */
    List<Esperto> getEspertiByGeneri(List<Genere> generi);

    /**
     * Implementa la funzionalità che restituisce la lista
     * di tutti gli Esperti del DB.
     * @return la lista di esperti
     */
    List<Esperto> getAllEsperti();

    /**
     * Implementa la funzionalità che restituisce la lista
     * di tutti gli Esperti del DB filtrati per nome.
     * @param name il nome con cui filtrare
     * @return la lista di esperti
     */
    List<Esperto> getEsperiByName(String name);

    /**
     * Implementa la funzionalità che permette
     * di visualizzare la lista completa dei libri
     * prenotabili di un dato genere.
     * @param genere Il nome del genere
     * @return La lista di libri
     */
    List<Esperto> visualizzaEspertiPerGenere(String genere);
}
