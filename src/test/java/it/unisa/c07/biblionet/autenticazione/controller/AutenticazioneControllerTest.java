package it.unisa.c07.biblionet.autenticazione.controller;

import it.unisa.c07.biblionet.autenticazione.service.AutenticazioneService;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import org.junit.jupiter.api.Test;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Implenta il testing per la classe
 * AutenticazioneController.
 * @author Ciro Maiorino,Giulio Triggiani.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AutenticazioneControllerTest {

    /**
     * Mock del service per simulare
     * le operazioni dei metodi.
     */
    @MockBean
    private AutenticazioneService autenticazioneService;

    /**
     * Inject di MockMvc per simulare
     * le richieste http.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Implementa la funzionalità che permette
     * di testare la visualizzazione del login.
     * @throws Exception Eccezione che può essere lanciata dal metodo perform.
     */
    @Test
    public void visualizzaLogin() throws Exception {
        this.mockMvc.perform(get("/autenticazione"))
                .andExpect(view().name("autenticazione/login"));
    }

    /**
     * Implementa la funzionalità che permette
     * di testare il login andato a buon fine
     * di un utente.
     * @param email La mail dell'utente inserita nel form.
     * @param password La password dell'utente inserita nel form.
     * @throws Exception Eccezione che può essere lanciata dal metodo perform.
     */

    @ParameterizedTest
    @MethodSource("provideAutenticazione")
    public void loginBuonFine(final String email,
                              final String password) throws Exception {
        UtenteRegistrato utente = new UtenteRegistrato(email, password,
                                            "Napoli", "Torre del Greco",
                                                "Via Roma", "1234567890");

        when(autenticazioneService.login(email, password)).thenReturn(utente);

        this.mockMvc.perform(post("/autenticazione/login")
                .param("email", email)
                .param("password", password))
                .andExpect(model().attribute("loggedUser", utente))
                .andExpect(view().name("index"));
    }

    /**
     * Implementa la funzionalità che permette
     * di testare il login con dati errati
     * di un utente.
     * @param email La mail dell'utente inserita nel form.
     * @param password La password dell'utente inserita nel form.
     * @throws Exception Eccezione che può essere lanciata dal metodo perform.
     */
    @ParameterizedTest
    @MethodSource("provideAutenticazione")
    public void loginErrata(final String email,
                            final String password) throws Exception {

        when(autenticazioneService.login(email, password)).thenReturn(null);

        this.mockMvc.perform(post("/autenticazione/login")
                .param("email", email)
                .param("password", password))
                .andExpect(view().name("autenticazione/login"));
    }

    /**
     * Implementa la funzionalità che permette
     * di testare il logout di un utente.
     * @throws Exception Eccezione che può essere
     * lanciata dal metodo perform.
     */
    @ParameterizedTest
    @MethodSource("provideAutenticazione")
    public void logout() throws Exception {
        this.mockMvc.perform(get("/autenticazione/logout"))
                .andExpect(view().name("index"));
    }


    /**
     * Simula i dati inviati da un metodo
     * http attraverso uno stream.
     * @return Lo stream di dati.
     */
    private static Stream<Arguments> provideAutenticazione() {
        return Stream.of(Arguments.of("mail@mail.com", "Password"));
    }

}
