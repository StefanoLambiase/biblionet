package it.unisa.c07.biblionet.prenotazioneLibri.service;

import it.unisa.c07.biblionet.model.dao.GenereDAO;
import it.unisa.c07.biblionet.model.dao.LibroDAO;
import it.unisa.c07.biblionet.model.dao.PossessoDAO;
import it.unisa.c07.biblionet.model.dao.TicketPrestitoDAO;
import it.unisa.c07.biblionet.model.dao.customQueriesResults.ILibroIdAndName;
import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.entity.*;
import it.unisa.c07.biblionet.model.entity.compositeKey.PossessoId;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.prenotazioneLibri.service.bookApiAdapter.BookApiAdapter;
import it.unisa.c07.biblionet.prenotazioneLibri.service.bookApiAdapter.GoogleBookApiAdapterImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

/**
 * Implementa il testing di unità per la classe
 * PrenotazioneLibriServiceImpl.
 *
 * @author Viviana Pentangelo
 * @author Gianmario Voria
 * @author Antonio Della Porta
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
     * Inject dell'api di google.
     */
    @Mock
    private BookApiAdapter bookApiAdapter;

    /**
     * Mocking del dao per simulare le
     * CRUD su libro.
     */
    @Mock
    private LibroDAO libroDAO;

    /**
     * Mocking del dao per simulare le
     * CRUD su genere.
     */
    @Mock
    private GenereDAO genereDAO;

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
    public void visualizzaListaLibriPerBibliotecaFor2For2() {
        Biblioteca b1 = new Biblioteca();
        b1.setEmail("b1");
        Biblioteca b2 = new Biblioteca();
        b2.setEmail("b2");
        List<Biblioteca> blist = new ArrayList<>();
        blist.add(b1);
        blist.add(b2);
        when(bibliotecaDAO.findByNome("nome")).thenReturn(
                blist);
        List<Possesso> listPos = new ArrayList<>();
        Possesso p1 = new Possesso(
                new PossessoId("b1", 1), 1);
        Possesso p2 = new Possesso(
                new PossessoId("b2", 2), 2);
        listPos.add(p1);
        listPos.add(p2);
        when(possessoDAO.findByBibliotecaID("b1")).thenReturn(listPos);
        List<Libro> libri = new ArrayList<>();
        Libro libro1 = new Libro();
        libro1.setIdLibro(1);
        libri.add(libro1);
        Optional<Libro> l1 = Optional.of(libro1);
        Optional<Libro> l2 = Optional.of(libro1);
        when(libroDAO.findById(1)).thenReturn(l1);
        when(libroDAO.findById(2)).thenReturn(l2);

        assertEquals(libri,
                prenotazioneService.visualizzaListaLibriPerBiblioteca("nome"));
    }

    /**
     * Implementa il test della funzionalità di
     * selezione di tutti i libri prenotabili di
     * una determinata biblioteca.
     */
    @Test
    public void visualizzaListaLibriPerBibliotecaFor2For0() {
        Biblioteca b1 = new Biblioteca();
        b1.setEmail("b1");
        Biblioteca b2 = new Biblioteca();
        b2.setEmail("b2");
        List<Biblioteca> blist = new ArrayList<>();
        blist.add(b1);
        blist.add(b2);
        when(bibliotecaDAO.findByNome("nome")).thenReturn(
                blist);
        List<Libro> libri = new ArrayList<>();
        assertEquals(libri,
                prenotazioneService.visualizzaListaLibriPerBiblioteca("nome"));
    }

    /**
     * Implementa il test della funzionalità di
     * selezione di tutti i libri prenotabili di
     * una determinata biblioteca.
     */
    @Test
    public void visualizzaListaLibriPerBibliotecaFor0() {
        List<Biblioteca> blist = new ArrayList<>();
        List<Libro> libri = new ArrayList<>();
        when(bibliotecaDAO.findByNome("nome")).thenReturn(
                blist);
        assertEquals(libri,
                prenotazioneService.visualizzaListaLibriPerBiblioteca("nome"));
    }

    /**
     * Implementa il test della funzionalità di
     * selezione di tutti i libri prenotabili di
     * una determinato genere.
     */
    @Test
    public void visualizzaListaLibriPerGenereFor2() {
        List<Libro> libri = new ArrayList<>();
        Libro l1 = new Libro();
        l1.setIdLibro(1);
        Libro l2 = new Libro();
        l2.setIdLibro(2);
        Genere g1 = new Genere();
        g1.setNome("genere");
        List<Genere> generiEmpty = new ArrayList<>();
        List<Genere> generi = new ArrayList<>();
        generi.add(g1);
        l1.setGeneri(generi);
        l2.setGeneri(generiEmpty);
        libri.add(l1);
        libri.add(l2);
        when(libroDAO.findAll()).thenReturn(libri);
        when(genereDAO.findByName("genere")).thenReturn(g1);
        List<Libro> list = new ArrayList<>();
        list.add(l1);
        assertEquals(list,
                prenotazioneService.visualizzaListaLibriPerGenere("genere"));
    }

    /**
     * Implementa il test della funzionalità di
     * selezione di tutti i libri prenotabili di
     * una determinato genere.
     */
    @Test
    public void visualizzaListaLibriPerGenereFor0() {
        when(libroDAO.findAll()).thenReturn(new ArrayList<>());
        when(genereDAO.findByName("a")).thenReturn(new Genere());
        assertEquals(new ArrayList<Libro>(),
                prenotazioneService.visualizzaListaLibriPerGenere("a"));
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
    public void getBibliotecheLibroFor2() {
        Libro l = new Libro();
        List<Possesso> pl = new ArrayList<>();
        Possesso p1 = new Possesso(new PossessoId("a", 1), 1);
        Possesso p2 = new Possesso(new PossessoId("b", 1), 1);
        pl.add(p1);
        pl.add(p2);
        l.setPossessi(pl);
        Biblioteca b1 = new Biblioteca();
        b1.setEmail("a");
        Biblioteca b2 = new Biblioteca();
        b1.setEmail("b");
        List<Biblioteca> bl = new ArrayList<>();
        bl.add(b1);
        bl.add(b2);
        when(bibliotecaDAO.
                findByID("a")).thenReturn(b1);
        when(bibliotecaDAO.
                findByID("b")).thenReturn(b2);
        assertEquals(bl, prenotazioneService.getBibliotecheLibro(l));
    }

    /**
     * Implementa il test della funzionalità che permette
     * di ottenere la lista delle biblioteche
     * che posseggono un dato libro.
     */
    @Test
    public void getBibliotecheLibroFor0() {
        Libro l = new Libro();
        List<Possesso> pl = new ArrayList<>();
        l.setPossessi(pl);
        List<Biblioteca> bl = new ArrayList<>();
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
        Biblioteca b = new Biblioteca();
        b.setEmail("a");
        Libro l = new Libro();
        l.setIdLibro(1);
        ticket.setBiblioteca(b);
        ticket.setLibro(l);
        Possesso pos = new Possesso(
                new PossessoId(b.getEmail(), l.getIdLibro()), 1);
        when(possessoDAO.getOne(new PossessoId(b.getEmail(), l.getIdLibro())))
                .thenReturn(pos);
        when(ticketPrestitoDAO.save(ticket)).thenReturn(ticket);
        assertEquals(ticket, prenotazioneService.accettaRichiesta(ticket, 1));
    }

    /**
     * Implementa il test della funzionalità che permette
     * di accettare la richiesta di prestito di un libro.
     */
    @Test
    public void accettaRichiestaNull() {
        TicketPrestito ticket = new TicketPrestito();
        ticket.setBiblioteca(new Biblioteca());
        ticket.setLibro(new Libro());
        when(ticketPrestitoDAO.save(ticket)).thenReturn(ticket);
        when(possessoDAO.getOne(new PossessoId("a", 1)))
                .thenReturn(null);
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
        Biblioteca b = new Biblioteca();
        b.setEmail("a");
        Libro l = new Libro();
        l.setIdLibro(1);
        ticket.setBiblioteca(b);
        ticket.setLibro(l);
        Possesso pos = new Possesso(
                new PossessoId(b.getEmail(), l.getIdLibro()), 1);
        when(possessoDAO.getOne(new PossessoId(b.getEmail(), l.getIdLibro())))
                .thenReturn(pos);
        when(ticketPrestitoDAO.save(ticket)).thenReturn(ticket);
        assertEquals(ticket, prenotazioneService.chiudiTicket(ticket));
    }

    /**
     * Implementa il test della funzionalità che permette
     * di chiudere un ticket quando il libro viene
     * restituito.
     */
    @Test
    public void chiudiTicketNull() {
        TicketPrestito ticket = new TicketPrestito();
        ticket.setBiblioteca(new Biblioteca());
        ticket.setLibro(new Libro());
        when(ticketPrestitoDAO.save(ticket)).thenReturn(ticket);
        when(possessoDAO.getOne(new PossessoId("a", 1)))
                .thenReturn(null);
        assertEquals(ticket, prenotazioneService.chiudiTicket(ticket));
    }

    /**
     * Implementa il test della funzionalità che permette
     * di ottenere la lista di ticket aperti da un Lettore.
     */
    @Test
    public void getTicketsLettore() {
        List<TicketPrestito> list = new ArrayList<>();
        when(ticketPrestitoDAO.findAllByLettoreEmail("a")).thenReturn(list);
        assertEquals(list,
                prenotazioneService.getTicketsLettore(new Lettore()));
    }

    /**
     * Implementa il test della funzionalità che permette
     * di creare un nuovo libro e inserirlo nella lista
     * a partire da un isbn usando una API di google
     * quando l'isbn inserito non viene trovato.
     */
    @Test
    public void inserimentoPerIsbnApiNull() {
        when(bookApiAdapter.getLibroDaBookApi("1234")).thenReturn(null);
        assertEquals(null,
                prenotazioneService.inserimentoPerIsbn("a", "a", 1, null));
    }

    /**
     * Implementa il test della funzionalità che permette
     * di creare un nuovo libro e inserirlo nella lista
     * a partire da un isbn usando una API di google.
     */
    @ParameterizedTest
    @MethodSource("provideLibro")
    public void inserimentoPerIsbnGeneriVuotoLibroTrovatoPosseduto(final Libro libro) {

        when(bookApiAdapter.getLibroDaBookApi(libro.getIsbn())).thenReturn(libro);
        libro.setGeneri(new ArrayList<>());

        List<Libro> libroList = new ArrayList<>();
        libroList.add(libro);
        when(libroDAO.findAll()).thenReturn(libroList);
        when(libroDAO.save(libro)).thenReturn(libro);

        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setEmail("test@test.com");

        PossessoId pid = new PossessoId(biblioteca.getEmail(), libro.getIdLibro());
        Possesso possesso = new Possesso(pid, 1);

        List<Possesso> possessoList = new ArrayList<>();
        possessoList.add(possesso);
        biblioteca.setPossessi(possessoList);

        when(bibliotecaDAO.findByID(biblioteca.getEmail())).thenReturn(biblioteca);

        assertEquals(libro,
                prenotazioneService.inserimentoPerIsbn(
                        libro.getIsbn(), biblioteca.getEmail(), 1, new ArrayList<>()));
    }

    /**
     * Implementa il test della funzionalità che permette
     * di creare un nuovo libro e inserirlo nella lista
     * a partire da un isbn usando una API di google.
     */
    @ParameterizedTest
    @MethodSource("provideLibro")
    public void inserimentoPerIsbnGeneriInseriti(final Libro libro) {

        Genere genere = new Genere("test","test");

        when(bookApiAdapter.getLibroDaBookApi(libro.getIsbn())).thenReturn(libro);
        when(genereDAO.findByName(genere.getNome())).thenReturn(genere);
        when(libroDAO.findAll()).thenReturn(new ArrayList<>());
        when(libroDAO.save(libro)).thenReturn(libro);

        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setEmail("test@test.com");
        biblioteca.setPossessi(new ArrayList<>());
        when(bibliotecaDAO.findByID(biblioteca.getEmail())).thenReturn(biblioteca);

        assertEquals(libro,
                prenotazioneService.inserimentoPerIsbn(
                        libro.getIsbn(), biblioteca.getEmail(), 1, Arrays.asList("test")));
    }

    /**
     * Implementa il test della funzionalità che permette di
     * creare un nuovo libro prendendolo dal database
     * @param libro il libro preso
     */
    @ParameterizedTest
    @MethodSource("provideLibro")
    public void inserimentoDatabase(Libro libro){
        libro.setIdLibro(1);
        Biblioteca biblioteca= new Biblioteca();
        biblioteca.setEmail("test");

        PossessoId pid= new PossessoId("test",1);
        Possesso p= new Possesso();
        p.setPossessoID(pid);
        p.setNumeroCopie(1);
        biblioteca.setPossessi(Arrays.asList(p));

        when(libroDAO.getOne(1)).thenReturn(libro);
        when(bibliotecaDAO.findByID("test")).thenReturn(biblioteca);
        assertEquals(libro, prenotazioneService.inserimentoDalDatabase(1,"test",1));

    }

    /**
     * Implementa il test della funzionalità che permette di
     * creare un nuovo libro prendendolo dal database
     * @param libro il libro preso
     */
    @ParameterizedTest
    @MethodSource("provideLibro")
    public void inserimentoDatabaseLibriDiversi(Libro libro){
        libro.setIdLibro(1);
        Biblioteca biblioteca= new Biblioteca();
        biblioteca.setEmail("test");

        PossessoId pid= new PossessoId("test",2);
        Possesso p= new Possesso();
        p.setPossessoID(pid);
        p.setNumeroCopie(1);
        ArrayList<Possesso> possessi= new ArrayList<>();
        possessi.add(p);
        biblioteca.setPossessi(possessi);

        when(libroDAO.getOne(1)).thenReturn(libro);
        when(bibliotecaDAO.findByID("test")).thenReturn(biblioteca);
        assertEquals(libro, prenotazioneService.inserimentoDalDatabase(1,"test",1));

    }

    /**
     * Implementa il test della funzionalità che permette di
     * creare un nuovo libro prendendolo dal database
     * @param libro il libro preso
     */
    @ParameterizedTest
    @MethodSource("provideLibro")
    public void inserimentoDatabaseNoPossessi(Libro libro){
        libro.setIdLibro(1);
        Biblioteca biblioteca= new Biblioteca();
        biblioteca.setEmail("test");
        biblioteca.setPossessi(new ArrayList<>());

        when(libroDAO.getOne(1)).thenReturn(libro);
        when(bibliotecaDAO.findByID("test")).thenReturn(biblioteca);
        assertEquals(libro, prenotazioneService.inserimentoDalDatabase(1,"test",1));

    }

    /**
     * Implementa il test della funzionalità che permette di
     * creare un nuovo libro manualmente
     * @param libro il libro preso
     */
    @ParameterizedTest
    @MethodSource("provideLibro")
    public void inserimentoManuale(Libro libro){

        Biblioteca biblioteca= new Biblioteca();
        biblioteca.setEmail("test");
        biblioteca.setPossessi(new ArrayList<>());

        List<String> generi=new ArrayList<String>();
        generi.add("test");

        ArrayList<Libro> libri = new ArrayList<>();
        libri.add(libro);



        when(bibliotecaDAO.findByID("test")).thenReturn(biblioteca);
        when(genereDAO.findByName("test")).thenReturn(new Genere());
        when(libroDAO.findAll()).thenReturn(libri);

        assertEquals(libro,prenotazioneService.inserimentoManuale(libro,"test",1,generi));
    }

    /**
     * Implementa il test della funzionalità che permette di
     * creare un nuovo libro manualmente
     * @param libro il libro preso
     */
    @ParameterizedTest
    @MethodSource("provideLibro")
    public void inserimentoManualeGiaEsistente(Libro libro){

        libro.setIdLibro(1);
        Biblioteca biblioteca= new Biblioteca();
        biblioteca.setEmail("test");


        List<String> generi=new ArrayList<String>();
        generi.add("test");

        ArrayList<Libro> libri = new ArrayList<>();
        Libro tmp = new Libro();
        tmp.setTitolo("TEST");
        tmp.setIdLibro(1);
        libri.add(tmp);

        PossessoId pid = new PossessoId("test", 1);
        Possesso possesso = new Possesso(pid, 1);
        ArrayList<Possesso> possessi = new ArrayList<>();
        possessi.add(possesso);
        biblioteca.setPossessi(possessi);


        when(bibliotecaDAO.findByID("test")).thenReturn(biblioteca);
        when(genereDAO.findByName("test")).thenReturn(new Genere());
        when(libroDAO.findAll()).thenReturn(libri);
        when(libroDAO.save(libro)).thenReturn(libro);

        assertEquals(libro,prenotazioneService.inserimentoManuale(libro,"test",1,generi));
    }

    /**
     * Implementa il test della funzionalità che permette di
     * creare un nuovo libro manualmente
     * @param libro il libro preso
     */
    @ParameterizedTest
    @MethodSource("provideLibro")
    public void inserimentoManualePossessoNonEsistente(Libro libro){

        libro.setIdLibro(1);
        Biblioteca biblioteca= new Biblioteca();
        biblioteca.setEmail("test");


        List<String> generi=new ArrayList<String>();
        generi.add("test");

        ArrayList<Libro> libri = new ArrayList<>();
        Libro tmp = new Libro();
        tmp.setTitolo("TEST");
        tmp.setIdLibro(1);
        libri.add(tmp);

        PossessoId pid = new PossessoId("test", 2);
        Possesso possesso = new Possesso(pid, 1);
        ArrayList<Possesso> possessi = new ArrayList<>();
        possessi.add(possesso);
        biblioteca.setPossessi(possessi);


        when(bibliotecaDAO.findByID("test")).thenReturn(biblioteca);
        when(genereDAO.findByName("test")).thenReturn(new Genere());
        when(libroDAO.findAll()).thenReturn(libri);
        when(libroDAO.save(libro)).thenReturn(libro);

        assertEquals(libro,prenotazioneService.inserimentoManuale(libro,"test",1,generi));
    }

    /**
     * Implementa il test della funzionalità che permette di
     * recuperare una biblioteca dato il suo ID.
     */
    @Test
    public void getBibliotecaById() {
        Biblioteca biblioteca = (Biblioteca) new Biblioteca();
        when(bibliotecaDAO.findByID("a")).thenReturn(biblioteca);
        assertEquals(prenotazioneService.getBibliotecaById("a"), biblioteca);
    }

    /**
     * Implementa il test della funzionalità che permette di
     * recuperare la lista di tutte le biblioteche del DB.
     */
    @Test
    public void getAllBiblioteche() {
        List<Biblioteca> list = new ArrayList<>();
        when(bibliotecaDAO.findAllBiblioteche()).thenReturn(list);
        assertEquals(prenotazioneService.getBibliotecheByNome("a"), list);
    }

    /**
     * Implementa il test della funzionalità che permette di
     * recuperare una lista delle biblioteche che contengono
     * la stringa passata nel nome.
     */
    @Test
    public void getBibliotecheByNome() {
        List<Biblioteca> list = new ArrayList<>();
        when(bibliotecaDAO.findByNome("a")).thenReturn(list);
        assertEquals(prenotazioneService.getBibliotecheByNome("a"), list);
    }

    /**
     * Implementa il test della funzionalità che permette di
     * recuperare una lista delle biblioteche che contengono
     * la stringa passata nella città.
     */
    @Test
    public void getBibliotecheByCitta() {
        List<Biblioteca> list = new ArrayList<>();
        when(bibliotecaDAO.findByCitta("a")).thenReturn(list);
        assertEquals(prenotazioneService.getBibliotecheByCitta("a"), list);
    }

    /**
     * Utilizzato per fornire il libro ai test
     * @return il libro
     */
    private static Stream<Arguments> provideLibro() {
        return Stream.of(
                Arguments.of(
                        new Libro(
                                "Amore Amaro",
                                "Fru",
                                "9597845613497",
                                LocalDateTime.of(LocalDate.of(2010, 10, 15), LocalTime.now()),
                                "Biblioteche 2.0",
                                "Mondadori"
                        )
                )
        );
    }

    /**
     * Non so davvero cosa faccia
     */
    @Test
    public void findByTitoloContainsNull() {
        when(prenotazioneService.findByTitoloContains("test")).thenReturn(null);
        assertEquals(new ArrayList<>(),prenotazioneService.findByTitoloContains("test"));
    }
}
