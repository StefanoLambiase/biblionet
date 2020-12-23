package it.unisa.c07.biblionet.registrazione.controller;

import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.registrazione.service.RegistrazioneService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @MethodSource("provideRegistrazioneEsperto")
    public void registrazioneEspertoBuonFine(final Esperto esperto,String confermaPassword,String email_biblioteca) throws Exception {

        Biblioteca biblioteca = new Biblioteca(
                "bibliotecacarrisi@gmail.com",
                "BibliotecaPassword",
                "Napoli",
                "Torre del Greco",
                "Via Carrisi 47",
                "1234567890",
                "Biblioteca Carrisi"
        );

        when(registrazioneService.registraEsperto(new Esperto())).thenReturn(esperto);
        when(registrazioneService.findBibliotecaByEmail(email_biblioteca)).thenReturn(biblioteca);

        this.mockMvc.perform(post("/registrazione/esperto")
                .param("email",esperto.getEmail())
                .param("nome",esperto.getNome())
                .param("cognome",esperto.getCognome())
                .param("username",esperto.getUsername())
                .param("password","EspertoPassword")
                .param("conferma_password",confermaPassword)
                .param("provincia",esperto.getProvincia())
                .param("citta",esperto.getCitta())
                .param("via",esperto.getVia())
                .param("recapito_telefonico",esperto.getRecapitoTelefonico())
                .param("email_biblioteca",email_biblioteca))
                .andExpect(view().name("registrazione"));
    }


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
                        )
                        ,"EspertoPassword"
                        ,"bibliotecacarrisi@gmail.com"
                )
        );

    }

    private static Stream<Arguments> provideRegistrazioneEspertoNoBiblioteca() {

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
                        )
                )
        );

    }
}
