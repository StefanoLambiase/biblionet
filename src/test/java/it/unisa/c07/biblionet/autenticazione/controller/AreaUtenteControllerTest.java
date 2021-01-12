package it.unisa.c07.biblionet.autenticazione.controller;

import it.unisa.c07.biblionet.autenticazione.service.AutenticazioneService;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import org.junit.jupiter.api.DisplayName;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Alessio Casolaro
 * @author Antonio Della Porta
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AreaUtenteControllerTest {


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
     * Metodo che testa la funzionalità gestita dal
     * controller per la modifica di un lettore
     * avvenuta in modo corretto.
     *
     * @param lettore Il lettore da modificare
     * @param vecchiaPassword La vecchia password dell'account
     * @param nuovaPassword La nuova password dell'account
     * @param confermaPassword La conferma password
     * @throws Exception eccezione di mockMvc
     */
    @ParameterizedTest
    @DisplayName("Modifica Dati Lettore")
    @MethodSource("provideModificaLettore")
    public void modificaLettoreCorretto(
            final Lettore lettore,
            final String vecchiaPassword,
            final String nuovaPassword,
            final String confermaPassword) throws Exception {

        when(autenticazioneService.findLettoreByEmail(lettore.getEmail())).
                thenReturn(lettore);

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
                .param("recapito_telefonico", lettore.getRecapitoTelefonico()))
                .andExpect(view().name("autenticazione/login"));
    }

    /**
     * Metodo che testa la funzionalità gestita dal
     * controller per la modifica di un lettore
     * avvenuta in modo scorretto.
     * La vecchiaPassword, nuovaPassword oppure confermaPassword
     * sono vuote.
     *
     * @param lettore Il lettore da modificare
     * @throws Exception eccezione di mockMvc
     */
    @ParameterizedTest
    @DisplayName("Modifica Dati Lettore Errato 1")
    @MethodSource("provideModificaLettore")
    public void modificaLettoreErrato1(
            final Lettore lettore) throws Exception {

        when(autenticazioneService.findLettoreByEmail(lettore.getEmail())).
                thenReturn(lettore);

        this.mockMvc.perform(post("/conferma-modifica-lettore")
                .param("email", lettore.getEmail())
                .param("nome", lettore.getNome())
                .param("cognome", lettore.getCognome())
                .param("username", lettore.getUsername())
                .param("vecchia_password", "")//Vuote
                .param("nuova_password", "")//Vuote
                .param("conferma_password", "NuovaPassword")
                .param("provincia", lettore.getProvincia())
                .param("citta", lettore.getCitta())
                .param("via", lettore.getVia())
                .param("recapito_telefonico", lettore.getRecapitoTelefonico()))
                .andExpect(view().name("autenticazione/login"));
    }

    /**
     * Metodo che testa la funzionalità gestita dal
     * controller per la modifica di un lettore
     * avvenuta in modo scorretto.
     * Se la vecchia password inserita é diversa
     * da quella corrente.
     * OPPURE.
     * Se nuovaPassword é diversa da confermaPassword.
     *
     * @param lettore Il lettore da modificare
     * @param vecchiaPassword La vecchia password dell'account
     * @throws Exception eccezione di MockMvc
     */
    @ParameterizedTest
    @DisplayName("Modifica Dati Lettore Errato 2")
    @MethodSource("provideModificaLettore")
    public void modificaLettoreErrato2(
            final Lettore lettore,
            final String vecchiaPassword) throws Exception {

        when(autenticazioneService.findLettoreByEmail(lettore.getEmail())).
                thenReturn(lettore);

        this.mockMvc.perform(post("/conferma-modifica-lettore")
                .param("email", lettore.getEmail())
                .param("nome", lettore.getNome())
                .param("cognome", lettore.getCognome())
                .param("username", lettore.getUsername())
                .param("vecchia_password", vecchiaPassword)
                .param("nuova_password", "PASSWORD DIVERSE")
                .param("conferma_password", "DIVERSA PASSWORD")
                .param("provincia", lettore.getProvincia())
                .param("citta", lettore.getCitta())
                .param("via", lettore.getVia())
                .param("recapito_telefonico", lettore.getRecapitoTelefonico()))
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
                                "giuliociccione@gmail.com",
                                "LettorePassword",
                                "Salerno",
                                "Baronissi",
                                "Via Barone 11",
                                "3456789012",
                                "SuperLettore",
                                "Giulio",
                                "Ciccione"
                        ),
                        "LettorePassword", // vecchia password
                        "NuovaPassword",   // nuova password
                        "NuovaPassword"    // conferma password
                )
        );
    }


    /**
     * Metodo che testa la funzionalità gestita dal
     * controller per la modifica di un esperto
     * avvenuta in modo corretto.
     *
     * @param esperto L'esperto da modificare
     * @param vecchiaPassword La vecchia password dell'account
     * @param nuovaPassword La nuova password dell'account
     * @param confermaPassword La conferma password
     * @param emailBiblioteca la mail della biblioteca dove lavora
     * @throws Exception eccezione di MockMvc
     */
    @ParameterizedTest
    @DisplayName("Modifica Dati Esperto Corretto")
    @MethodSource("provideModificaEsperto")
    public void modificaEsperto(
            final Esperto esperto,
            final String vecchiaPassword,
            final String nuovaPassword,
            final String confermaPassword,
            final String emailBiblioteca) throws Exception {

        when(autenticazioneService.findEspertoByEmail(esperto.getEmail())).
                thenReturn(esperto);

        when(autenticazioneService
                .findBibliotecaByEmail(esperto.getBiblioteca().getEmail()))
                .thenReturn(esperto.getBiblioteca());

        this.mockMvc.perform(post("/conferma-modifica-esperto")
                .param("email", esperto.getEmail())
                .param("nome", esperto.getNome())
                .param("cognome", esperto.getCognome())
                .param("username", esperto.getUsername())
                .param("password", "EspertoPassword")
                .param("vecchia_password", vecchiaPassword)
                .param("nuova_password", nuovaPassword)
                .param("conferma_password", confermaPassword)
                .param("provincia", esperto.getProvincia())
                .param("citta", esperto.getCitta())
                .param("via", esperto.getVia())
                .param("recapito_telefonico", esperto.getRecapitoTelefonico())
                .param("email_biblioteca", emailBiblioteca)
                .param("genere", " "))
                .andExpect(view().name("autenticazione/login"));
    }

    /**
     * Metodo che testa la funzionalità gestita dal
     * controller per la modifica di un esperto
     * avvenuta in modo scorretto.
     * La vecchiaPassword, nuovaPassword oppure confermaPassword
     * sono vuote.
     *
     * @param esperto L'esperto da modificare
     * @param emailBiblioteca L'email della biblioteca in cui lavora.
     * @throws Exception eccezione di MockMvc
     */
    @ParameterizedTest
    @DisplayName("Modifica Dati Esperto Password non cambiata")
    @MethodSource("provideModificaEsperto")
    public void modificaEspertoErrato1(
            final Esperto esperto,
            final String emailBiblioteca) throws Exception {

        when(autenticazioneService.findEspertoByEmail(esperto.getEmail())).
                thenReturn(esperto);

        when(autenticazioneService
                .findBibliotecaByEmail(esperto.getBiblioteca().getEmail()))
                .thenReturn(esperto.getBiblioteca());

        this.mockMvc.perform(post("/conferma-modifica-esperto")
                .param("email", esperto.getEmail())
                .param("nome", esperto.getNome())
                .param("cognome", esperto.getCognome())
                .param("username", esperto.getUsername())
                .param("password", "EspertoPassword")
                .param("vecchia_password", "")
                .param("nuova_password", "")
                .param("conferma_password", "")
                .param("provincia", esperto.getProvincia())
                .param("citta", esperto.getCitta())
                .param("via", esperto.getVia())
                .param("recapito_telefonico", esperto.getRecapitoTelefonico())
                .param("email_biblioteca", emailBiblioteca)
                .param("genere", " "))
                .andExpect(view().name("area-utente/modifica-dati-esperto"));
    }

    /**
     * Metodo che testa la funzionalità gestita dal
     * controller per la modifica di una biblioteca
     * avvenuta in modo scorretto.
     * Se la vecchia password inserita é diversa
     * da quella corrente.
     *
     * @param esperto L'esperto da modificare
     * @param nuovaPassword La nuova password dell'account
     * @param confermaPassword La conferma password
     * @param emailBiblioteca L'email della biblioteca in cui lavora.
     * @throws Exception eccezione di MockMvc
     */
    @ParameterizedTest
    @DisplayName("Modifica Dati Esperto vecchia password errata")
    @MethodSource("provideModificaEsperto")
    public void modificaEspertoErrato2(
            final Esperto esperto,
            final String nuovaPassword,
            final String confermaPassword,
            final String emailBiblioteca) throws Exception {

        when(autenticazioneService.findEspertoByEmail(esperto.getEmail())).
                thenReturn(esperto);

        when(autenticazioneService
                .findBibliotecaByEmail(emailBiblioteca))
                .thenReturn(esperto.getBiblioteca());

        this.mockMvc.perform(post("/conferma-modifica-esperto")
                .param("email", esperto.getEmail())
                .param("nome", esperto.getNome())
                .param("cognome", esperto.getCognome())
                .param("username", esperto.getUsername())
                .param("password", "EspertoPassword")
                .param("vecchia_password", "PASSWORD ERRATA")
                .param("nuova_password", nuovaPassword)
                .param("conferma_password", confermaPassword)
                .param("provincia", esperto.getProvincia())
                .param("citta", esperto.getCitta())
                .param("via", esperto.getVia())
                .param("recapito_telefonico", esperto.getRecapitoTelefonico())
                .param("email_biblioteca", emailBiblioteca)
                .param("genere", " "))
                .andExpect(view().name("area-utente/modifica-dati-esperto"));
    }

    /**
     * Metodo che testa la funzionalità gestita dal
     * controller per la modifica di una biblioteca
     * avvenuta in modo scorretto.
     * Se nuovaPassword é diversa da confermaPassword.
     *
     * @param esperto L'esperto da modificare
     * @param nuovaPassword La nuova password dell'account
     * @param emailBiblioteca L'email della biblioteca in cui lavora.
     * @throws Exception eccezione di MockMvc
     */
    @ParameterizedTest
    @DisplayName("Modifica Dati Esperto conferma password errata")
    @MethodSource("provideModificaEsperto")
    public void modificaEspertoErrato3(
            final Esperto esperto,
            final String nuovaPassword,
            final String emailBiblioteca) throws Exception {

        when(autenticazioneService.findEspertoByEmail(esperto.getEmail())).
                thenReturn(esperto);

        when(autenticazioneService
                .findBibliotecaByEmail(emailBiblioteca))
                .thenReturn(esperto.getBiblioteca());

        this.mockMvc.perform(post("/conferma-modifica-esperto")
                .param("email", esperto.getEmail())
                .param("nome", esperto.getNome())
                .param("cognome", esperto.getCognome())
                .param("username", esperto.getUsername())
                .param("password", "EspertoPassword")
                .param("vecchia_password", "EspertoPassword")
                .param("nuova_password", nuovaPassword)
                .param("conferma_password", "PASSWORDSBAGLIATA")
                .param("provincia", esperto.getProvincia())
                .param("citta", esperto.getCitta())
                .param("via", esperto.getVia())
                .param("recapito_telefonico", esperto.getRecapitoTelefonico())
                .param("email_biblioteca", emailBiblioteca)
                .param("genere", " "))
                .andExpect(view().name("area-utente/modifica-dati-esperto"));
    }

    /**
     * Metodo che testa la funzionalità gestita dal
     * controller per la modifica di una biblioteca
     * avvenuta in modo scorretto.
     *
     * @param esperto L'esperto da modificare
     * @param nuovaPassword La nuova password dell'account
     * @param confermaPassword La conferma password
     * @param emailBiblioteca L'email della biblioteca in cui lavora.
     * @throws Exception eccezione di MockMvc
     */
    @ParameterizedTest
    @DisplayName("Modifica Dati Esperto biblioteca null")
    @MethodSource("provideModificaEsperto")
    public void modificaEspertoErrato4(
            final Esperto esperto,
            final String nuovaPassword,
            final String confermaPassword,
            final String emailBiblioteca) throws Exception {

        when(autenticazioneService.findEspertoByEmail(esperto.getEmail())).
                thenReturn(esperto);

        when(autenticazioneService
                .findBibliotecaByEmail(emailBiblioteca))
                .thenReturn(null);

        this.mockMvc.perform(post("/conferma-modifica-esperto")
                .param("email", esperto.getEmail())
                .param("nome", esperto.getNome())
                .param("cognome", esperto.getCognome())
                .param("username", esperto.getUsername())
                .param("password", "EspertoPassword")
                .param("vecchia_password", "PASSWORD ERRATA")
                .param("nuova_password", nuovaPassword)
                .param("conferma_password", confermaPassword)
                .param("provincia", esperto.getProvincia())
                .param("citta", esperto.getCitta())
                .param("via", esperto.getVia())
                .param("recapito_telefonico", esperto.getRecapitoTelefonico())
                .param("email_biblioteca", emailBiblioteca)
                .param("genere", " "))
                .andExpect(view().name("area-utente/modifica-dati-esperto"));
    }


    /**
     * Simula i dati inviati da un metodo
     * http attraverso uno stream.
     *
     * @return Lo stream di dati.
     */
    private static Stream<Arguments> provideModificaEsperto() {

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
                                new Biblioteca(
                                        "bibliotecacarrisi@gmail.com",
                                        "BibliotecaPassword",
                                        "Napoli",
                                        "Torre del Greco",
                                        "Via Carrisi 47",
                                        "1234567890",
                                        "Biblioteca Carrisi"
                                )
                        ),
                        "EspertoPassword",              // vecchia password
                        "NuovaPassword",                // nuova password
                        "NuovaPassword",                // conferma password
                        "bibliotecacarrisi@gmail.com"   // mail della biblioteca
                )
        );
    }


    /**
     * Metodo che testa la funzionalità di scegliere
     * di modificare un esperto loggato.
     * @param esperto l'esperto in sessione
     * @throws Exception eccezione di mockMvc
     */
    @ParameterizedTest
    @MethodSource("provideModificaEsperto")
    @DisplayName("Scelta modifica esperto")
    public void sceltaModificaEsperto(final Esperto esperto) throws Exception {

        when(autenticazioneService.isEsperto(esperto)).thenReturn(true);

        this.mockMvc.perform(get("/modifica-dati")
                .sessionAttr("loggedUser", esperto))
                .andExpect(view().name("area-utente/modifica-dati-esperto"));


    }

    /**
     * Metodo che testa la funzionalità di scegliere
     * di modificare un lettore loggato.
     * @param lettore il lettore in sessione
     * @throws Exception eccezione di mockMvc
     */
    @ParameterizedTest
    @MethodSource("provideModificaLettore")
    @DisplayName("Scelta modifica lettore")
    public void sceltaModificaLettore(final Lettore lettore) throws Exception {

        when(autenticazioneService.isLettore(lettore)).thenReturn(true);

        this.mockMvc.perform(get("/modifica-dati")
                .sessionAttr("loggedUser", lettore))
                .andExpect(view().name("area-utente/modifica-dati-lettore"));
    }

    /**
     * Metodo che testa la funzionalità di scegliere
     * di modificare una biblioteca loggato.
     * @param biblioteca la biblioteca in sessione
     * @throws Exception eccezione di mockMvc
     */
    @ParameterizedTest
    @MethodSource("provideModificaBiblioteca")
    @DisplayName("Scelta modifica biblioteca")
    public void sceltaModificaBiblioteca(final Biblioteca biblioteca)
            throws Exception {

        when(autenticazioneService.isBiblioteca(biblioteca))
                .thenReturn(true);

        this.mockMvc.perform(get("/modifica-dati")
                .sessionAttr("loggedUser", biblioteca))
                .andExpect(view().name("area-utente/modifica-dati-biblioteca"));
    }

    /**
     * Metodo che testa la funzionalità di scegliere
     * di modificare un utente che non è presente in sessione.
     * @throws Exception eccezione di mockMvc
     */
    @Test
    @DisplayName("Scelta modifica utente non presente in sessione")
    public void sceltaModificaNull() throws Exception {

        this.mockMvc.perform(get("/modifica-dati"))
                .andExpect(view().name("autenticazione/login"));
    }

    /**
     * Simula i dati inviati da un metodo
     * http attraverso uno stream.
     *
     * @return Lo stream di dati.
     */
    private static Stream<Arguments> provideModificaBiblioteca() {
        return Stream.of(
          Arguments.of(
                  new Biblioteca(
                          "bibliotecacarrisi@gmail.com",
                          "BibliotecaPassword",
                          "Napoli",
                          "Torre del Greco",
                          "Via Carrisi 47",
                          "1234567890",
                          "Biblioteca Carrisi"
                  )
          )
        );
    }

    /**
     * Metodo che testa la funzionalità di scegliere
     * di visualizzare l'area utente Biblioteca.
     * @param biblioteca la biblioteca in sessione
     * @throws Exception eccezione di mockMvc
     */
    @ParameterizedTest
    @MethodSource("provideModificaBiblioteca")
    @DisplayName("Visualizza Area Utente Biblioteca")
    public void visualizzaAreaUtenteBiblioteca(final Biblioteca biblioteca)
            throws Exception {

        when(autenticazioneService.isBiblioteca(biblioteca))
                .thenReturn(true);

        this.mockMvc.perform(get("/area-utente")
                .sessionAttr("loggedUser", biblioteca))
                .andExpect(view().name("area-utente/visualizza-biblioteca"));
    }

    /**
     * Metodo che testa la funzionalità di scegliere
     * di visualizzare l'area utente Esperto.
     * @param esperto l'esperto in sessione
     * @throws Exception eccezione di mockMvc
     */
    @ParameterizedTest
    @MethodSource("provideModificaEsperto")
    @DisplayName("Visualizza Area Utente Esperto")
    public void visualizzaAreaUtenteEsperto(final Esperto esperto)
            throws Exception {

        when(autenticazioneService.isEsperto(esperto))
                .thenReturn(true);

        this.mockMvc.perform(get("/area-utente")
                .sessionAttr("loggedUser", esperto))
                .andExpect(view().name("area-utente/visualizza-esperto"));
    }

    /**
     * Metodo che testa la funzionalità di scegliere
     * di visualizzare l'area utente Lettore.
     * @param lettore Il lettore in sessione
     * @throws Exception eccezione di mockMvc
     */
    @ParameterizedTest
    @MethodSource("provideModificaLettore")
    @DisplayName("Visualizza Area Utente Lettore")
    public void visualizzaAreaUtenteLettore(final Lettore lettore)
            throws Exception {

        when(autenticazioneService.isLettore(lettore))
                .thenReturn(true);

        this.mockMvc.perform(get("/area-utente")
                .sessionAttr("loggedUser", lettore))
                .andExpect(view().name("area-utente/visualizza-lettore"));
    }

    /**
     * Metodo che testa la funzionalità di
     * di visualizzare i clubs dell' utente Lettore.
     * @param lettore Il lettore in sessione
     * @throws Exception eccezione di mockMvc
     */
    @ParameterizedTest
    @MethodSource("provideModificaLettore")
    @DisplayName("Visualizza Clubs Lettore")
    public void visualizzaClubsLettore(final Lettore lettore)
            throws Exception {

        when(autenticazioneService.isLettore(lettore))
                .thenReturn(true);

        this.mockMvc.perform(get("/area-utente/visualizza-clubs-personali-lettore")
                .sessionAttr("loggedUser", lettore))
                .andExpect(view()
                        .name("area-utente/visualizza-clubs-personali"));
    }
    /**
     * Metodo che testa la funzionalità di
     * di visualizzare i clubs dell' utente Esperto.
     * @param esperto L'esperto in sessione
     * @throws Exception eccezione di mockMvc
     */
    @ParameterizedTest
    @MethodSource("provideModificaEsperto")
    @DisplayName("Visualizza Clubs Esperto")
    public void visualizzaClubsLettore(final Esperto esperto)
            throws Exception {

        when(autenticazioneService.isEsperto(esperto))
                .thenReturn(true);

        this.mockMvc.perform(get("/area-utente/visualizza-clubs-personali-esperto")
                .sessionAttr("loggedUser", esperto))
                .andExpect(view()
                        .name("area-utente/visualizza-clubs-personali"));
    }
}
