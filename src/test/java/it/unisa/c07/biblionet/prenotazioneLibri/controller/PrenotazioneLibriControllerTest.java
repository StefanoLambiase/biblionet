package it.unisa.c07.biblionet.prenotazioneLibri.controller;

import it.unisa.c07.biblionet.model.entity.Libro;
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
}
