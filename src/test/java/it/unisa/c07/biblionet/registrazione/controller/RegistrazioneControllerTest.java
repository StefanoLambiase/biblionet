package it.unisa.c07.biblionet.registrazione.controller;

import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.registrazione.service.RegistrazioneService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.stream.Stream;

import static org.mockito.Mockito.when;

/**
 * @author Alessio Casolaro, Antonio Della Porta
 */
@SpringBootTest
@AutoConfigureMockMvc
public final class RegistrazioneControllerTest {

    /**
     * Mock del service per simulare
     * le operazioni dei metodi.
     */
    @MockBean
    private RegistrazioneService registrazioneService;

    /**
     * Inject di MockMvc per simulare
     * le richieste http.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     *
     *
     * @param esperto          Il lettore da registrare
     * @param confermaPassword il campo conferma password del
     *                         form per controllare
     * @throws Exception Eccezione per MovkMvc
     */
    /**
     * Metodo che testa la funzionalità gestita dal
     * controller per la registrazione di un esperto
     * avvenuta correttamente
     * simulando la richiesta http.
     * @param esperto L'esperto da registrare
     * @param confermaPassword la password da confermare
     * @param emailBiblioteca la mail della biblioteca
     * @throws Exception Eccezione per MockMvc
     */
    @ParameterizedTest
    @DisplayName("Registrazione Esperto che va a buon fine")
    @MethodSource("provideRegistrazioneEsperto")
    public void registrazioneEspertoBuonFine(
            final Esperto esperto, final String confermaPassword,
            final String emailBiblioteca
            ) throws Exception {

        Biblioteca biblioteca = new Biblioteca(
                "bibliotecacarrisi@gmail.com",
                "BibliotecaPassword",
                "Napoli",
                "Torre del Greco",
                "Via Carrisi 47",
                "1234567890",
                "Biblioteca Carrisi"
        );

        when(registrazioneService.registraEsperto(new Esperto())).
                thenReturn(esperto);
        when(registrazioneService.findBibliotecaByEmail(emailBiblioteca)).
                thenReturn(biblioteca);

        this.mockMvc.perform(post("/registrazione/esperto")
                .param("email", esperto.getEmail())
                .param("nome", esperto.getNome())
                .param("cognome", esperto.getCognome())
                .param("username", esperto.getUsername())
                .param("password", "EspertoPassword")
                .param("conferma_password", confermaPassword)
                .param("provincia", esperto.getProvincia())
                .param("citta", esperto.getCitta())
                .param("via", esperto.getVia())
                .param("recapito_telefonico", esperto.getRecapitoTelefonico())
                .param("email_biblioteca", emailBiblioteca)
                )
                .andExpect(view().name("registrazione"));
    }

    /**
     * Simula i dati inviati da un metodo
     * http attraverso uno stream.
     *
     * @return Lo stream di dati.
     */
    private static Stream<Arguments> provideRegistrazioneEsperto() {

        return Stream.of(
                Arguments.of(
                        new Esperto(
                                "eliaviviani@gmail.com",
                                "EspertoPassword",
                                "Napoli",
                                "Torre del Greco",
                                "Via Roma 2",
                                "2345678901",
                                "Espertissimo",
                                "Elia",
                                "Viviani",
                                null
                        ), "EspertoPassword", "bibliotecacarrisi@gmail.com"
                )
        );

    }

