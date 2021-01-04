package it.unisa.c07.biblionet.prenotazioneLibri.controller;

import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.Libro;
import it.unisa.c07.biblionet.model.entity.TicketPrestito;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import it.unisa.c07.biblionet.prenotazioneLibri.service.PrenotazioneLibriService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void visualizzaListaLibri() throws Exception {
        List<Libro> list = new ArrayList<>();
        list.add(new Libro());
        when(prenotazioneService.visualizzaListaLibriCompleta())
                        .thenReturn(list);
        this.mockMvc.perform(get("/prenotazione-libri/"))
                .andExpect(model().attribute("listaLibri", list))
                .andExpect(view().name(
                    "prenotazione-libri/visualizza-libri-prenotabili"));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la visualizzazione di
     * tutti i libri prenotabili
     * simulando la richiesta http.
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void visualizzaListaFiltrata() throws Exception {
        List<Libro> list = new ArrayList<>();
        when(prenotazioneService.visualizzaListaLibriPerTitolo("titolo"))
                        .thenReturn(list);
        when(prenotazioneService.visualizzaListaLibriPerGenere("genere"))
                        .thenReturn(list);
        when(prenotazioneService.
                visualizzaListaLibriPerBiblioteca("biblioteca"))
                .thenReturn(list);

        this.mockMvc.perform(get("/prenotazione-libri/ricerca")
                .param("filtro", "titolo")
                .param("stringa", "a"))
                .andExpect(model().attribute("listaLibri", list))
                .andExpect(view().name(
                "prenotazione-libri/visualizza-libri-prenotabili"));

        this.mockMvc.perform(get("/prenotazione-libri/ricerca")
                .param("filtro", "genere")
                .param("stringa", "a"))
                .andExpect(model().attribute("listaLibri", list))
                .andExpect(view().name(
                        "prenotazione-libri/visualizza-libri-prenotabili"));

        this.mockMvc.perform(get("/prenotazione-libri/ricerca")
                .param("filtro", "biblioteca")
                .param("stringa", "a"))
                .andExpect(model().attribute("listaLibri", list))
                .andExpect(view().name(
                        "prenotazione-libri/visualizza-libri-prenotabili"));
    }

    /**
     * Implementa il test della funzionalità che permette di
     * richiedere il prestito di un libro
     * simulando la richiesta http.
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void confermaPrenotazione() throws Exception {
        UtenteRegistrato u = new Lettore();
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
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void prenotaLibro() throws Exception {
        Libro l = new Libro();
        List<Biblioteca> bl = new ArrayList<>();
        when(prenotazioneService.getLibroByID(1)).thenReturn(l);
        when(prenotazioneService.getBibliotecheLibro(l)).thenReturn(bl);

        this.mockMvc.perform(post("/prenotazione-libri/1/visualizza-libro"))
                            .andExpect(model().attribute("lista", bl))
                            .andExpect(model().attribute("libro", l))
                            .andExpect(view().name(
                            "prenotazione-libri/visualizza-prenota-libro"));

    }

    /**
     * Implementa il test della funzionalità che permette di
     * ad una biblioteca di visualizzare le richieste di
     * prenotazione ricevute.
     * @throws Exception Eccezione per MockMvc
     */
    @ParameterizedTest
    @MethodSource("provideTicket")
    public void visualizzaRichieste(TicketPrestito t) throws Exception {
        List<TicketPrestito> list = new ArrayList<>();
        list.add(t);
        when(prenotazioneService.getTicketsByBiblioteca(t.getBiblioteca()))
                    .thenReturn(list);

        this.mockMvc.perform(get("/prenotazione-libri/visualizza-richieste")
                            .sessionAttr("loggedUser", t.getBiblioteca()))
                            .andExpect(model().
                                    attribute("listaTicketDaAccettare", list))
                            .andExpect(model().
                                    attribute("listaTicketAccettati", list))
                            .andExpect(model().
                                    attribute("listaTicketChiusi", list))
                            .andExpect(view().name(
                    "/prenotazione-libri/visualizza-richieste-biblioteca"));

    }

    /**
     * Implementa il test della funzionalità che permette di
     * accettare la richiesta di prestito di un libro.
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void accettaPrenotazione() throws Exception {
        TicketPrestito ticket = new TicketPrestito();
        when(prenotazioneService.getTicketByID(1)).thenReturn(ticket);
        when(prenotazioneService.accettaRichiesta(ticket, 1))
                .thenReturn(ticket);
        this.mockMvc.perform(post("/prenotazione-libri/ticket/1/accetta")
                .param("giorni", "1"))
                .andExpect(view()
                .name("redirect:/prenotazione-libri/visualizza-richieste"));
    }

    /**
     * Implementa il test della funzionalità che permette di
     * rifiutare la richiesta di prestito di un libro.
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void rifiutaPrenotazione() throws Exception {
        TicketPrestito ticket = new TicketPrestito();
        when(prenotazioneService.getTicketByID(1)).thenReturn(ticket);
        when(prenotazioneService.rifiutaRichiesta(ticket)).thenReturn(ticket);
        this.mockMvc.perform(post("/prenotazione-libri/ticket/1/rifiuta"))
                .andExpect(view()
                .name("redirect:/prenotazione-libri/visualizza-richieste"));
    }

    /**
     * Implementa il test della funzionalità che permette
     * di chiudere una richiesta di prestito di un libro
     * quando questo viene restituito.
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void chiudiPrenotazione() throws Exception {
        TicketPrestito ticket = new TicketPrestito();
        when(prenotazioneService.getTicketByID(1)).thenReturn(ticket);
        when(prenotazioneService.chiudiTicket(ticket)).thenReturn(ticket);
        this.mockMvc.perform(post("/prenotazione-libri/ticket/1/chiudi"))
                .andExpect(view()
                .name("redirect:/prenotazione-libri/visualizza-richieste"));
    }

    /**
     * Implementa il test della funzionalità che permette di
     * ottenere la lista di ticket di un lettore.
     * @throws Exception Eccezione per MockMvc
     */
    @ParameterizedTest
    @MethodSource("provideTicket")
    public void  visualizzaPrenotazioniLettore(TicketPrestito t) throws Exception {
        List<TicketPrestito> list = new ArrayList<>();
        UtenteRegistrato u = (Lettore) new Lettore();
        if (true) {
            Lettore lettore = (Lettore) u;
            when(prenotazioneService.getTicketsLettore(lettore))
                    .thenReturn(list);
        }
        this.mockMvc.perform(get("/prenotazione-libri/visualizza-prenotazioni")
                            .sessionAttr("loggedUser", u))
                .andExpect(model().
                        attribute("listaTicket", list))
                .andExpect(model().
                        attribute("listaTicketDaAccettare", list))
                .andExpect(model().
                        attribute("listaTicketAccettati", list))
                .andExpect(model().
                        attribute("listaTicketChiusi", list))
                .andExpect(model().
                        attribute("listaTicketRifiutati", list))
                .andExpect(view().name(
                        "prenotazione-libri/visualizza-richieste-lettore"));
    }


    /**
     * Simula i dati inviati da un metodo
     * http attraverso uno stream.
     * @return Lo stream di dati.
     */
    private static Stream<Arguments> provideTicket() {
        return Stream.of(
                Arguments.of(new TicketPrestito(
                        TicketPrestito.Stati.IN_ATTESA_DI_CONFERMA,
                        LocalDateTime.now(),
                        new Libro(
                                "BiblioNet",
                                "Stefano Lambiase",
                                "1234567890123",
                                LocalDateTime.now(),
                                "Biblioteche 2.0",
                                "Mondadori"

                        ),
                        new Biblioteca(
                                "b4@gmail.com",
                                "aaaaa",
                                "Napoli",
                                "Scampia",
                                "Via Portici 47",
                                "3341278415",
                                "Naboli"
                        ),
                        new Lettore(
                                "giuliociccione@gmail.com",
                                "LettorePassword",
                                "Salerno",
                                "Baronissi",
                                "Via Barone 11",
                                "3456789012",
                                "SuperLettore",
                                "Giulio",
                                "Ciccione"
                        )
                    )
                ),
                Arguments.of(new TicketPrestito(
                                TicketPrestito.Stati.CHIUSO,
                                LocalDateTime.now(),
                                new Libro(
                                        "BiblioNet",
                                        "Stefano Lambiase",
                                        "1234567890123",
                                        LocalDateTime.now(),
                                        "Biblioteche 2.0",
                                        "Mondadori"

                                ),
                                new Biblioteca(
                                        "b4@gmail.com",
                                        "aaaaa",
                                        "Napoli",
                                        "Scampia",
                                        "Via Portici 47",
                                        "3341278415",
                                        "Naboli"
                                ),
                                new Lettore(
                                        "giuliociccione@gmail.com",
                                        "LettorePassword",
                                        "Salerno",
                                        "Baronissi",
                                        "Via Barone 11",
                                        "3456789012",
                                        "SuperLettore",
                                        "Giulio",
                                        "Ciccione"
                                )
                        )
                ),
                Arguments.of(new TicketPrestito(
                                TicketPrestito.Stati.IN_ATTESA_DI_RESTITUZIONE,
                                LocalDateTime.now(),
                                new Libro(
                                        "BiblioNet",
                                        "Stefano Lambiase",
                                        "1234567890123",
                                        LocalDateTime.now(),
                                        "Biblioteche 2.0",
                                        "Mondadori"

                                ),
                                new Biblioteca(
                                        "b4@gmail.com",
                                        "aaaaa",
                                        "Napoli",
                                        "Scampia",
                                        "Via Portici 47",
                                        "3341278415",
                                        "Naboli"
                                ),
                                new Lettore(
                                        "giuliociccione@gmail.com",
                                        "LettorePassword",
                                        "Salerno",
                                        "Baronissi",
                                        "Via Barone 11",
                                        "3456789012",
                                        "SuperLettore",
                                        "Giulio",
                                        "Ciccione"
                                )
                        )
                )
        );
    }
}
