package it.unisa.c07.biblionet.gestioneEventi.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import it.unisa.c07.biblionet.model.dao.EventoDAO;
import it.unisa.c07.biblionet.model.dao.LibroDAO;
import it.unisa.c07.biblionet.model.entity.Evento;
import it.unisa.c07.biblionet.model.entity.Libro;
import lombok.RequiredArgsConstructor;

/**
 * Implementa la classe che esplicita i metodi definiti nell'interfaccia service
 * per il sottosustema GestioneEventi.
 *
 * @author Nicola Pagliara
 * @author Luca Topo
 */
@Service
@RequiredArgsConstructor
public class GestioneEventiServiceImpl implements GestioneEventiService {

    /**
     * Si occupa delle operazioni CRUD per un lettore.
     */
    private final EventoDAO eventoDAO;

    /**
     * Si occupa delle operazioni CRUD per un lettore.
     */
    private final LibroDAO libroDAO;

    /**
     * Implementa la funzionalità che permette ad un Esperto di organizzare un
     * Evento.
     *
     * @param evento L'Evento da memorizzare
     * @return L'Evento appena creato
     */
    @Override
    public Evento creaEvento(final Evento evento) {
        return eventoDAO.save(evento);
    }

    /**
     * Implementa la funzionalità che permette di recuperare un libro a
     * partire dall'ID.
     */
    @Override
    public Optional<Libro> getLibroById(final int id) {
        return libroDAO.findById(id);
    }

}
