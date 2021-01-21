package it.unisa.c07.biblionet.autenticazione.controller;

import it.unisa.c07.biblionet.BiblionetApplication;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Antonio Della Porta
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblionetApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AreaUtenteControllerIntegrationTest {

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
     *
     * @param lettore
     * @param vecchiaPassword
     * @param nuovaPassword
     * @param confermaPassword
     * @throws Exception
     */
    @ParameterizedTest
    @MethodSource("provideModificaLettore")
    public void modificaDatiRegexTestIntegration(
            final Lettore lettore,
            final String vecchiaPassword,
            final String nuovaPassword,
            final String confermaPassword) throws Exception {

        this.mockMvc.perform(post("/conferma-modifica-lettore")
                .param("email", lettore.getEmail())
                .param("nome", lettore.getNome())
                .param("cognome", lettore.getCognome())
                .param("username", lettore.getUsername())
                .param("vecchia_password", vecchiaPassword)
                .param("nuova_password", nuovaPassword)
                .param("conferma_password", confermaPassword)
                .param("provincia", lettore.getProvincia())
                .param("citta", lettore.getCitta())
                .param("via", lettore.getVia())
                .param("recapitoTelefonico", lettore.getRecapitoTelefonico()))
                .andExpect(view().name("autenticazione/login"));

    }

    @ParameterizedTest
    @MethodSource("provideModificaLettore")
    public void modificaDatiRegexFaultTestIntegration(
            final Lettore lettore,
            final String vecchiaPassword,
            final String nuovaPassword,
            final String confermaPassword) throws Exception {

        this.mockMvc.perform(post("/conferma-modifica-lettore")
                .param("email", lettore.getEmail())
                .param("nome", lettore.getNome()+"$")
                .param("cognome", lettore.getCognome())
                .param("username", lettore.getUsername())
                .param("vecchia_password", vecchiaPassword)
                .param("nuova_password", nuovaPassword)
                .param("conferma_password", confermaPassword)
                .param("provincia", lettore.getProvincia())
                .param("citta", lettore.getCitta())
                .param("via", lettore.getVia())
                .param("recapitoTelefonico", lettore.getRecapitoTelefonico()))
                .andExpect(view().name("area-utente/modifica-dati-lettore"));

    }

    @ParameterizedTest
    @MethodSource("provideModificaLettore")
    public void modificaDatiRegexPasswordFaultTestIntegration(
            final Lettore lettore,
            final String vecchiaPassword) throws Exception {

        this.mockMvc.perform(post("/conferma-modifica-lettore")
                .param("email", lettore.getEmail())
                .param("nome", lettore.getNome())
                .param("cognome", lettore.getCognome())
                .param("username", lettore.getUsername())
                .param("vecchia_password", vecchiaPassword)
                .param("nuova_password", "asd")
                .param("conferma_password", "asd")
                .param("provincia", lettore.getProvincia())
                .param("citta", lettore.getCitta())
                .param("via", lettore.getVia())
                .param("recapitoTelefonico", lettore.getRecapitoTelefonico()))
                .andExpect(view().name("area-utente/modifica-dati-lettore"));

    }



    /**
     * Simula i dati inviati da un metodo
     * http attraverso uno stream.
     *
     * @return Lo stream di dati.
     */
    private static Stream<Arguments> provideModificaLettore() {

        return Stream.of(
                Arguments.of(
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
                        ),
                        "LettorePassword", // vecchia password
                        "NuovaPassword",   // nuova password
                        "NuovaPassword"    // conferma password
                )
        );
    }


}
