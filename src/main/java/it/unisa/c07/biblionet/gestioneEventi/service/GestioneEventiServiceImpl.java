package it.unisa.c07.biblionet.gestioneEventi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unisa.c07.biblionet.model.dao.utente.LettoreDAO;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
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
 * @author Viviana Pentangelo
 */
@Service
@RequiredArgsConstructor
public class GestioneEventiServiceImpl implements GestioneEventiService {

    /**
     * Si occupa delle operazioni CRUD per un evento.
     */
    private final EventoDAO eventoDAO;

    /**
     * Si occupa delle operazioni CRUD per un libro.
     */
    private final LibroDAO libroDAO;

    /**
     * Si occupa delle operazioni CRUD per un lettore.
     */
    private final LettoreDAO lettoreDAO;


    /**
     * Implementa la funzionalità che permette
     * di trovare un evento dato il suo identificativo.
     * @param idEvento L'identificativo dell'evento
     * @return L'Evento trovato
     */
    public Optional<Evento> getEventoById(final int idEvento) {
        return eventoDAO.findById(idEvento);
    }

    /**
     * Implementa la funzionalità che permette ad un Esperto di organizzare un
     * Evento.
     * @param evento L'Evento da memorizzare
     * @return L'Evento appena creato
     */
    @Override
    public Evento creaEvento(final Evento evento) {
        return eventoDAO.save(evento);
    }

    /**
     * Implementa la funzionalità che permette
     * di modificare un evento.
     * @param evento La nuova versione dell'evento
     * @return Optional.empty() se l'evento da modificare
     *         non esiste, altrimenti un optional contenente
     *         l'evento modificato.
     */
    @Override
    public Optional<Evento> modificaEvento(final Evento evento) {
        if (!this.eventoDAO.existsById(evento.getIdEvento())) {
            return Optional.empty();
        }
        var eventoSalvato = eventoDAO.save(evento);
        return Optional.of(eventoSalvato);
    }

   /**
     * Metodo di utilità per recuperare
     * un libro a partire dall'ID.
     * @param id Id del libro da recuperare
     * @return Il libro recuperato
     */
    @Override
    public Optional<Libro> getLibroById(final int id) {
        return libroDAO.findById(id);
    }

    /**
     * Implementa la funzionalità che permette
     * ad un Esperto di eliminare un evento.
     * @param id L'id dell'evento da eliminare
     * @return L'evento che è stato eliminato, o
     *         un Optional vuoto se l'evento non
     *         esiste.
     */
    @Override
    public Optional<Evento> eliminaEvento(final int id) {
        var evento = this.eventoDAO.findById(id);
        if (evento.isEmpty()) {
            return Optional.empty();
        }

        this.eventoDAO.deleteById(id);
        return evento;
    }

    /**
     * Implementa la funzionalità che permette
     * ad un Lettore di partecipare ad un evento.
     * @param idLettore Il lettore da iscrivere all'evento
     * @param idEvento L'id dell'evento a cui partecipare
     * @return Il lettore aggiornato ed iscritto all'evento
     */
    @Override
    public Lettore partecipaEvento(final String idLettore, final int idEvento) {
        Evento evento = eventoDAO.getOne(idEvento);
        Lettore lettore = lettoreDAO.findByID(idLettore);
        List<Evento> listaEventi = lettore.getEventi();
        if (listaEventi == null) {
            listaEventi = new ArrayList<>();
        }
        for (Evento e : listaEventi) {
            if (e.getIdEvento() == evento.getIdEvento()) {
                return lettore;
            }
        }
        listaEventi.add(evento);
        lettore.setEventi(listaEventi);
        Lettore l = lettoreDAO.save(lettore);
        return l;
    }

    /**
     * Implementa la funzionalità che permette
     * ad un Lettore di abbandonare un evento.
     * @param idLettore Il lettore da disiscrivere dall'evento
     * @param idEvento L'id dell'evento da abbandonare
     * @return Il lettore aggiornato ed disiscritto dall'evento
     */
    @Override
    public Lettore abbandonaEvento(final String idLettore, final int idEvento) {
        Evento evento = eventoDAO.getOne(idEvento);
        Lettore lettore = lettoreDAO.findByID(idLettore);
        List<Evento> listaEventi = lettore.getEventi();

        //Per chiunque leggesse, non fate domande e non toccate. Grazie
        int pos = -1;
        for (int i = 0; i < listaEventi.size(); i++) {
            if (listaEventi.get(i).getIdEvento() == evento.getIdEvento()) {
                pos = i;
            }
        }
        if (pos > -1) {
            listaEventi.remove(pos);
        }

        lettore.setEventi(listaEventi);
        Lettore l = lettoreDAO.save(lettore);
        return l;
    }

}
