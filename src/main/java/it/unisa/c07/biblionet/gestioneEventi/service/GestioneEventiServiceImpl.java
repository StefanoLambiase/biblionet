package it.unisa.c07.biblionet.gestioneEventi.service;

import org.springframework.stereotype.Service;

import it.unisa.c07.biblionet.model.dao.EventoDAO;
import it.unisa.c07.biblionet.model.entity.Evento;
import lombok.RequiredArgsConstructor;

/**
 * Implementa la classe che esplicita i metodi
 * definiti nell'interfaccia service per il
 * sottosustema GestioneEventi.
 * @author Nicola Pagliara, Luca Topo
 */
@Service
@RequiredArgsConstructor
public class GestioneEventiServiceImpl implements GestioneEventiService {

    /**
     * Si occupa delle operazioni CRUD per un lettore.
     */
    private final EventoDAO eventoDAO;

    /**
     * Implementa la funzionalità che permette
     * ad un Esperto di organizzare un Evento.
     * @param evento L'Evento da memorizzare
     * @return L'Evento appena creato
     */
    @Override
    public Evento creaEvento(final Evento evento) {
        return eventoDAO.save(evento);
    }

}
