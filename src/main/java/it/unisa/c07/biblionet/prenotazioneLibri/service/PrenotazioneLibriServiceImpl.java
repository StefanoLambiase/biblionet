package it.unisa.c07.biblionet.prenotazioneLibri.service;

import it.unisa.c07.biblionet.clubDelLibro.service.ClubDelLibroService;
import it.unisa.c07.biblionet.clubDelLibro.service.ClubDelLibroServiceImpl;
import it.unisa.c07.biblionet.model.dao.GenereDAO;
import it.unisa.c07.biblionet.model.dao.LibroDAO;
import it.unisa.c07.biblionet.model.dao.PossessoDAO;
import it.unisa.c07.biblionet.model.dao.TicketPrestitoDAO;
import it.unisa.c07.biblionet.model.dao.customQueriesResults.ILibroIdAndName;
import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.Libro;
import it.unisa.c07.biblionet.model.entity.Possesso;
import it.unisa.c07.biblionet.model.entity.TicketPrestito;
import it.unisa.c07.biblionet.model.entity.compositeKey.PossessoId;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.prenotazioneLibri.service.bookApiAdapter.BookApiAdapter;
import it.unisa.c07.biblionet.prenotazioneLibri.service.bookApiAdapter.GoogleBookApiAdapterImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
     *Si occupa delle operazioni CRUD per genere.
     */
    private final GenereDAO genereDAO;

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
     * @param nomeBiblioteca Il nome della biblioteca
     * @return La lista di libri
     */
    @Override
    public List<Libro> visualizzaListaLibriPerBiblioteca(
                    final String nomeBiblioteca) {
        List<Biblioteca> b = bibliotecaDAO.findByNome(nomeBiblioteca);
        List<Libro> libri = new ArrayList<>();
        for (Biblioteca bib : b) {
            String bibID = bib.getEmail();
            List<Possesso> possessi = possessoDAO.findByBibliotecaID(bibID);
            for (Possesso p : possessi) {
                Optional<Libro> l =
                        libroDAO.findById(p.getPossessoID().getLibroID());
                if (!libri.contains(l.orElse(null))) {
                    libri.add(l.orElse(null));
                }
            }
        }

        return libri;
    }

    /**
     * Implementa la funzionalità che permette
     * di visualizzare la lista completa dei libri
     * prenotabili di un dato genere.
     * @param genere Il nome del genere
     * @return La lista di libri
     */
    @Override
    public List<Libro> visualizzaListaLibriPerGenere(
            final String genere) {

        List<Libro> libri = libroDAO.findAll();
        List<Libro> list = new ArrayList<>();
        Genere g = genereDAO.findByName(genere);
        for (Libro l : libri) {
            if (l.getGeneri().contains(g)) {
                list.add(l);
            }
        }
        return list;
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
                findAllByBibliotecaEmail(biblioteca.getEmail());
    }

    /**
     * Implementa la funzionalità che permette
     * di ottenere un ticket dato il suo ID.
     * @param id L'ID del ticket da recuperare
     * @return Il ticket ottenuto
     */
    @Override
    public TicketPrestito getTicketByID(final int id) {
        return ticketPrestitoDAO.getOne(id);
    }

    /**
     * Implementa la funzionalità che permette
     * di accettare la richiesta di prestito di un libro.
     * @param ticket il ticket che rappresenta la richiesta
     * @param giorni il tempo di concessione del libro
     * @return Il ticket aggiornato
     */
    @Override
    public TicketPrestito accettaRichiesta(final TicketPrestito ticket,
                                           final int giorni) {
        ticket.setDataRestituzione(LocalDateTime.now().plusDays(giorni));
        ticket.setStato(TicketPrestito.Stati.IN_ATTESA_DI_RESTITUZIONE);
        Libro l = ticket.getLibro();
        Biblioteca b = ticket.getBiblioteca();
        Possesso pos = possessoDAO.
                    getOne(new PossessoId(b.getEmail(), l.getIdLibro()));
        if (pos != null) {
            pos.setNumeroCopie(pos.getNumeroCopie() - 1);
            possessoDAO.save(pos);
        }
        ticketPrestitoDAO.save(ticket);
        return ticket;
    }

    /**
     * Implementa la funzionalità che permette
     * di rifiutare la richiesta di prestito di un libro.
     * @param ticket il ticket che rappresenta la richiesta
     * @return Il ticket aggiornato
     */
    @Override
    public TicketPrestito rifiutaRichiesta(final TicketPrestito ticket) {
        ticket.setStato(TicketPrestito.Stati.RIFIUTATO);
        ticketPrestitoDAO.save(ticket);
        return ticket;
    }

    /**
     * Implementa la funzionalità che permette
     * di chiudere un ticket di prenotazione di un libro
     * quando questo viene riconsegnato.
     * @param ticket il ticket che rappresenta la richiesta da chiudere
     * @return Il ticket aggiornato a chiuso
     */
    @Override
    public TicketPrestito chiudiTicket(final TicketPrestito ticket) {
        ticket.setStato(TicketPrestito.Stati.CHIUSO);
        Libro l = ticket.getLibro();
        Biblioteca b = ticket.getBiblioteca();
        Possesso pos = possessoDAO.
                getOne(new PossessoId(b.getEmail(), l.getIdLibro()));
        if (pos != null) {
            pos.setNumeroCopie(pos.getNumeroCopie() + 1);
            possessoDAO.save(pos);
        }
        return ticketPrestitoDAO.save(ticket);
    }

    /**
     * Implementa la funzionalità che permette
     * di ottenere la lista di ticket aperti da un Lettore.
     * @param lettore il Lettore di cui recuperare i ticket
     * @return la lista dei ticket
     */
    @Override
    public List<TicketPrestito> getTicketsLettore(final Lettore lettore) {
        return ticketPrestitoDAO.
                findAllByLettoreEmail(lettore.getEmail());
    }


    /**
     * Implementa la funzionalità che permette di
     * ottenere una lista di id e titoli di libri
     * sulla base di un titolo dato
     *
     * ! Controllare prima di consegnare
     *
     * @param titolo il titolo che deve mathcare
     * @return la lista di informazioni
     */
    public List<ILibroIdAndName> findByTitoloContains(String titolo) {
        List<ILibroIdAndName> infoLibroList =
                libroDAO.findByTitoloContains(titolo);

        if (infoLibroList == null) {
            infoLibroList = new ArrayList<>();
        } else if (infoLibroList.size() > 10) {
            infoLibroList = infoLibroList.subList(0, 9);
        }
        return infoLibroList;
    }

    /**
     * Implementa la funzionalità che permette
     * di creare un nuovo libro e inserirlo nella lista
     * a partire da un isbn usando una API di google.
     * @param isbn il Lettore di cui recuperare i ticket
     * @param idBiblioteca l'id della biblioteca che lo possiede
     * @param numCopie il numero di copie possedute
     * @return il libro creato
     */
    public Libro inserimentoPerIsbn(String isbn, String idBiblioteca, int numCopie, List<String> generi) {

        //Recuper l'oggetto Libro da Api per isbn
        BookApiAdapter bookApiAdapter = new GoogleBookApiAdapterImpl();
        Libro l = bookApiAdapter.getLibroDaBookApi(isbn);
        if (l == null) {
            return l;
        }

        //Salvo il libro nel db
        List<Genere> g = new ArrayList<>();
        if (!generi.isEmpty()) {
            for (String s : generi) {
                g.add(genereDAO.findByName(s));
            }
        }
        l.setGeneri(g);

        //Controllo che il libro non sia già salvato
        boolean exists = false;
        Libro libro = null;
        for (Libro tl : libroDAO.findAll()) {
            if(tl.getIsbn().equals(l.getIsbn())) {
                exists = true;
                libro = tl;
            }
        }
        if (!exists) {
            libro = libroDAO.save(l);
        }

        //Creo il possesso relativo al libro e alla biblioteca
        //che lo inserisce e lo memorizzo
        PossessoId pid = new PossessoId(idBiblioteca, libro.getIdLibro());
        Possesso possesso = new Possesso(pid, numCopie);
        possessoDAO.save(possesso);
        Biblioteca b = bibliotecaDAO.findByID(idBiblioteca);
        List<Possesso> plist = b.getPossessi();
        plist.add(possesso);
        b.setPossessi(plist);

        //Update della biblioteca con il nuovo possesso
        bibliotecaDAO.save(b);

        return libro;
    }

}

