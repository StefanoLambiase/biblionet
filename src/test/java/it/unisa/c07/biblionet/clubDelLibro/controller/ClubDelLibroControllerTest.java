package it.unisa.c07.biblionet.clubDelLibro.controller;

import it.unisa.c07.biblionet.clubDelLibro.service.ClubDelLibroService;
import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class ClubDelLibroControllerTest {

    @MockBean
    private ClubDelLibroService clubService;

    @Mock
    private HttpServletRequest request;

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @MethodSource("provideCreaClubDelLibro")
    public void creaClubDelLibro(final String nome, final String descrizione, Esperto esperto) throws Exception {
        ClubDelLibro club = new ClubDelLibro();
        club.setNome(nome);
        club.setDescrizione(descrizione);
        when(request.getSession().getAttribute("utente")).thenReturn(esperto);
        this.mockMvc.perform(post("/club-del-libro/crea-club"))
                .andExpect(view().name("club-del-libro"));
    }

    private static Stream<Arguments> provideCreaClubDelLibro() {
        return Stream.of(
                Arguments.of("Club1", "descrizione1", new Esperto("drink@home.com",
                        "ALotOfBeerInMyLife",
                        "Salerno",
                        "Salerno",
                        "Via vicino casa di Stefano 2",
                        "3694578963",
                        "mrDuff",
                        "Nicola",
                        "Pagliara",
                        new Biblioteca("gmail@gmail.com",
                                "Ueuagliobellstuorolog69",
                                "Napoli",
                                "Scampia",
                                "Via Portici 47",
                                "3341278415",
                                "Vieni che non ti faccio niente bookstore")))
        );
    }

}
