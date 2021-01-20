package it.unisa.c07.biblionet.comunicazioneEsperto.controller;

import it.unisa.c07.biblionet.BiblionetApplication;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblionetApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ComunicazioneEspertoControllerIntegrationTest {

    @Autowired
    @Setter
    @Getter
    private ApplicationContext applicationContext;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        BiblionetApplication.init(applicationContext);
    }

    @Test
    public void visualizzaListaEsperti() throws Exception {
        this.mockMvc.perform(get("/comunicazione-esperto/lista-esperti"))
                .andExpect(model().attributeExists("listaEsperti"))
                .andExpect(view().name("comunicazione-esperto/lista-completa-esperti"));
    }

}
