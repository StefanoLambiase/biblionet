package it.unisa.c07.biblionet.comunicazioneEsperto.controller;

import it.unisa.c07.biblionet.comunicazioneEsperto.service.ComunicazioneEspertoServiceImpl;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
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
 * BibliotecaController.
 * @author Viviana Pentangelo
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ComunicazioneEspertoControllerTest {

    /**
     * Mock del service per simulare
     * le operazioni dei metodi.
     */
    @MockBean
    private ComunicazioneEspertoServiceImpl comunicazioneEspertoService;

    /**
     * Inject di MockMvc per simulare
     * le richieste http.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Implementa il test della funzionalità che permette di
     * visualizzare gli esperti filtrati
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void visualizzaListaFiltrata() throws Exception {
        List<Esperto> list = new ArrayList<>();

        when(comunicazioneEspertoService
                .getEsperiByName("a")).thenReturn(list);
        when(comunicazioneEspertoService
                .getAllEsperti()).thenReturn(list);
        when(comunicazioneEspertoService
                .visualizzaEspertiPerGenere("a")).thenReturn(list);

        this.mockMvc.perform(get("/comunicazione-esperto/ricerca")
                .param("filtro", "nome")
                .param("stringa", "a"))
                .andExpect(model().attribute("listaEsperti", list))
                .andExpect(view()
                        .name("comunicazione-esperto/lista-completa-esperti"));
        this.mockMvc.perform(get("/comunicazione-esperto/ricerca")
                .param("filtro", "genere")
                .param("stringa", "a"))
                .andExpect(model().attribute("listaEsperti", list))
                .andExpect(view()
                        .name("comunicazione-esperto/lista-completa-esperti"));
        this.mockMvc.perform(get("/comunicazione-esperto/ricerca")
                .param("filtro", "default")
                .param("stringa", "a"))
                .andExpect(model().attribute("listaEsperti", list))
                .andExpect(view()
                        .name("comunicazione-esperto/lista-completa-esperti"));
    }

    /**
     * Implementa il test della funzionalitá di visualizzazione
     * della lista di tutte le biblioteche.
     * @throws Exception Eccezione per MockMvc
     */
    @Test
    public void visualizzaListaEsperti() throws Exception {
        List<Esperto> list = new ArrayList<>();

        when(comunicazioneEspertoService
                .getAllEsperti()).thenReturn(list);

        this.mockMvc.perform(get("/comunicazione-esperto/lista-esperti"))
                .andExpect(model().attribute("listaEsperti", list))
                .andExpect(view()
                        .name("comunicazione-esperto/lista-completa-esperti"));
    }


}
