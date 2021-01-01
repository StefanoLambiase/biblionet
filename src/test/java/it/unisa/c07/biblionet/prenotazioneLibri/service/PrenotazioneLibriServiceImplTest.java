package it.unisa.c07.biblionet.prenotazioneLibri.service;

import it.unisa.c07.biblionet.model.dao.LibroDAO;
import it.unisa.c07.biblionet.model.dao.PossessoDAO;
import it.unisa.c07.biblionet.model.dao.TicketPrestitoDAO;
import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.entity.Libro;
import it.unisa.c07.biblionet.model.entity.Possesso;
import it.unisa.c07.biblionet.model.entity.TicketPrestito;
import it.unisa.c07.biblionet.model.entity.compositeKey.PossessoId;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Implementa il testing di unità per la classe
 * PrenotazioneLibriServiceImpl.
 * @author Viviana Pentangelo, Gianmario Voria
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PrenotazioneLibriServiceImplTest {

    /**
     * Inject del service per simulare
     * le operazioni del service.
     */
    @InjectMocks
    private PrenotazioneLibriServiceImpl prenotazioneService;

    /**
     * Mocking del dao per simulare le
     * CRUD su libro.
     */
    @Mock
    private LibroDAO libroDAO;

    /**
     * Mocking del dao per simulare le
     * CRUD su biblioteca.
     */
    @Mock
    private BibliotecaDAO bibliotecaDAO;

    /**
     * Mocking del dao per simulare le
     * CRUD su possesso.
     */
    @Mock
    private PossessoDAO possessoDAO;

    /**
     * Mocking del dao per simulare le
     * CRUD su ticket.
     */
    @Mock
    private TicketPrestitoDAO ticketPrestitoDAO;

    /**
     * Implementa il test della funzionalità di
     * selezione di tutti i libri prenotabili.
     */
    @Test
    public void visualizzaListaLibriCompleta() {
        List<Libro> list = new ArrayList<>();
        list.add(new Libro());
        when(libroDAO.findAll(Sort.by("titolo"))).thenReturn(list);
        assertEquals(list, prenotazioneService.visualizzaListaLibriCompleta());
    }

    /**
     * Implementa il test della funzionalità di
     * selezione di tutti i libri prenotabili che contengono
     * una determinata stringa nel titolo.
     */
    @Test
    public void visualizzaListaLibriPerTitolo() {
        List<Libro> list = new ArrayList<>();
        list.add(new Libro());
        when(libroDAO.findByTitoloLike("a")).thenReturn(list);
        assertEquals(list,
                prenotazioneService.visualizzaListaLibriPerTitolo("a"));
    }

    /**
     * Implementa il test della funzionalità di
     * selezione di tutti i libri prenotabili di
     * una determinata biblioteca.
     */
    @Test
    public void visualizzaListaLibriPerBiblioteca() {
        when(bibliotecaDAO.findByNome("nome")).thenReturn(new Biblioteca());
        List<Possesso> listPos = new ArrayList<>();
        when(possessoDAO.findByBibliotecaID("a")).thenReturn(listPos);
        List<Libro> libri = new ArrayList<>();
        Optional<Libro> l = Optional.empty();
        for (int i = 0; i < 2; i++) {
            when(libroDAO.findById(1)).thenReturn(l);
        }
        assertEquals(libri,
                prenotazioneService.visualizzaListaLibriPerBiblioteca("nome"));
    }

   /**
     * Implementa il test della funzionalità che
     * permette di richiedere un prestito per un libro
     * da una biblioteca.
     */
    @Test
    public void richiediPrestito() {
        Biblioteca b = new Biblioteca();
        Libro l = new Libro();
        TicketPrestito t = new TicketPrestito();
        when(bibliotecaDAO.findByID("id")).thenReturn(b);
        when(libroDAO.getOne(2)).thenReturn(l);
        when(ticketPrestitoDAO.save(t)).thenReturn(t);
        t.setStato(TicketPrestito.Stati.IN_ATTESA_DI_CONFERMA);
        LocalDateTime ld = LocalDateTime.of(1, 1, 1, 1, 1);
        t.setDataRichiesta(ld);
        TicketPrestito test = prenotazioneService.richiediPrestito(
                new Lettore(), "id", 2);
        test.setDataRichiesta(ld);
        assertEquals(t.toString(), test.toString());
    }

    /**
     * Implementa il test della funzionalità che permette
     * di ottenere la lista delle biblioteche
     * che posseggono un dato libro.
     */
    @Test
    public void getBibliotecheLibro() {
        Libro l = new Libro();
        List<Possesso> pl = new ArrayList<>();
        l.setPossessi(pl);
        List<Biblioteca> bl = new ArrayList<>();
        Biblioteca b = new Biblioteca();
        for (Possesso ignored : l.getPossessi()) {
            when(bibliotecaDAO.findByID("a"))
                    .thenReturn(b);
            bl.add(b);
        }
        assertEquals(bl, prenotazioneService.getBibliotecheLibro(l));
    }

    /**
     * Implementa il test della funzionalità che permette
     * di ottenere un libro dato il suo ID.
     */
    @Test
    public void getLibroByID() {
        Libro l = new Libro();
        when(libroDAO.getOne(1)).thenReturn(l);
        assertEquals(l, prenotazioneService.getLibroByID(1));
    }

    /**
     * Implementa il test della funzionalità che permette
     * di ottenere una lista di richieste per una biblioteca.
     */
    @Test
    public void getTicketsByBiblioteca() {
        List<TicketPrestito> list = new ArrayList<>();
        when(ticketPrestitoDAO.findAllByBibliotecaEmail("email"))
                .thenReturn(list);
        assertEquals(list, prenotazioneService
                .getTicketsByBiblioteca(new Biblioteca()));
    }

    /**
     * Implementa il test della funzionalità che permette
     * di ottenere un ticket dato il suo ID.
     */
    @Test
    public void getTicketByID() {
        TicketPrestito ticket = new TicketPrestito();
        when(ticketPrestitoDAO.getOne(1)).thenReturn(ticket);
        assertEquals(ticket, prenotazioneService.getTicketByID(1));
    }

    /**
     * Implementa il test della funzionalità che permette
     * di accettare la richiesta di prestito di un libro.
     */
    @Test
    public void accettaRichiesta() {
        TicketPrestito ticket = new TicketPrestito();
        ticket.setBiblioteca(new Biblioteca());
        ticket.setLibro(new Libro());
        Possesso pos = new Possesso();
        when(ticketPrestitoDAO.save(ticket)).thenReturn(ticket);
        when(possessoDAO.getOne(new PossessoId("a", 1)))
                                        .thenReturn(pos);
        assertEquals(ticket, prenotazioneService.accettaRichiesta(ticket, 1));
    }

    /**
     * Implementa il test della funzionalità che permette
     * di rifiutare la richiesta di prestito di un libro.
     */
    @Test
    public void rifiutaRichiesta() {
        TicketPrestito ticket = new TicketPrestito();
        when(ticketPrestitoDAO.save(ticket)).thenReturn(ticket);
        assertEquals(ticket, prenotazioneService.rifiutaRichiesta(ticket));
    }

    /**
     * Implementa il test della funzionalità che permette
     * di chiudere un ticket quando il libro viene
     * restituito.
     */
    @Test
    public void chiudiTicket() {
        TicketPrestito ticket = new TicketPrestito();
        ticket.setBiblioteca(new Biblioteca());
        ticket.setLibro(new Libro());
        Possesso pos = new Possesso();
        when(ticketPrestitoDAO.save(ticket)).thenReturn(ticket);
        when(possessoDAO.getOne(new PossessoId("a", 1)))
                .thenReturn(pos);
        assertEquals(ticket, prenotazioneService.chiudiTicket(ticket));
    }

}
