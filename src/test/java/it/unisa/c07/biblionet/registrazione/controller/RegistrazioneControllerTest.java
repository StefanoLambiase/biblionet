package it.unisa.c07.biblionet.registrazione.controller;

import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.registrazione.service.RegistrazioneService;
import org.junit.jupiter.api.Assertions;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
     * Metodo che testa la funzionalità gestita dal
     * controller per la registrazione di un esperto
     * avvenuta correttamente
     * simulando la richiesta http.
     *
     * @param esperto          L'esperto da registrare
     * @param confermaPassword la password da confermare
     * @param emailBiblioteca  la mail della biblioteca
     * @param generi           gli eventuali generi inseriti dall'utente
     * @throws Exception Eccezione per MockMvc
     */
    @ParameterizedTest
    @DisplayName("Registrazione Esperto che va a buon fine")
    @MethodSource("provideRegistrazioneEsperto")
    public void registrazioneEspertoBuonFine(
            final Esperto esperto, final String confermaPassword,
            final String emailBiblioteca,
            final String[] generi) throws Exception {

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
                .param("genere", generi))
                .andExpect(view().name("login"));
    }

    /**
     * Metodo che testa la funzionalità gestita dal
     * controller per la registrazione di un esperto
     * avvenuta in modo errato poiché la conferma della
     * password è sbagliata simulando la richiesta http.
     *
     * @param esperto L'esperto da registrare
     * @param confermaPassword la password da confermare
     * @param emailBiblioteca  la mail della biblioteca
     * @param generi           gli eventuali generi inseriti dall'utente
     * @throws Exception Eccezione per MockMvc
     */
    @ParameterizedTest
    @DisplayName("Registrazione Esperto che non va a buon fine "
               + "perché la conferma password è sbagliata")
    @MethodSource("provideRegistrazioneEsperto")
    public void registrazioneEspertoErrorePassword(
            final Esperto esperto, final String confermaPassword,
            final String emailBiblioteca,
            final String[] generi) throws Exception {

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
                .param("conferma_password", "PASSWORDERRATA")
                .param("provincia", esperto.getProvincia())
                .param("citta", esperto.getCitta())
                .param("via", esperto.getVia())
                .param("recapito_telefonico", esperto.getRecapitoTelefonico())
                .param("email_biblioteca", emailBiblioteca)
                .param("genere", generi))
                .andExpect(view().name("registrazione_esperto"));
    }

    /**
     * Metodo che testa la funzionalità gestita dal
     * controller per la registrazione di un esperto
     * avvenuta correttamente
     * simulando la richiesta http.
     *
     * @param esperto          L'esperto da registrare
     * @param confermaPassword la password da confermare
     * @param emailBiblioteca  la mail della biblioteca
     * @param generi           gli eventuali generi inseriti dall'utente
     * @throws Exception Eccezione per MockMvc
     */
    @ParameterizedTest
    @DisplayName("Registrazione Esperto che non va a buon fine "
               + "perché la mail della biblioteca non corrisponde a "
               + "nessun utente")
    @MethodSource("provideRegistrazioneEsperto")
    public void registrazioneEspertoEmailBibliotecaErrata(
            final Esperto esperto, final String confermaPassword,
            final String emailBiblioteca,
            final String[] generi) throws Exception {

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
                thenReturn(null);

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
                .param("genere", generi))
                .andExpect(view().name("registrazione_esperto"));
    }

    /**
     * Metodo che testa la funzionalità gestita dal
     * controller per la registrazione di un esperto
     * avvenuta correttamente
     * simulando la richiesta http.
     *
     * @param esperto          L'esperto da registrare
     * @param confermaPassword la password da confermare
     * @param emailBiblioteca  la mail della biblioteca
     * @throws Exception Eccezione per MockMvc
     */
    @ParameterizedTest
    @DisplayName("Registrazione Esperto che va a buon fine anche se i generi "
               + "passati sono null")
    @MethodSource("provideRegistrazioneEsperto")
    public void registrazioneEspertoBuonFineGeneriNull(
            final Esperto esperto, final String confermaPassword,
            final String emailBiblioteca) throws Exception {

        Biblioteca biblioteca = new Biblioteca(
                "bibliotecacarrisi@gmail.com",
                "BibliotecaPassword",
                "Napoli",
                "Torre del Greco",
                "Via Carrisi 47",
                "1234567890",
                "Biblioteca Carrisi"
        );

        String[] generi = {""};

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
                .param("genere", generi))
                .andExpect(view().name("login"));
    }

    @ParameterizedTest
    @DisplayName("Registrazione Esperto che va a buon fine anche se i generi "
            + "passati sono null")
    @MethodSource("provideRegistrazioneEsperto")
    public void registrazioneEspertoEccezione( final Esperto esperto, final String confermaPassword,
                                               final String emailBiblioteca,
                                               final String[] generi) throws Exception {

        Assertions.assertThrows(NoSuchAlgorithmException.class,()->{
            MessageDigest.getInstance("");
        });

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
                .param("genere", generi))
                .andExpect(view().name("registrazione_esperto"));



    }


    /**
     * Simula i dati inviati da un metodo
     * http attraverso uno stream.
     *
     * @return Lo stream di dati.
     */
    private static Stream<Arguments> provideRegistrazioneEsperto() {
        String[] generi = {"Fantasy", "Politico"};

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
                        ), "EspertoPassword", "bibliotecacarrisi@gmail.com",
                        generi
                )
        );

    }

    /**
     * Test che registra correttamente una biblioteca.
     *
     * @param biblioteca       la biblioteca da registrare
     * @param confermaPassword la password da confermare
     * @throws Exception Eccezione di MockMvc
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
                .andExpect(view().name("login"));
    }

    /**
     * Test che non registra correttamente una biblioteca.
     * poiché la conferma della password è errata
     *
     * @param biblioteca       la biblioteca da registrare
     * @param confermaPassword la passoword da confermare
     * @throws Exception Eccezione di MockMvc
     */
    @ParameterizedTest
    @DisplayName("Registrazione Biblioteca che non va a buon fine "
           + "perché la conferma password è sbagliata")
    @MethodSource("provideRegistrazioneBiblioteca")
    public void registrazioneBibliotecaPasswordErrata(
                                                final Biblioteca biblioteca,
                                                final String confermaPassword)
                                                            throws Exception {

        when(registrazioneService.registraBiblioteca(new Biblioteca()))
                .thenReturn(biblioteca);

        this.mockMvc.perform(post("/registrazione/biblioteca")
                .param("email", biblioteca.getEmail())
                .param("nomeBiblioteca", biblioteca.getNomeBiblioteca())
                .param("password", "BibliotecaPassword")
                .param("conferma_password", "PASSWORDSBAGLIATA")
                .param("provincia", biblioteca.getProvincia())
                .param("citta", biblioteca.getCitta())
                .param("via", biblioteca.getVia())
                .param("recapito_telefonico",
                        biblioteca.getRecapitoTelefonico()))
                .andExpect(view().name("registrazione_biblioteca"));
    }


    /**
     * Restituisce i dati per la registrazione della biblioteca.
     *
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


    /**
     * controller per la registrazione di un lettore
     * avvenuta correttamente
     * simulando la richiesta http.
     *
     * @param lettore          Il lettore da registrare
     * @param confermaPassword il campo conferma password del
     *                         form per controllare
     * @throws Exception Eccezione per MockMvc
     */
    @ParameterizedTest
    @DisplayName("Registrazione Lettore che va a buon fine")
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
                .andExpect(view().name("login"));
    }

    /**
     * Metodo che testa la funzionalità gestita dal
     * controller per la registrazione di un lettore
     * avvenuta in modo scorretto
     * simulando la richiesta http.
     *
     * @param lettore          Il lettore da registrare
     * @param confermaPassword il campo conferma password del
     *                         form per controllare
     * @throws Exception Eccezione per MockMvc
     */
    @ParameterizedTest
    @DisplayName("Registrazione Lettore che non va a buon fine, "
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
     *
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

        when(registrazioneService.findLettoreByEmail(lettore.getEmail())).
                thenReturn(lettore);

        this.mockMvc.perform(post("/registrazione/conferma-modifica-lettore")
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
                .andExpect(view().name("login"));
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

        when(registrazioneService.findLettoreByEmail(lettore.getEmail())).
                thenReturn(lettore);

        this.mockMvc.perform(post("/registrazione/conferma-modifica-lettore")
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
                .andExpect(view().name("login"));
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

        when(registrazioneService.findLettoreByEmail(lettore.getEmail())).
                thenReturn(lettore);

        this.mockMvc.perform(post("/registrazione/conferma-modifica-lettore")
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
                .andExpect(view().name("modifica_dati_lettore"));
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
     * controller per la modifica di una biblioteca
     * avvenuta in modo corretto.
     *
     * @param biblioteca Il lettore da modificare
     * @param vecchiaPassword La vecchia password dell'account
     * @param nuovaPassword La nuova password dell'account
     * @param confermaPassword La conferma password
     * @throws Exception eccezione di MockMvc
     */
    @ParameterizedTest
    @DisplayName("Modifica Dati Biblioteca")
    @MethodSource("provideModificaBiblioteca")
    public void modificaBiblioteca(
            final Biblioteca biblioteca,
            final String vecchiaPassword,
            final String nuovaPassword,
            final String confermaPassword) throws Exception {

        when(registrazioneService.findBibliotecaByEmail(biblioteca.getEmail())).
                thenReturn(biblioteca);

        this.mockMvc.perform(post("/registrazione/conferma-modifica-biblioteca")
                .param("email", biblioteca.getEmail())
                .param("nomeBiblioteca", biblioteca.getNomeBiblioteca())
                .param("vecchia_password", vecchiaPassword)
                .param("nuova_password", nuovaPassword)
                .param("conferma_password", confermaPassword)
                .param("provincia", biblioteca.getProvincia())
                .param("citta", biblioteca.getCitta())
                .param("via", biblioteca.getVia())
                .param("recapito_telefonico",
                        biblioteca.getRecapitoTelefonico()))
                .andExpect(view().name("login"));
    }

    /**
     * Metodo che testa la funzionalità gestita dal
     * controller per la modifica di una biblioteca
     * avvenuta in modo scorretto.
     * La vecchiaPassword, nuovaPassword oppure confermaPassword
     * sono vuote.
     *
     * @param biblioteca Il lettore da modificare
     * @throws Exception eccezione di MockMvc
     */
    @ParameterizedTest
    @DisplayName("Modifica Dati Biblioteca Errato 1")
    @MethodSource("provideModificaBiblioteca")
    public void modificaBibliotecaErrato1(
            final Biblioteca biblioteca) throws Exception {

        when(registrazioneService.findBibliotecaByEmail(biblioteca.getEmail())).
                thenReturn(biblioteca);

        this.mockMvc.perform(post("/registrazione/conferma-modifica-biblioteca")
                .param("email", biblioteca.getEmail())
                .param("nomeBiblioteca", biblioteca.getNomeBiblioteca())
                .param("vecchia_password", "")//Vuote
                .param("nuova_password", "")//Vuote
                .param("conferma_password", "")//Vuote
                .param("provincia", biblioteca.getProvincia())
                .param("citta", biblioteca.getCitta())
                .param("via", biblioteca.getVia())
                .param("recapito_telefonico",
                        biblioteca.getRecapitoTelefonico()))
                .andExpect(view().name("login"));
    }

    /**
     * Metodo che testa la funzionalità gestita dal
     * controller per la modifica di una biblioteca
     * avvenuta in modo scorretto.
     * Se la vecchia password inserita é diversa
     * da quella corrente.
     * OPPURE.
     * Se nuovaPassword é diversa da confermaPassword.
     *
     * @param biblioteca La biblioteca da modificare
     * @param nuovaPassword La nuova password dell'account
     * @param confermaPassword La conferma password
     * @throws Exception eccezione di MockMvc
     */
    @ParameterizedTest
    @DisplayName("Modifica Dati Biblioteca Errato 2")
    @MethodSource("provideModificaBiblioteca")
    public void modificaBibliotecaErrato2(
            final Biblioteca biblioteca,
            final String nuovaPassword,
            final String confermaPassword) throws Exception {

        when(registrazioneService.findBibliotecaByEmail(biblioteca.getEmail())).
                thenReturn(biblioteca);

        this.mockMvc.perform(post("/registrazione/conferma-modifica-biblioteca")
                .param("email", biblioteca.getEmail())
                .param("nomeBiblioteca", biblioteca.getNomeBiblioteca())
                .param("vecchia_password", "SBAGLIATA")
                .param("nuova_password", nuovaPassword)
                .param("conferma_password", confermaPassword)
                .param("provincia", biblioteca.getProvincia())
                .param("citta", biblioteca.getCitta())
                .param("via", biblioteca.getVia())
                .param("recapito_telefonico",
                        biblioteca.getRecapitoTelefonico()))
                .andExpect(view().name("modifica_dati_biblioteca"));
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
                        ),
                        "BibliotecaPassword", // vecchia password
                        "NuovaPassword",      // nuova password
                        "NuovaPassword"       // conferma password
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

        when(registrazioneService.findEspertoByEmail(esperto.getEmail())).
                thenReturn(esperto);

        when(registrazioneService
                .findBibliotecaByEmail(esperto.getBiblioteca().getEmail()))
                .thenReturn(esperto.getBiblioteca());

        this.mockMvc.perform(post("/registrazione/conferma-modifica-esperto")
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
                .andExpect(view().name("login"));
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

        when(registrazioneService.findEspertoByEmail(esperto.getEmail())).
                thenReturn(esperto);

        when(registrazioneService
                .findBibliotecaByEmail(esperto.getBiblioteca().getEmail()))
                .thenReturn(esperto.getBiblioteca());

        this.mockMvc.perform(post("/registrazione/conferma-modifica-esperto")
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
                .andExpect(view().name("modifica_dati_esperto"));
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

        when(registrazioneService.findEspertoByEmail(esperto.getEmail())).
                thenReturn(esperto);

        when(registrazioneService
                .findBibliotecaByEmail(emailBiblioteca))
                .thenReturn(esperto.getBiblioteca());

        this.mockMvc.perform(post("/registrazione/conferma-modifica-esperto")
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
                .andExpect(view().name("modifica_dati_esperto"));
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

        when(registrazioneService.findEspertoByEmail(esperto.getEmail())).
                thenReturn(esperto);

        when(registrazioneService
                .findBibliotecaByEmail(emailBiblioteca))
                .thenReturn(esperto.getBiblioteca());

        this.mockMvc.perform(post("/registrazione/conferma-modifica-esperto")
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
                .andExpect(view().name("modifica_dati_esperto"));
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

        when(registrazioneService.findEspertoByEmail(esperto.getEmail())).
                thenReturn(esperto);

        when(registrazioneService
                .findBibliotecaByEmail(emailBiblioteca))
                .thenReturn(null);

        this.mockMvc.perform(post("/registrazione/conferma-modifica-esperto")
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
                .andExpect(view().name("modifica_dati_esperto"));
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

        when(registrazioneService.isUserEsperto(esperto)).thenReturn(true);

        this.mockMvc.perform(get("/registrazione/modifica-dati")
                .sessionAttr("loggedUser", esperto))
                .andExpect(view().name("modifica_dati_esperto"));


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

        when(registrazioneService.isUserLettore(lettore)).thenReturn(true);

        this.mockMvc.perform(get("/registrazione/modifica-dati")
                .sessionAttr("loggedUser", lettore))
                .andExpect(view().name("modifica_dati_lettore"));
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

        when(registrazioneService.isUserBiblioteca(biblioteca))
                .thenReturn(true);

        this.mockMvc.perform(get("/registrazione/modifica-dati")
                .sessionAttr("loggedUser", biblioteca))
                .andExpect(view().name("modifica_dati_biblioteca"));
    }

    /**
     * Metodo che testa la funzionalità di scegliere
     * di modificare un utente che non è presente in sessione.
     * @throws Exception eccezione di mockMvc
     */
    @Test
    @DisplayName("Scelta modifica utente non presente in sessione")
    public void sceltaModificaNull() throws Exception {

        this.mockMvc.perform(get("/registrazione/modifica-dati"))
                .andExpect(view().name("login"));
    }



}
