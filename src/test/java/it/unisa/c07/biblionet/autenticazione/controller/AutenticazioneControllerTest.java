package it.unisa.c07.biblionet.autenticazione.controller;

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

        when(autenticazioneService.login(email, password)).thenReturn(?);

        this.mockMvc.perform(post("/autenticazione/login")
                .param(email)
                .param(password))
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
