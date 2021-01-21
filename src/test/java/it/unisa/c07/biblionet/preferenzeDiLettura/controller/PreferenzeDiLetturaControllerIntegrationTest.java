package it.unisa.c07.biblionet.preferenzeDiLettura.controller;

import it.unisa.c07.biblionet.BiblionetApplication;
import it.unisa.c07.biblionet.autenticazione.controller.AutenticazioneController;
import it.unisa.c07.biblionet.autenticazione.service.AutenticazioneService;
import it.unisa.c07.biblionet.model.dao.GenereDAO;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.preferenzeDiLettura.service.PreferenzeDiLetturaService;
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

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblionetApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PreferenzeDiLetturaControllerIntegrationTest {

    @Autowired
    @Setter
    @Getter
    private ApplicationContext applicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PreferenzeDiLetturaService preferenzeDiLetturaService;

    @Autowired
    private AutenticazioneService autenticazioneService;

    @Autowired
    private GenereDAO genereDAO;

    @BeforeEach
    public void init() {
        BiblionetApplication.init(applicationContext);
    }

    /**
     * Test di Inserimento nuovi generi ad un esperto con esperto
     * e generi non null.
     * @throws Exception eccezione di mockMvc
     */
    @Test
    public void generiLetterari() throws Exception {
        Esperto esperto = autenticazioneService.findEspertoByEmail("ciromaiorino@gmail.com");
        Genere genere = genereDAO.findByName("Fantasy");
        esperto.setGeneri(Arrays.asList(genere));

        this.mockMvc.perform(post("/preferenze-di-lettura/generi")
                .sessionAttr("loggedUser", esperto))
                .andExpect(view().name("preferenze-lettura/modifica-generi"));
    }

    /**
     * Test di Inserimento nuovi generi ad un esperto con esperto
     * null e generi non null.
     * @throws Exception eccezione di mockMvc
     */
    @Test
    public void generiLetterariNoUser() throws Exception {

        this.mockMvc.perform(post("/preferenze-di-lettura/generi"))
                .andExpect(view().name("index"));
    }


}
