package it.unisa.c07.biblionet.prenotazioneLibri.controller;

import it.unisa.c07.biblionet.BiblionetApplication;
import it.unisa.c07.biblionet.model.dao.GenereDAO;
import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
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
public class BibliotecaControllerTestIntegrationTest {

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

    @BeforeEach
    public void init() {
        BiblionetApplication.init(applicationContext);
    }

    @Test
    public void inserisciNuovoLibro() throws Exception {

        Biblioteca biblioteca = bibliotecaDAO.findByID("bibliotecacarrisi@gmail.com");

        this.mockMvc.perform(get("/biblioteca/inserisci-nuovo-libro")
                .sessionAttr("loggedUser", biblioteca))
                .andExpect(view().name(
                        "/biblioteca/inserimento-nuovo-libro-prenotabile"));
    }

    @Test
    public void inserimentoDatabase() throws Exception {

        Biblioteca biblioteca = bibliotecaDAO.findByID("bibliotecacarrisi@gmail.com");

        this.mockMvc.perform(post("/biblioteca/inserimento-archivio")
                .sessionAttr("loggedUser", biblioteca)
                .param("idLibro", "3")
                .param("numCopie", "1"))
                .andExpect(view().name(
                        "redirect:/prenotazione-libri/3/visualizza-libro"));
    }
}
