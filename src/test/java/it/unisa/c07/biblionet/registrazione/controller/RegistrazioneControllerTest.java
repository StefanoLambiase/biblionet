package it.unisa.c07.biblionet.registrazione.controller;

import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
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
    @MethodSource("provideRegistrazioneBiblioteca")
    public void registrazioneBibliotecaBuonFine(final Biblioteca biblioteca,String confermaPassword) throws Exception {

        when(registrazioneService.registraBiblioteca(new Biblioteca())).thenReturn(biblioteca);

        this.mockMvc.perform(post("/registrazione/biblioteca")
                .param("email",biblioteca.getEmail())
                .param("nomeBiblioteca",biblioteca.getNomeBiblioteca())
                .param("password","BibliotecaPassword")
                .param("conferma_password",confermaPassword)
                .param("provincia",biblioteca.getProvincia())
                .param("citta",biblioteca.getCitta())
                .param("via",biblioteca.getVia())
                .param("recapito_telefonico",biblioteca.getRecapitoTelefonico()))
                .andExpect(view().name("registrazione"));
    }

    @ParameterizedTest
    @DisplayName("Registrazione che non va a buon fine, password e conferma password sbagliate")
    @MethodSource("provideRegistrazioneBiblioteca")
    public void registrazioneBibliotecaErrataPassword(final Biblioteca biblioteca,String confermaPassword) throws Exception {

        when(registrazioneService.registraBiblioteca(new Biblioteca())).thenReturn(biblioteca);

        this.mockMvc.perform(post("/registrazione/biblioteca")
                .param("email",biblioteca.getEmail())
                .param("nomeBiblioteca",biblioteca.getNomeBiblioteca())
                .param("password","PASSWORD_SBAGLIATA")//Password errata
                .param("conferma_password",confermaPassword)
                .param("provincia",biblioteca.getProvincia())
                .param("citta",biblioteca.getCitta())
                .param("via",biblioteca.getVia())
                .param("recapito_telefonico",biblioteca.getRecapitoTelefonico()))
                .andExpect(view().name("registrazione_biblioteca"));
    }

    private static Stream<Arguments> provideRegistrazioneBiblioteca() {

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
                        ,"BibliotecaPassword"//Password Conferma
                )
        );
    }
}