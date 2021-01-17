package it.unisa.c07.biblionet.prenotazioneLibri.controller;

import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.Libro;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.prenotazioneLibri.service.PrenotazioneLibriService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Implementa il testing di unità per la classe
 * BibliotecaController.
 * @author Viviana Pentangelo, Gianmario Voria
 */
@SpringBootTest
@AutoConfigureMockMvc
public class BibliotecaControllerTest {

    /**
     * Mock del service per simulare
     * le operazioni dei metodi.
     */
    @MockBean
    private PrenotazioneLibriService prenotazioneService;

    /**
     * Inject di MockMvc per simulare
     * le richieste http.
     */
    @Autowired
    private MockMvc mockMvc;


    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la visualizzazione della pagina
     * di inserimento di un libro
     * simulando la richiesta http.
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void visualizzaInserimentoLibroUserNull () throws Exception {
        this.mockMvc.perform(get("/biblioteca/inserisci-nuovo-libro"));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la visualizzazione della pagina
     * di inserimento di un libro
     * simulando la richiesta http.
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void visualizzaInserimentoLibroUserNotBiblioteca () throws Exception {
        Lettore l = new Lettore();
        this.mockMvc.perform(get("/biblioteca/inserisci-nuovo-libro")
                            .sessionAttr("loggedUser", l));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la visualizzazione della pagina
     * di inserimento di un libro
     * simulando la richiesta http.
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void visualizzaInserimentoLibroValid () throws Exception {
        Biblioteca b = new Biblioteca();
        Genere g = new Genere();
        Libro l = new Libro();
        List<Genere> gl = new ArrayList<>();
        List<Libro> ll = new ArrayList<>();
        gl.add(g);
        ll.add(l);
        when(prenotazioneService.visualizzaListaLibriCompleta()).thenReturn(ll);
        when(prenotazioneService.getAllGeneri()).thenReturn(gl);
        this.mockMvc.perform(get("/biblioteca/inserisci-nuovo-libro")
                .sessionAttr("loggedUser", b))
                .andExpect(model().attribute("listaLibri", ll))
                .andExpect(model().attribute("listaGeneri",gl))
                .andExpect(view().name(
                        "/biblioteca/inserimento-nuovo-libro-prenotabile"));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per l'inserimento di un libro da isbn
     * simulando la richiesta http.
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void inserimentoIsbnUserNull () throws Exception {

        String[] generi = {""};

        this.mockMvc.perform(post("/biblioteca/inserimento-isbn")
                            .param("isbn", "a")
                            .param("generi", generi)
                            .param("numCopie", "1"));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per l'inserimento di un libro da isbn
     * simulando la richiesta http.
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void inserimentoIsbnUserNotValid () throws Exception {

        String[] generi = {""};
        Lettore l = new Lettore();
        this.mockMvc.perform(post("/biblioteca/inserimento-isbn")
                .sessionAttr("loggedUser", l)
                .param("isbn", "a")
                .param("generi", generi)
                .param("numCopie", "1"));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per l'inserimento di un libro da isbn
     * simulando la richiesta http.
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void inserimentoIsbnLibroNull() throws Exception {

        String[] generi = {""};
        Biblioteca b = new Biblioteca();

        List<String> stlist = new ArrayList<>();

        when(prenotazioneService.inserimentoPerIsbn(
                "a", "a", 1, stlist))
            .thenReturn(null);

        this.mockMvc.perform(post("/biblioteca/inserimento-isbn")
                .sessionAttr("loggedUser", b)
                .param("isbn", "a")
                .param("generi", generi)
                .param("numCopie", "1"))
                .andExpect(view().name(
                        "redirect:/biblioteca/inserisci-nuovo-libro"));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per l'inserimento di un libro da isbn
     * simulando la richiesta http.
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void inserimentoIsbn() throws Exception {

        String[] generi = {""};
        Biblioteca b = new Biblioteca();
        b.setEmail("a");

        List<String> stlist = new ArrayList<>();

        Libro l = new Libro(
                "BiblioNet",
                "Stefano Lambiase",
                "1234567890123",
                LocalDateTime.now(),
                "Biblioteche 2.0",
                "Mondadori"

        );
        l.setIdLibro(3);
        when(prenotazioneService.inserimentoPerIsbn(
                "1234567890123", "a", 1, stlist))
                .thenReturn(l);

        this.mockMvc.perform(post("/biblioteca/inserimento-isbn")
                .sessionAttr("loggedUser", b)
                .param("isbn", "1234567890123")
                .param("generi", generi)
                .param("numCopie", "1"))
                .andExpect(view().name(
                        "redirect:/prenotazione-libri/3/visualizza-libro"));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per l'inserimento di un libro da archivio
     * simulando la richiesta http.
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void inserimentoDatabaseUserNull () throws Exception {

        this.mockMvc.perform(post("/biblioteca/inserimento-archivio")
                .param("idLibro", "1")
                .param("numCopie", "1"));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per l'inserimento di un libro da archivio
     * simulando la richiesta http.
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void inserimentoDatabaseUserNotValid () throws Exception {

        Lettore l = new Lettore();
        this.mockMvc.perform(post("/biblioteca/inserimento-archivio")
                .sessionAttr("loggedUser", l)
                .param("idLibro", "1")
                .param("numCopie", "1"));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per l'inserimento di un libro da archivio
     * simulando la richiesta http.
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void inserimentoDatabase () throws Exception {

        Biblioteca b = new Biblioteca();
        b.setEmail("a");
        Libro l = new Libro(
                "BiblioNet",
                "Stefano Lambiase",
                "1234567890123",
                LocalDateTime.now(),
                "Biblioteche 2.0",
                "Mondadori"

        );
        l.setIdLibro(3);
        when(prenotazioneService.inserimentoDalDatabase(
                3,"a", 1))
                .thenReturn(l);
        this.mockMvc.perform(post("/biblioteca/inserimento-archivio")
                .sessionAttr("loggedUser", b)
                .param("idLibro", "3")
                .param("numCopie", "1"))
                .andExpect(view().name(
                        "redirect:/prenotazione-libri/3/visualizza-libro"));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per l'inserimento di un libro con form manuale
     * simulando la richiesta http.
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void inserimentoManualeUserNull () throws Exception {

        this.mockMvc.perform(post("/biblioteca/inserimento-manuale")
                .param("numCopie", "1"));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per l'inserimento di un libro con form manuale
     * simulando la richiesta http.
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void inserimentoManualeUserNotValid () throws Exception {
        Lettore l = new Lettore();
        this.mockMvc.perform(post("/biblioteca/inserimento-manuale")
                .sessionAttr("loggedUser", l)
                .param("numCopie", "1"));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per l'inserimento di un libro con form manuale
     * simulando la richiesta http.
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void inserimentoManualeIsbnDescrizioneImmagineVuoto () throws Exception {
        Biblioteca b = new Biblioteca();
        b.setEmail("a");
        Libro l = new Libro();
        l.setTitolo("BiblioNet");
        l.setAutore("Stefano Lambiase");
        l.setCasaEditrice("Mondadori");
        l.setAnnoDiPubblicazione(LocalDateTime.of(2010, 1 , 1, 1, 1));
        l.setImmagineLibro(null);
        l.setIdLibro(0);
        List<String> generi = new ArrayList<>();
        generi.add("g1");
        generi.add("g2");
        Genere g1 = new Genere();
        Genere g2 = new Genere();
        List<Genere> glist = new ArrayList<>();
        glist.add(g1);
        glist.add(g2);
        when(prenotazioneService.inserimentoManuale(
                l, "a", 1, generi))
                .thenReturn(l);

        this.mockMvc.perform(post("/biblioteca/inserimento-manuale")
                .sessionAttr("loggedUser", b)
                .param("titolo", "BiblioNet")
                .param("casaEditrice", "Mondadori")
                .param("autore", "Stefano Lambiase")
                .param("annoPubblicazione", "2010")
                .param("generi", "g1")
                .param("generi", "g2")
                .param("numCopie", "1"))
                .andExpect(view().name(
                        "redirect:/prenotazione-libri/0/visualizza-libro"));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per l'inserimento di un libro con form manuale
     * simulando la richiesta http.
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void inserimentoManuale () throws Exception {
        Biblioteca b = new Biblioteca();
        b.setEmail("a");
        Libro l = new Libro();
        l.setTitolo("BiblioNet");
        l.setAutore("Stefano Lambiase");
        l.setCasaEditrice("Mondadori");
        l.setAnnoDiPubblicazione(LocalDateTime.of(2010, 1 , 1, 1, 1));
        l.setImmagineLibro(null);
        l.setIdLibro(0);
        l.setIsbn("1234");
        l.setDescrizione("descrizione");
        List<String> generi = new ArrayList<>();
        generi.add("g1");
        generi.add("g2");
        Genere g1 = new Genere();
        Genere g2 = new Genere();
        List<Genere> glist = new ArrayList<>();
        glist.add(g1);
        glist.add(g2);
        when(prenotazioneService.inserimentoManuale(
                l, "a", 1, generi))
                .thenReturn(l);
        this.mockMvc.perform(post("/biblioteca/inserimento-manuale")
                .sessionAttr("loggedUser", b)
                .param("titolo", "BiblioNet")
                .param("casaEditrice", "Mondadori")
                .param("autore", "Stefano Lambiase")
                .param("annoPubblicazione", "2010")
                .param("generi", "g1")
                .param("generi", "g2")
                .param("isbn", "1234")
                .param("descrizione", "descrizione")
                .param("numCopie", "1"))
                .andExpect(view().name(
                        "redirect:/prenotazione-libri/0/visualizza-libro"));
    }

    /**
     * Implementa il test della funzionalità che permette di
     * visualizzare le biblioteche filtrate.     *
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void visualizzaListaFiltrata() throws Exception {
        List<Biblioteca> list = new ArrayList<>();

        when(prenotazioneService.getBibliotecheByNome("a")).thenReturn(list);
        when(prenotazioneService.getBibliotecheByCitta("a")).thenReturn(list);
        when(prenotazioneService.getAllBiblioteche()).thenReturn(list);

        this.mockMvc.perform(get("/biblioteca/ricerca")
                .param("filtro", "nome")
                .param("stringa", "a"))
                .andExpect(model().attribute("listaBiblioteche", list))
                .andExpect(view()
                        .name("biblioteca/visualizza-lista-biblioteche"));
        this.mockMvc.perform(get("/biblioteca/ricerca")
                .param("filtro", "citta")
                .param("stringa", "a"))
                .andExpect(model().attribute("listaBiblioteche", list))
                .andExpect(view()
                        .name("biblioteca/visualizza-lista-biblioteche"));
        this.mockMvc.perform(get("/biblioteca/ricerca")
                .param("filtro", "default")
                .param("stringa", "a"))
                .andExpect(model().attribute("listaBiblioteche", list))
                .andExpect(view()
                        .name("biblioteca/visualizza-lista-biblioteche"));

    }

    /**
     * Implementa il test della funzionalitá di visualizzazione
     * della lista di tutte le biblioteche.
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void visualizzaListaBiblioteche() throws Exception {
        List<Biblioteca> list = new ArrayList<>();

        when(prenotazioneService.getAllBiblioteche()).thenReturn(list);
        this.mockMvc.perform(get("/biblioteca/visualizza-biblioteche"))
                .andExpect(model().attribute("listaBiblioteche", list))
                .andExpect(view()
                        .name("/biblioteca/visualizza-lista-biblioteche"));
    }

    /**
     * Implementa il test dla funzionalitá di visualizzazione
     * del profilo di una singola biblioteca.
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void visualizzaDatiBiblioteca() throws Exception {
        Biblioteca biblioteca = (Biblioteca) new Biblioteca();
        biblioteca.setEmail("a");
        when(prenotazioneService
                .getBibliotecaById("a")).thenReturn(biblioteca);
        this.mockMvc.perform(get("/biblioteca/a"))
                .andExpect(model().attribute("biblioteca", biblioteca))
                .andExpect(view()
                        .name("biblioteca/visualizza-singola-biblioteca"));
    }

}
