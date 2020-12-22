package it.unisa.c07.biblionet.clubDelLibro.controller;

import it.unisa.c07.biblionet.clubDelLibro.service.ClubDelLibroService;
import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Viviana Pentangelo, Gianmario Voria
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ClubDelLibroControllerTest {

    /**
     * Mock del service per simulare
     * le operazioni dei metodi.
     */
    @MockBean
    private ClubDelLibroService clubService;

    /**
     * Inject di MockMvc per simulare
     * le richieste http.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Metodo che testa la funzionalità gestita dal
     * controller per la creazione di un club
     * simulando la richiesta http.
     * @param club Il club da creare
     * @throws Exception Eccezione per MovkMvc
     */
    @ParameterizedTest
    @MethodSource("provideClubDelLibro")
    public void creaClubDelLibro(final ClubDelLibro club) throws Exception {
        String[] list = {"A", "B"};
        MockMultipartFile copertina =
                new MockMultipartFile("copertina",
                        "filename.png",
                        "image/png",
                        "immagine di copertina".getBytes());
        when(clubService.getGeneri(Arrays.asList(list.clone())))
                .thenReturn(new ArrayList<>());
        when(clubService.creaClubDelLibro(club)).thenReturn(club);
        this.mockMvc.perform(MockMvcRequestBuilders
                            .multipart("/club-del-libro/crea")
                .file(copertina)
                .param("generi", list))
                .andExpect(view().name("redirect:/club-del-libro/"));
    }

    /**
     * Metodo che testa la funzionalità gestita dal
     * controller per la visualizzazione di
     * tutti i club del libro
     * simulando la richiesta http.
     * @param club Un club per simulare la lista
     * @throws Exception Eccezione per MovkMvc
     */
    @ParameterizedTest
    @MethodSource("provideClubDelLibro")
    public void visualizzaListaClubs(final ClubDelLibro club) throws Exception {
        List<ClubDelLibro> list = new ArrayList<>();
        list.add(club);
        when(clubService.visualizzaClubsDelLibro()).thenReturn(list);
        this.mockMvc.perform(get("/club-del-libro/"))
                .andExpect(model().attribute("listaClubs", list))
                .andExpect(view().name("visualizza-clubs"));
    }

    /**
     * Metodo che testa la funzionalità gestita dal
     * controller per il reinderizzamento alla modifica
     * dei dati di un club del libro
     * simulando la richiesta http.
     * @param club Un club per simulare la modifica
     * @throws Exception Eccezione per MovkMvc
     */
    @ParameterizedTest
    @MethodSource("provideClubDelLibro")
    public void visualizzaModificaDatiClub(final ClubDelLibro club) throws Exception {
        when(clubService.getClubByID(1)).thenReturn(club);
        this.mockMvc.perform(get("/club-del-libro/modifica-dati/1"))
                .andExpect(model().attribute("club", club))
                .andExpect(view().name("modifica-club"));
    }

    /**
     * Metodo che testa la funzionalità gestita dal
     * controller per la modifica dei dati di un club
     * simulando la richiesta http.
     * @param club Il club da modificare
     * @throws Exception Eccezione per MovkMvc
     */
    @ParameterizedTest
    @MethodSource("provideClubDelLibro")
    public void modificaDatiClub(final ClubDelLibro club) throws Exception {

        String[] nomiGeneri = {"A", "B"};
        MockMultipartFile copertina =
                new MockMultipartFile("copertina",
                        "filename.png",
                        "image/png",
                        "immagine di copertina".getBytes());
        when(clubService.getClubByID(club.getIdClub())).thenReturn(club);
        when(clubService.getGeneri(Arrays.asList(nomiGeneri))).thenReturn(new ArrayList<>());
        this.mockMvc.perform(MockMvcRequestBuilders
                .multipart("/club-del-libro/modifica-dati")
                .file(copertina)
                .param("idClub", String.valueOf(club.getIdClub()))
                .param("nome", club.getNome())
                .param("descrizione", club.getDescrizione())
                .param("generi", nomiGeneri))
                .andExpect(view().name("redirect:/club-del-libro/"));
    }

    /**
     * Simula i dati inviati da un metodo
     * http attraverso uno stream.
     * @return Lo stream di dati.
     */
    private static Stream<Arguments> provideClubDelLibro() {
        return Stream.of(
                Arguments.of(new ClubDelLibro("Club1",
                        "descrizione1",
                        new Esperto("drink@home.com",
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
                                    "Vieni che non ti faccio niente"))))
        );
    }

}
