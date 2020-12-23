package it.unisa.c07.biblionet.registrazione.controller;

import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.registrazione.service.RegistrazioneService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrlTemplate;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrazioneControllerTest {
    @MockBean
    private RegistrazioneService registrazioneService;

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @MethodSource("provideRegistrazioneBiblioteca")
    public void registrazioneBiblioteca(final Biblioteca biblioteca) throws Exception{
        when(registrazioneService.registraBiblioteca(biblioteca)).thenReturn(biblioteca);

        this.mockMvc.perform(get("/registrazione/scegli?scelta=Biblioteca"))
                .andExpect(view().name("registrazione_biblioteca"));
    }

    private static Stream<Arguments> provideRegistrazioneBiblioteca(){
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

}
