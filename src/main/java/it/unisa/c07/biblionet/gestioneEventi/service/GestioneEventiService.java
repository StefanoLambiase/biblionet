package it.unisa.c07.biblionet.gestioneEventi.service;

import it.unisa.c07.biblionet.model.entity.Evento;

/**
 * Implementa l'interfaccia service
 * per il sottosistema GestioneEventi.
 * @author Nicola Pagliara, Luca Topo
 */
public interface GestioneEventiService {

    /**
     * Implementa la funzionalità che permette
     * ad un Esperto di organizzare un Evento.
     * @param evento L'Evento da memorizzare
     * @return L'Evento appena creato
     */
    Evento creaEvento(Evento evento);

}
