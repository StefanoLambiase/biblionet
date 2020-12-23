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

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrazioneControllerTest {

    @MockBean
    private RegistrazioneService registrazioneService;

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @DisplayName("Registrazione che va a buon fine")
    @MethodSource("provideRegistrazioneLettore")
    public void registrazioneLettoreBuonFine(final Lettore lettore, String confermaPassword) throws Exception {

        when(registrazioneService.registraLettore(new Lettore())).thenReturn(lettore);

        this.mockMvc.perform(post("/registrazione/lettore")
                .param("email",lettore.getEmail())
                .param("username",lettore.getUsername())
                .param("nome",lettore.getNome())
                .param("cognome",lettore.getCognome())
                .param("password","LettorePassword")
                .param("conferma_password",confermaPassword)
                .param("provincia",lettore.getProvincia())
                .param("citta",lettore.getCitta())
                .param("via",lettore.getVia())
                .param("recapito_telefonico",lettore.getRecapitoTelefonico()))
                .andExpect(view().name("registrazione"));
    }

    @ParameterizedTest
    @DisplayName("Registrazione che non va a buon fine, password e conferma password sbagliate")
    @MethodSource("provideRegistrazioneLettore")
    public void registrazioneLettoreErrataPassword(final Lettore lettore,String confermaPassword) throws Exception {

        when(registrazioneService.registraLettore(new Lettore())).thenReturn(lettore);

        this.mockMvc.perform(post("/registrazione/lettore")
                .param("email",lettore.getEmail())
                .param("username",lettore.getUsername())
                .param("nome",lettore.getNome())
                .param("cognome",lettore.getCognome())
                .param("password","PASSWORD_SBAGLIATA")//Password errata
                .param("conferma_password",confermaPassword)
                .param("provincia",lettore.getProvincia())
                .param("citta",lettore.getCitta())
                .param("via",lettore.getVia())
                .param("recapito_telefonico",lettore.getRecapitoTelefonico()))
                .andExpect(view().name("registrazione_lettore"));
    }

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
                        )
                        ,"LettorePassword"//Password Conferma
                )
        );
    }
}