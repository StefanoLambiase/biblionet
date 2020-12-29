package it.unisa.c07.biblionet.prenotazioneLibri.controller;

import it.unisa.c07.biblionet.model.entity.Libro;
import it.unisa.c07.biblionet.model.entity.TicketPrestito;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import it.unisa.c07.biblionet.prenotazioneLibri.service.PrenotazioneLibriService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Implementa il testing di unità per la classe
 * PrenotazioneLibriController.
 * @author Viviana Pentangelo, Gianmario Voria
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PrenotazioneLibriControllerTest {

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
     * controller per la visualizzazione di
     * tutti i libri prenotabili
     * simulando la richiesta http.
     * @throws Exception Eccezione per MovkMvc
     */
    @Test
    public void visualizzaListaLibri() throws Exception {
        List<Libro> list = new ArrayList<>();
        list.add(new Libro());
        when(prenotazioneService.visualizzaListaLibriCompleta())
                        .thenReturn(list);
        this.mockMvc.perform(get("/prenotazione-libri/"))
                .andExpect(model().attribute("listaLibri", list))
                .andExpect(view().name("visualizza-libri-prenotabili"));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la visualizzazione di
     * tutti i libri prenotabili
     * simulando la richiesta http.
     * @throws Exception Eccezione per MovkMvc
     */
    @Test
    public void visualizzaListaLibriPerTitolo() throws Exception {
        List<Libro> list = new ArrayList<>();
        list.add(new Libro());
        when(prenotazioneService.visualizzaListaLibriPerTitolo("titolo"))
                        .thenReturn(list);
        this.mockMvc.perform(get("/prenotazione-libri/ricerca-titolo")
                .param("titolo", "titolo"))
                .andExpect(model().attribute("listaLibri", list))
                .andExpect(view().name("visualizza-libri-prenotabili"));
    }

    /**
     * Implementa il test della funzionalità che permette di
     * richiedere il prestito di un libro
     * simulando la richiesta http.
     * @throws Exception Eccezione per MovkMvc
     */
    @Test
    public void confermaPrenotazione() throws Exception {
        UtenteRegistrato u = (Lettore) new Lettore();
        TicketPrestito t = new TicketPrestito();
        if (true) {
            Lettore l = (Lettore) u;
            when(prenotazioneService.richiediPrestito(l,
                    "id",
                    1)).thenReturn(t);
        }
        this.mockMvc.perform(
                post("/prenotazione-libri/conferma-prenotazione")
                .param("idBiblioteca", "id")
                .param("idLibro", "1")
                .sessionAttr("loggedUser", u))
                .andExpect(view().name("redirect:/prenotazione-libri"));

    }

    /**
     * Implementa il test della funzionalità che permette di
     * visualizzare le biblioteche presso cui è
     * possibile prentoare il libro
     * simulando la richiesta http.
     * @throws Exception Eccezione per MovkMvc
     */
    @Test
    public void prenotaLibro() throws Exception {
        Libro l = new Libro();
        List<Biblioteca> bl = new ArrayList<>();
        when(prenotazioneService.getLibroByID(1)).thenReturn(l);
        when(prenotazioneService.getBibliotecheLibro(l)).thenReturn(bl);

        this.mockMvc.perform(post("/prenotazione-libri/1/prenota-libro"))
                            .andExpect(model().attribute("lista", bl))
                            .andExpect(model().attribute("libro", l))
                            .andExpect(view().name("prenota-libro"));

    }

    /**
     * Implementa il test della funzionalità che permette di
     * ad una biblioteca di visualizzare le richieste di
     * prenotazione ricevute.
     * @throws Exception Eccezione per MovkMvc
     */
    @Test
    public void visualizzaRichieste() throws Exception {
        UtenteRegistrato u = (Biblioteca) new Biblioteca();
        List<TicketPrestito> list = new ArrayList<>();

        if (true) {
            Biblioteca b = (Biblioteca) u;
            when(prenotazioneService.getTicketsByBiblioteca(b))
                    .thenReturn(list);
        }
        this.mockMvc.perform(get("/prenotazione-libri/visualizza-richieste")
                            .sessionAttr("loggedUser", u))
                            .andExpect(model().
                                    attribute("listaTicketDaAccettare", list))
                            .andExpect(model().
                                    attribute("listaTicketAccettati", list))
                            .andExpect(model().
                                    attribute("listaTicketChiusi", list))
                            .andExpect(view().name("visualizza-richieste"));

    }
}
