package it.unisa.c07.biblionet.clubDelLibro.controller;

import it.unisa.c07.biblionet.clubDelLibro.service.ClubDelLibroService;
import it.unisa.c07.biblionet.gestioneEventi.service.GestioneEventiService;
import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.Evento;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.Libro;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Implementa il testing di unità per la classe
 * ClubDelLibroController.
 * @author Viviana Pentangelo
 * @author Gianmario Voria
 * @author Nicola Pagliara
 * @author Luca Topo
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
     * Mock del service per simulare
     * le operazioni dei metodi.
     */
    @MockBean
    private GestioneEventiService eventiService;

    /**
     * Inject di MockMvc per simulare
     * le richieste http.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la creazione di un club
     * simulando la richiesta http.
     * @param club Un club per la simulazione
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
                .param("nome", club.getNome())
                .param("descrizione", club.getDescrizione())
                .param("generi", list))
                .andExpect(view().name("redirect:/club-del-libro/"));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la visualizzazione di
     * tutti i club del libro
     * simulando la richiesta http.
     * @param club Un club per la simulazione
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
                .andExpect(view().name("club-del-libro/visualizza-clubs"));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per il reinderizzamento alla modifica
     * dei dati di un club del libro
     * simulando la richiesta http.
     * @param club Un club per la simulazione
     * @throws Exception Eccezione per MovkMvc
     */
    @ParameterizedTest
    @MethodSource("provideClubDelLibro")
    public void visualizzaModificaDatiClub(final ClubDelLibro club)
            throws Exception {
        when(clubService.getClubByID(1)).thenReturn(club);
        this.mockMvc
                .perform(get("/club-del-libro/modifica-dati/1"))
                .andExpect(model().attribute("club", club))
                .andExpect(view().name("club-del-libro/modifica-club"));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la modifica dei dati di un club
     * simulando la richiesta http.
     * @param club Un club per la simulazione
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
        when(clubService.getClubByID(club.getIdClub()))
                .thenReturn(club);
        when(clubService.getGeneri(Arrays.asList(nomiGeneri)))
                .thenReturn(new ArrayList<>());
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
     * Implementa il test della funzionalità gestita dal
     * controller per l'iscrizione di un lettore ad un club
     * simulando la richiesta http.
     * @param club Un club per la simulazione
     * @throws Exception Eccezione per MovkMvc
     */
    @ParameterizedTest
    @MethodSource("provideClubDelLibro")
    public void partecipaClub(final ClubDelLibro club) throws Exception {
        when(clubService.getClubByID(1)).thenReturn(club);
        this.mockMvc
                .perform(get("/club-del-libro/iscrizione-club/1"))
                .andExpect(view().name("redirect:/club-del-libro/"));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per il reinderizzamento alla creazione di
     * un evento simulando la richiesta http.
     * @param club Un club per la simulazione
     * @throws Exception Eccezione per MockMvc
     */
    @ParameterizedTest
    @MethodSource("provideClubDelLibro")
    public void visualizzaCreaEvento(final ClubDelLibro club)
            throws Exception {
        when(clubService.getClubByID(1)).thenReturn(club);
        this.mockMvc
                .perform(get("/club-del-libro/1/crea-evento"))
                .andExpect(model().attribute("club", club))
                .andExpect(model().attributeExists("evento"))
                .andExpect(view().name("club-del-libro/aggiungi-evento"));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la creazione di
     * un evento simulando la richiesta http.
     * @param club Un club per la simulazione
     * @throws Exception Eccezione per MovkMvc
     */
    @ParameterizedTest
    @MethodSource("provideClubDelLibro")
    public void creaEvento(final ClubDelLibro club) throws Exception {
        when(clubService.getClubByID(1)).thenReturn(club);
        when(eventiService.getLibroById(1)).thenReturn(Optional.of(
                new Libro(
                        "Bibbia ebraica. Pentateuco e Haftarot.",
                        "Dario Disegni",
                        "9788880578529",
                        LocalDateTime.of(2020, 1, 1, 0, 0),
                        "La Torah, a cura di Dario Disegni",
                        "Giuntina"
                )
        ));
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/club-del-libro/1/crea-evento")
                        .param("nome", "Prova")
                        .param("descrizione", "Prova")
                        .param("data", "2024-12-12")
                        .param("ora", "11:24")
                        .param("libro", "1"))
                .andExpect(view().name(
                        "redirect:/club-del-libro/1/eventi"
                ));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la visualizzazione dei dati di un club
     * simulando la richiesta http.
     * @param club Un club per la simulazione
     * @throws Exception Eccezione per MovkMvc
     */
    @ParameterizedTest
    @MethodSource("provideClubDelLibro")
    public void visualizzaDatiClub(final ClubDelLibro club) throws Exception {
        when(clubService.getClubByID(1)).thenReturn(club);
        this.mockMvc
                .perform(get("/club-del-libro/visualizza-dati-club/1"))
                .andExpect(model().attribute("club", club))
                .andExpect(view().name("club-del-libro/visualizza-singolo-club"));
    }

    @Test
    public void eliminaEvento() throws Exception {
        when(
                eventiService.eliminaEvento(1)
        ).thenReturn(
                Optional.of(
                        new Evento()
                )
        );

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete(
                        "/club-del-libro/1/eventi/1"
                ))
                .andExpect(view().name("redirect:/club-del-libro/1/eventi"));
    }


    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la visualizzazione di tutti i club
     *  presenti,filtrati per genere, simulando la richiesta http.
     * @param club Un club per la simulazione
     * @throws Exception Eccezione per MovkMvc
     */

    @ParameterizedTest
    @MethodSource("provideClubDelLibro")
    public void visualizzaListaClubsFilterGenre(final ClubDelLibro club) throws  Exception {
        List<ClubDelLibro> list = new ArrayList<>();
        list.add(club);
        when(clubService.visualizzaClubsDelLibro()).thenReturn(list);
        this.mockMvc.perform(get("/club-del-libro/visualizza-clubs")
       .param("generi", String.valueOf(club.getGeneri()))
        .param("città", "")
        .param("ordine", ""))
                .andExpect(model().attributeExists("listaClubs"))
                .andExpect(view().name("club-del-libro/visualizza-clubs"));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la visualizzazione di tutti i club
     *  presenti, filtrati per città, simulando la richiesta http.
     * @param club Un club per la simulazione
     * @throws Exception Eccezione per MovkMvc
     */

    @ParameterizedTest
    @MethodSource("provideClubDelLibro")
    public void visualizzaListaClubsFilterCity(final ClubDelLibro club) throws  Exception {
        List<ClubDelLibro> list = new ArrayList<>();
        List<String> città= new ArrayList<>();
        città.add("Scampia");
        list.add(club);
        when(clubService.visualizzaClubsDelLibro()).thenReturn(list);
        this.mockMvc.perform(get("/club-del-libro/visualizza-clubs")
                .param("generi", "")
                .param("città", String.valueOf(città))
                .param("ordine", ""))
                .andExpect(model().attributeExists("listaClubs"))
                .andExpect(view().name("club-del-libro/visualizza-clubs"));
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


    /*************************** Tests for Exception ******************************/
/**
    * Implementa il test della funzionalità gestita dal
     * controller per la creazione di un evento
     * simulando la richiesta http.
     * @throws Exception Eccezione per MovkMvc
     */
    @Test
    public void creaEventoFirstException() throws Exception {
        when(clubService.getClubByID(1)).thenReturn(null);          // Mock che consente di entrare nella prima condizione e lanciare l'eccezione

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/club-del-libro/1/crea-evento")
                        .param("nome", "Prova")
                        .param("descrizione", "Prova")
                        .param("data", "2024-12-12")
                        .param("ora", "11:24")
                        .param("libro", "1"))
                .andExpect(status().isBadRequest())             // Verifica lo status della eccezione
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof ResponseStatusException))       // Verifica la classe del lancio della eccezione
                .andExpect(result ->
                        assertEquals("400 BAD_REQUEST \"Club del Libro Inesistente\"", result.getResolvedException().getMessage()));        // Verifica il messaggio di ritorno della eccezione
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la creazione di un evento,
     * simulando la richiesta http.
     * @param club Un club per la simulazione
     * @throws Exception Eccezione per MovkMvc
     */
    /* Non possibile il mocking poiche il metodo è statico */
    @ParameterizedTest
    @MethodSource("provideClubDelLibro")
    public void creaEventoSecondExcpetion(final ClubDelLibro club) throws Exception {
            when(clubService.getClubByID(1)).thenReturn(club);

            this.mockMvc.perform(MockMvcRequestBuilders.post("/club-del-libro/1/crea-evento")
                     .param("nome", "Discussione sopra i due massimi sistemi")
                .param("descrizione", "TestDescrizione")
                    .param("data", "2024-11-11")
                       .param("ora", "12:24")
                        .param("libro", "2"))
                        .andExpect(status().isBadRequest())
                        .andExpect(result ->
                                assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                        .andExpect(result ->
                                assertEquals("400 BAD_REQUEST \"Lunghezza del nome non valida.\"", result.getResolvedException().getMessage()));

    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la creazione di un evento,
     * simulando la richiesta http.
     * @param club Un club per la simulazione
     * @throws Exception Eccezione per MovkMvc
     */
    @ParameterizedTest
    @MethodSource("provideClubDelLibro")
    public void creaEventoThridException(final ClubDelLibro club) throws Exception {
                when(clubService.getClubByID(1)).thenReturn(club);

                this.mockMvc.perform(MockMvcRequestBuilders.post("/club-del-libro/1/crea-evento")
                .param("nome", "TestNome")
                .param("descrizione", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum")
                .param("data", "2024-08-11")
                .param("ora", "13:24")
                .param("libro", "3"))
                .andExpect(status().isBadRequest())
                 .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                 .andExpect(result ->
                         assertEquals("400 BAD_REQUEST \"Lunghezza della descrizione non valida.\"", result.getResolvedException().getMessage()));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la creazione di un evento
     * simulando la richiesta http.
     * @param club Un club per la simulazione
     * @throws Exception Eccezione per MovkMvc
     */
    @ParameterizedTest
    @MethodSource("provideClubDelLibro")
    public void creaEventoFourthException(final ClubDelLibro club) throws Exception {
                    when(clubService.getClubByID(1)).thenReturn(club);
                    this.mockMvc.perform(MockMvcRequestBuilders.post("/club-del-libro/1/crea-evento")
                    .param("nome", "TestNome")
                    .param("descrizione", "TestDescrizione")
                    .param("data", "1985-11-10")
                    .param("ora", "14:24")
                    .param("libro", "4"))
                            .andExpect(status().isBadRequest())
                            .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                            .andExpect(result ->
                                    assertEquals("400 BAD_REQUEST \"Ora inserita non valida.\"", result.getResolvedException().getMessage()));
    }


    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la creazione di un evento
     * simulando la richiesta http.
     * @param club Un club per la simulazione
     * @throws Exception Eccezione per MovkMvc
     */
   @ParameterizedTest
   @MethodSource("provideClubDelLibro")
    public void creaEventoFiveException(final ClubDelLibro club) throws Exception {
                when(clubService.getClubByID(1)).thenReturn(club);
                when(eventiService.getLibroById(5)).thenReturn(Optional.empty());
                this.mockMvc.perform(MockMvcRequestBuilders.post("/club-del-libro/1/crea-evento")
                    .param("nome", "TestNome")
                    .param("descrizione", "TestDescrizione")
                    .param("data", "2024-09-11")
                    .param("ora", "15:24")
                    .param("libro", "5"))
                        .andExpect(status().isBadRequest())
                        .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                        .andExpect(result ->
                                assertEquals("400 BAD_REQUEST \"Il libro inserito non è valido.\"", result.getResolvedException().getMessage()));

   }


    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la visualizzazione della creazione di un evento
     * simulando la richiesta http.
     * @throws Exception Eccezione per MovkMvc
     */

    @Test
    public void visualizzaCreaEventoFirstException() throws Exception {
        when(clubService.getClubByID(1)).thenReturn(null);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/club-del-libro/1/crea-evento")
                .param("nome", "TestNome")
                .param("descrizione", "TestDescrizione")
                .param("data", "2024-11-04")
                .param("ora", "16:24")
                .param("libro", "1"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result ->
                        assertEquals("404 NOT_FOUND \"Club del Libro Inesistente\"", result.getResolvedException().getMessage()));

    }



    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la eliminazione di un evento
     * simulando la richiesta http.
     * @throws Exception Eccezione per MovkMvc
     */
    @Test
    public void eliminaEventoFirstException() throws Exception {
        when(eventiService.eliminaEvento(1)).thenReturn(Optional.empty());
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/club-del-libro/1/eventi/1")
                .param("nome", "TestNome")
                .param("descrizione", "TestDescrizione")
                .param("data", "2024-07-06")
                .param("ora", "17:24")
                .param("libro", "2"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result ->
                        assertEquals("404 NOT_FOUND \"Evento Inesistente\"", result.getResolvedException().getMessage()));

    }



}
