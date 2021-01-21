package it.unisa.c07.biblionet.prenotazioneLibri.controller;

import it.unisa.c07.biblionet.BiblionetApplication;
import it.unisa.c07.biblionet.model.dao.GenereDAO;
import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.dao.utente.LettoreDAO;
import it.unisa.c07.biblionet.model.entity.Libro;
import it.unisa.c07.biblionet.model.entity.TicketPrestito;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import it.unisa.c07.biblionet.prenotazioneLibri.service.PrenotazioneLibriService;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Antonio Della Porta
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblionetApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PrenotazioneLibriControllerIntegrationTest {

    @Autowired
    @Setter
    @Getter
    private ApplicationContext applicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PrenotazioneLibriService prenotazioneLibriService;

    @Autowired
    private BibliotecaDAO bibliotecaDAO;

    @Autowired
    private GenereDAO genereDAO;

    @Autowired
    private LettoreDAO lettoreDAO;

    @BeforeEach
    public void init() {
        BiblionetApplication.init(applicationContext);
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la visualizzazione di
     * tutti i libri prenotabili
     * simulando la richiesta http.
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void visualizzaListaLibri() throws Exception {

        this.mockMvc.perform(get("/prenotazione-libri/"))
                .andExpect(view().name(
                        "prenotazione-libri/visualizza-libri-prenotabili"));
    }

    @Test
    public void ricercaFiltrata() throws Exception {
        this.mockMvc.perform(get("/prenotazione-libri/ricerca")
                .param("filtro", "biblioteca")
                .param("stringa", "Harry Potter e la Pietra Filosofale"))
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

        Biblioteca biblioteca = bibliotecaDAO.findByID("bibliotecacarrisi@gmail.com");
        Lettore lettore = lettoreDAO.findByID("antoniorenatomontefusco@gmail.com");
        this.mockMvc.perform(
                post("/prenotazione-libri/conferma-prenotazione")
                        .param("idBiblioteca", biblioteca.getEmail())
                        .param("idLibro", "1")
                        .sessionAttr("loggedUser", lettore))
                .andExpect(view().name("redirect:/prenotazione-libri"));
    }

}