    /**
     * Test che registra correttamente una biblioteca.
     * @param biblioteca la biblioteca da registrare
     * @param confermaPassword la passoword da confermare
     * @throws Exception Eccezzione di MockMvc
     */
    @ParameterizedTest
    @DisplayName("Registrazione Biblioteca che va a buon fine")
    @MethodSource("provideRegistrazioneBiblioteca")
    public void registrazioneBibliotecaBuonFine(final Biblioteca biblioteca,
                                                final String confermaPassword)
            throws Exception {
        when(registrazioneService.registraBiblioteca(new Biblioteca()))
                .thenReturn(biblioteca);

        this.mockMvc.perform(post("/registrazione/biblioteca")
                .param("email", biblioteca.getEmail())
                .param("nomeBiblioteca", biblioteca.getNomeBiblioteca())
                .param("password", "BibliotecaPassword")
                .param("conferma_password", confermaPassword)
                .param("provincia", biblioteca.getProvincia())
                .param("citta", biblioteca.getCitta())
                .param("via", biblioteca.getVia())
                .param("recapito_telefonico",
                        biblioteca.getRecapitoTelefonico()))
                .andExpect(view().name("registrazione"));
    }
    /**
     * controller per la registrazione di un lettore
     * avvenuta correttamente
     * simulando la richiesta http.
     * @param lettore Il lettore da registrare
     * @param confermaPassword il campo conferma password del
     * form per controllare
     * @throws Exception Eccezione per MovkMvc
     */
    @ParameterizedTest
    @DisplayName("Registrazione che va a buon fine")
    @MethodSource("provideRegistrazioneLettore")
    public void registrazioneLettoreBuonFine(final Lettore lettore,
                                             final String confermaPassword)
            throws Exception {

        when(registrazioneService.registraLettore(new Lettore()))
                                        .thenReturn(lettore);

        this.mockMvc.perform(post("/registrazione/lettore")
                .param("email", lettore.getEmail())
                .param("username", lettore.getUsername())
                .param("nome", lettore.getNome())
                .param("cognome", lettore.getCognome())
                .param("password", "LettorePassword")
                .param("conferma_password", confermaPassword)
                .param("provincia", lettore.getProvincia())
                .param("citta", lettore.getCitta())
                .param("via", lettore.getVia())
                .param("recapito_telefonico", lettore.getRecapitoTelefonico()))
                .andExpect(view().name("registrazione"));
    }

    /**
     * Metodo che testa la funzionalità gestita dal
     * controller per la registrazione di un lettore
     * avvenuta in modo scorretto
     * simulando la richiesta http.
     * @param lettore Il lettore da registrare
     * @param confermaPassword il campo conferma password del
     * form per controllare
     * @throws Exception Eccezione per MovkMvc
     */
    @ParameterizedTest
    @DisplayName("Registrazione che non va a buon fine, "
            + "password e conferma password sbagliate")
    @MethodSource("provideRegistrazioneLettore")
    public void registrazioneLettoreErrataPassword(final Lettore lettore,
                                     final String confermaPassword)
            throws Exception {

        when(registrazioneService.registraLettore(new Lettore()))
                .thenReturn(lettore);

        this.mockMvc.perform(post("/registrazione/lettore")
                .param("email", lettore.getEmail())
                .param("username", lettore.getUsername())
                .param("nome", lettore.getNome())
                .param("cognome", lettore.getCognome())
                .param("password", "PASSWORD_SBAGLIATA")//Password errata
                .param("conferma_password", confermaPassword)
                .param("provincia", lettore.getProvincia())
                .param("citta", lettore.getCitta())
                .param("via", lettore.getVia())
                .param("recapito_telefonico", lettore.getRecapitoTelefonico()))
                .andExpect(view().name("registrazione_lettore"));
    }

    /**
     * Simula i dati inviati da un metodo
     * http attraverso uno stream.
     * @return Lo stream di dati.
     */
    private static Stream<Arguments> provideRegistrazioneLettore() {

        return Stream.of(
                Arguments.of(
                         new Lettore(
                                "giuliociccione@gmail.com",
                                "LettorePassword",
                                "Salerno",
                                "Baronissi",
                                "Via Barone 11",
                                "3456789012",
                                "SuperLettore",
                                "Giulio",
                                "Ciccione"
                        ), "LettorePassword"//Password Conferma
                )
        );
    }

    /**
     * Restituisce i dati per la registrazione della biblioteca.
     * @return i dati per il testing
     */
    private static Stream<Arguments> provideRegistrazioneBiblioteca() {

        return Stream.of(Arguments.of(
                new Biblioteca(
                        "bibliotecacarrisi@gmail.com",
                        "BibliotecaPassword",
                        "Napoli",
                        "Torre del Greco",
                        "Via Carrisi 47",
                        "1234567890",
                        "Biblioteca Carrisi"
                ),
                "BibliotecaPassword"//Password Conferma
        ));
    }
}
