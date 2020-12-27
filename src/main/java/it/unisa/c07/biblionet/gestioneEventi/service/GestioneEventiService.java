package it.unisa.c07.biblionet.gestioneEventi.service;

import java.util.Optional;

import it.unisa.c07.biblionet.model.entity.Evento;
import it.unisa.c07.biblionet.model.entity.Libro;

/**
 * Implementa l'interfaccia service
 * per il sottosistema GestioneEventi.
 * @author Nicola Pagliara
 * @author Luca Topo
 */
public interface GestioneEventiService {

    /**
     * Implementa la funzionalità che permette
     * ad un Esperto di organizzare un Evento.
     * @param evento L'Evento da memorizzare
     * @return L'Evento appena creato
     */
    Evento creaEvento(Evento evento);

    /**
     * Metodo di utilità per recuperare
     * un libro a partire dall'ID.
     * @param id Id del libro da recuperare
     * @return Il libro recuperato
     */
    Optional<Libro> getLibroById(int id);

}
