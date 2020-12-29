package it.unisa.c07.biblionet.prenotazioneLibri.service;

import it.unisa.c07.biblionet.model.dao.LibroDAO;
import it.unisa.c07.biblionet.model.dao.PossessoDAO;
import it.unisa.c07.biblionet.model.dao.TicketPrestitoDAO;
import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.entity.Libro;
import it.unisa.c07.biblionet.model.entity.Possesso;
import it.unisa.c07.biblionet.model.entity.TicketPrestito;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementa la classe che esplicita i metodi
 * definiti nell'interfaccia service per il
 * sottosustema PrenotazioneLibri.
 * @author Viviana Pentangelo, Gianmario Voria
 */
@Service
@RequiredArgsConstructor
public class PrenotazioneLibriServiceImpl implements PrenotazioneLibriService {

    /**
     *Si occupa delle operazioni CRUD per libro.
     */
    private final LibroDAO libroDAO;

    /**
     *Si occupa delle operazioni CRUD per biblioteca.
     */
    private final BibliotecaDAO bibliotecaDAO;

    /**
     *Si occupa delle operazioni CRUD per possesso.
     */
    private final PossessoDAO possessoDAO;

    /**
     *Si occupa delle operazioni CRUD per ticket.
     */
    private final TicketPrestitoDAO ticketPrestitoDAO;


    /**
     * Implementa la funzionalità che permette
     * di visualizzare la lista completa dei libri
     * prenotabili sulla piattaforma.
     * @return La lista di libri
     */
    @Override
    public List<Libro> visualizzaListaLibriCompleta() {
        return libroDAO.findAll(Sort.by("titolo"));
    }

    /**
     * Implementa la funzionalità che permette
     * di visualizzare una lista di libri prenotabili
     * filtrata per titolo.
     * @param titolo Stringa che deve essere contenuta
     * nel titolo
     * @return La lista di libri
     */
    @Override
    public List<Libro> visualizzaListaLibriPerTitolo(final String titolo) {
        return libroDAO.findByTitoloLike(titolo);
    }

    /**
     * Implementa la funzionalità che permette
     * di visualizzare la lista completa dei libri
     * prenotabili da una determinata biblioteca.
     * MI RIFIUTO DI TESTARLO.
     * @param nomeBiblioteca Il nome della biblioteca
     * @return La lista di libri
     */
    @Override
    public List<Libro> visualizzaListaLibriPerBiblioteca(
                    final String nomeBiblioteca) {
        Biblioteca b = bibliotecaDAO.findByNome(nomeBiblioteca);
        String bibID = b.getEmail();
        List<Possesso> possessi = possessoDAO.findByBibliotecaID(bibID);
        List<Libro> libri = new ArrayList<>();
        for (Possesso p : possessi) {
            Optional<Libro> l =
                    libroDAO.findById(p.getPossessoID().getLibroID());
            libri.add(l.orElse(null));
        }
        return libri;
    }

    /**
     * Implementa la funzionalità che permette
     * di richiedere un prestito per un libro
     * da una biblioteca.
     * @param lettore Il lettore che lo richiede
     * @param idBiblioteca id della biblioteca
     * @param idLibro id del libro
     * @return Il ticket aperto in attesa di approvazione
     */
    @Override
    public TicketPrestito richiediPrestito(final Lettore lettore,
                                           final String idBiblioteca,
                                           final int idLibro) {
        TicketPrestito ticket = new TicketPrestito();
        ticket.setLettore(lettore);
        ticket.setDataRichiesta(LocalDateTime.now());
        ticket.setStato(TicketPrestito.Stati.IN_ATTESA_DI_CONFERMA);

        Biblioteca biblioteca =
                bibliotecaDAO.findByID(idBiblioteca);
        Libro libro = libroDAO.getOne(idLibro);

        ticket.setBiblioteca(biblioteca);
        ticket.setLibro(libro);

        ticketPrestitoDAO.save(ticket);
        return ticket;
    }

    /**
     * Implementa la funzionalità che permette
     * di ottenere la lista delle biblioteche
     * che posseggono un dato libro.
     * @param libro Il libro di cui estrarre le biblioteche
     * @return La lista delle biblioteche che possiedono il libro
     */
    @Override
    public  List<Biblioteca> getBibliotecheLibro(final Libro libro) {
        List<Biblioteca> lista = new ArrayList<>();
        for (Possesso p : libro.getPossessi()) {
            lista.add(bibliotecaDAO.
                        findByID(p.getPossessoID().getBibliotecaID()));
        }
        return lista;
    }

    /**
     * Implementa la funzionalità che permette
     * di ottenere un libro dato il suo ID.
     * @param id L'ID del libro da ottenere
     * @return Il libro da ottenere
     */
    @Override
    public Libro getLibroByID(final int id) {
        return libroDAO.getOne(id);
    }

    /**
     * Implementa la funzionalità che permette
     * di ottenere una lista di richieste per una biblioteca.
     * @param biblioteca la biblioteca di cui vedere le richieste
     * @return La lista di richieste
     */
    @Override
    public List<TicketPrestito> getTicketsByBiblioteca(
                            final Biblioteca biblioteca) {
        return ticketPrestitoDAO.
                findAllByBiblioteca_Email(biblioteca.getEmail());
    }

}

