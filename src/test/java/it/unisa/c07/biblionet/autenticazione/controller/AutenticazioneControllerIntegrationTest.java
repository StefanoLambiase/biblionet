package it.unisa.c07.biblionet.autenticazione.controller;

import it.unisa.c07.biblionet.BiblionetApplication;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.stream.Stream;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Antonio Della Porta
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblionetApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AutenticazioneControllerIntegrationTest {

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

    /**
     * Integration Test per la funzionalità che permette
     * di testare la visualizzazione del login.
     * @throws Exception Eccezione che può essere lanciata dal metodo perform.
     */
    @Test
    public void visualizzaLogin() throws Exception {
        this.mockMvc.perform(get("/autenticazione"))
                .andExpect(view().name("autenticazione/login"));
    }

    /**
     * Integration Test per la funzionalità che permette
     * di testare il login andato a buon fine
     * di un utente.
     * @param email La mail dell'utente inserita nel form.
     * @param password La password dell'utente inserita nel form.
     * @throws Exception Eccezione che può essere lanciata dal metodo perform.
     */
    @ParameterizedTest
    @MethodSource("provideAutenticazione")
    public void login(final String email,
                              final String password,
                              final UtenteRegistrato utente) throws Exception {

        this.mockMvc.perform(post("/autenticazione/login")
                .param("email", email)
                .param("password", password))
                .andExpect(model().attribute("loggedUser", utente))
                .andExpect(view().name("index"));
    }

    /**
     * Integration Test per la funzionalità che permette
     * di testare il logout di un utente.
     * @throws Exception Eccezione che può essere
     * lanciata dal metodo perform.
     */
    @Test
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
        return Stream.of(
                Arguments.of(
                        "antoniorenatomontefusco@gmail.com",
                        "LettorePassword",
                        new Lettore(
                                "antoniorenatomontefusco@gmail.com",
                                "LettorePassword",
                                "Napoli",
                                "Somma Vesuviana",
                                "Via Vesuvio 33",
                                "3456789012",
                                "antoniomontefusco",
                                "Antonio",
                                "Montefusco"
                        )

                        ));
    }





}
