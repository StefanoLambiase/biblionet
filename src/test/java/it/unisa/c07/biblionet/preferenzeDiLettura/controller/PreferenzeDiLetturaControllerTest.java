package it.unisa.c07.biblionet.preferenzeDiLettura.controller;

import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.preferenzeDiLettura.service.PreferenzeDiLetturaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.when;

/**
 * @author Alessio Casolaro, Antonio Della Porta
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PreferenzeDiLetturaControllerTest {

    /**
     * Mock del service per simulare
     * le operazioni dei metodi.
     */
    @MockBean
    private PreferenzeDiLetturaService preferenzeDiLetturaService;

    /**
     * Inject di MockMvc per simulare
     * le richieste http.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Test di Inserimento nuovi generi ad un esperto con esperto
     * e generi non null.
     * @throws Exception eccezione di mockMvc
     */
    @Test
    @DisplayName("Inserimento nuovi generi ad un esperto "
                 + "con esperto e generi non null")
    public void generiLetterari() throws Exception {
        Esperto test = new Esperto();
        test.setGeneri(Arrays.asList(new Genere("TEST", "test")));

        this.mockMvc.perform(post("/preferenze-di-lettura/generi")
                .sessionAttr("loggedUser", test))
                .andExpect(view().name("preferenze-lettura/modifica-generi"));
    }

    /**
     * Test di Inserimento nuovi generi ad un esperto con esperto
     * null e generi non null.
     * @throws Exception eccezione di mockMvc
     */
    @Test
    @DisplayName("Inserimento nuovi generi ad un esperto"
                 + "con esperto null e generi non null")
    public void generiLetterari1() throws Exception {
        Esperto test = null;

        this.mockMvc.perform(post("/preferenze-di-lettura/generi"))
                .andExpect(view().name("index"));
    }

    /**
     * Test di Inserimento nuovi generi ad un esperto con esperto
     * non null e generi null.
     * @throws Exception eccezione di mockMvc
     */
    @Test
    @DisplayName("Inserimento nuovi generi ad un esperto"
                 + "con esperto non null e generi null")
    public void generiLetterari2() throws Exception {
        Esperto test = new Esperto();

        this.mockMvc.perform(post("/preferenze-di-lettura/generi")
                .sessionAttr("loggedUser", test))
                .andExpect(view().name("preferenze-lettura/modifica-generi"));
    }

    /**
     * Test di Inserimento nuovi generi ad un esperto con 0IT.
     * @throws Exception eccezione di mockMvc
     */
    @Test
    @DisplayName("Inserimento nuovi generi ad un esperto con 0IT")
    public void generiLetterari0IT() throws Exception {
        Esperto test = new Esperto();
        test.setGeneri(new ArrayList<>());

        this.mockMvc.perform(post("/preferenze-di-lettura/generi")
                .sessionAttr("loggedUser", test))
                .andExpect(view().name("preferenze-lettura/modifica-generi"));
    }

    /**
     * Test di Inserimento nuovi generi ad un esperto con 1IT.
     * @throws Exception eccezione di mockMvc
     */
    @Test
    @DisplayName("Inserimento nuovi generi ad un esperto con 1IT")
    public void generiLetterari1IT() throws Exception {
        Esperto test = new Esperto();
        test.setGeneri(Arrays.asList(new Genere("TEST", "test")));

        this.mockMvc.perform(post("/preferenze-di-lettura/generi")
                .sessionAttr("loggedUser", test))
                .andExpect(view().name("preferenze-lettura/modifica-generi"));
    }

    /**
     * Test di Inserimento nuovi generi ad un esperto con 2IT.
     * @throws Exception eccezione di mockMvc
     */
    @Test
    @DisplayName("Inserimento nuovi generi ad un esperto con 2IT")
    public void generiLetterari2IT() throws Exception {
        Esperto test = new Esperto();
        test.setGeneri(Arrays.asList(new Genere("TEST", "test"),
                                     new Genere("TEST2", "test2")));

        this.mockMvc.perform(post("/preferenze-di-lettura/generi")
                .sessionAttr("loggedUser", test))
                .andExpect(view().name("preferenze-lettura/modifica-generi"));
    }

    /**
     * Modifica di generi di un esperto con utente non null.
     * @throws Exception eccezione di mockMvc
     */
    @Test
    @DisplayName("Modifica di generi di un esperto")
    public void modificaGeneriEsperto() throws Exception {
        Esperto test = new Esperto();
        String[] gen = {"Fantasy"};

        when(preferenzeDiLetturaService.getGeneriByName(gen))
                .thenReturn(new ArrayList<>());

        this.mockMvc.perform(post("/preferenze-di-lettura/modifica-generi")
                .param("genere", gen)
                .sessionAttr("loggedUser", test))
                .andExpect(view().name("autenticazione/login"));

    }

    /**
     * Modifica di generi di un lettore con utente non null.
     * @throws Exception eccezione di mockMvc
     */
    @Test
    @DisplayName("Modifica di generi di un lettore")
    public void modificaGeneriLettore() throws Exception {
        Esperto test = new Esperto();
        String[] gen = {"Fantasy"};

        when(preferenzeDiLetturaService.getGeneriByName(gen))
                .thenReturn(new ArrayList<>());

        this.mockMvc.perform(post("/preferenze-di-lettura/modifica-generi")
                .param("genere", gen)
                .sessionAttr("loggedUser", test))
                .andExpect(view().name("autenticazione/login"));

    }

    /**
     * Modifica di generi con utente null.
     * @throws Exception eccezione di mockMvc
     */
    @Test
    @DisplayName("Modifica di generi con utente null")
    public void modificaGeneri1() throws Exception {
        String[] gen = {""};

        when(preferenzeDiLetturaService.getGeneriByName(gen))
                .thenReturn(new ArrayList<>());

        this.mockMvc.perform(post("/preferenze-di-lettura/modifica-generi")
                .param("genere", gen))
                .andExpect(view().name("autenticazione/login"));

    }


}
