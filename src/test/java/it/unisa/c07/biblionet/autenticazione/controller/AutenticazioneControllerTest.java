package it.unisa.c07.biblionet.autenticazione.controller;

import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import it.unisa.c07.biblionet.autenticazione.service.AutenticazioneService;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class AutenticazioneControllerTest {

    @MockBean
    private AutenticazioneService autenticazioneService;

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @MethodSource("provideAutenticazione")
    public void loginBuonFine(final String email, final String password) throws Exception {
        System.out.println(email + password);
        UtenteRegistrato utente = new UtenteRegistrato();
        when(autenticazioneService.login(email, password)).thenReturn(utente);

        this.mockMvc.perform(post("/autenticazione/login")
                .param("cio")
                .param("jkf"))
                .andExpect(model().attribute("loggedUser", utente))
                .andExpect(view().name("index"));
    }

    @ParameterizedTest
    @MethodSource("provideAutenticazione")
    public void loginErrata(final String email, final String password) throws Exception {

    }


    private static Stream<Arguments> provideAutenticazione() {
        return Stream.of(Arguments.of("mail@mail.com", "Password"));
    }
}
