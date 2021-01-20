package it.unisa.c07.biblionet.clubDelLibro.controller;

import it.unisa.c07.biblionet.clubDelLibro.service.ClubDelLibroService;
import it.unisa.c07.biblionet.gestioneEventi.service.GestioneEventiService;
import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.Evento;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.Libro;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import it.unisa.c07.biblionet.model.form.ClubForm;
import it.unisa.c07.biblionet.model.form.EventoForm;
import org.hibernate.query.criteria.internal.path.SetAttributeJoin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.nullable;
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




    /**************************Tests for visualizzaModificaEvento*************************/

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per visualizzazione di modifica di un
     *  evento simulando la richiesta http.
     * @param clubDelLibro Un club per la simulazione
     * @throws Exception Eccezione per MovkMvc
     */
        @ParameterizedTest
        @MethodSource({"provideClubDelLibro"})
        public void visualizzaModificaEventoTest(final ClubDelLibro clubDelLibro) throws Exception {
            // Creo  attributto Evento
            Evento evento=new Evento();
            evento.setNomeEvento("Evento1");
            evento.setDataOra(LocalDateTime.now());
            evento.setDescrizione("Descrizione Evento1");
            Libro libro = new Libro();
            libro.setIdLibro(1);
            evento.setLibro(libro);
            evento.setClub(clubDelLibro);
            evento.setIdEvento(1);
            //Creo  attributo l'evento Form
            EventoForm eventoForm= new EventoForm();
            eventoForm.setNome(evento.getNomeEvento());
            eventoForm.setDescrizione(evento.getDescrizione());
            eventoForm.setData(evento.getDataOra().toLocalDate());
            eventoForm.setOra(evento.getDataOra().toLocalTime());
            eventoForm.setLibro(evento.getLibro().getIdLibro());
                // setto clubdellibro
                clubDelLibro.setIdClub(1);
                // Mocking
                    when(eventiService.getEventoById(1)).thenReturn(Optional.of((evento)));
                    // Assert del test
                    this.mockMvc.perform(MockMvcRequestBuilders.get("/club-del-libro/1/eventi/1/modifica")
                    .param("idClub", "1")
                    .param("idEvento","1")
                    .sessionAttr("loggedUser",clubDelLibro.getEsperto()))
                            .andExpect(model().attribute("loggedUser", clubDelLibro.getEsperto()))
                        .andExpect(model().attribute("evento",eventoForm))
                        .andExpect(model().attribute("club",evento.getClub()))
                        .andExpect(model().attribute("id",1))
                    .andExpect(view().name("club-del-libro/modifica-evento"));
        }

    /***********************++Tests for creaClubDelLibro*******************/

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
        // Creo l'attributo per la sessione
        UtenteRegistrato utente = new Esperto();
        // Creo l'attributo per la copertina del Club
        MockMultipartFile copertina =
                new MockMultipartFile("copertina",
                        "filename.png",
                        "image/png",
                        "immagine di copertina".getBytes());
        // Mocking
        when(clubService.getGeneri(Arrays.asList(list.clone())))
                .thenReturn(new ArrayList<>());
        when(clubService.creaClubDelLibro(club)).thenReturn(club);
        // Assert del test
        this.mockMvc.perform(MockMvcRequestBuilders
                    .multipart("/club-del-libro/crea")
                    .file(copertina)
                    .sessionAttr("loggedUser", utente)
                    .param("nome", club.getNome())
                    .param("descrizione", club.getDescrizione())
                    .param("generi", list))
                .andExpect(view().name("redirect:/club-del-libro/"));
    }



    /**************** Tests for visualizzaCreaClubDelLibro************++***/
            @Test
            public void visualizzaCreaClubDelLibroTest() throws Exception{
                                UtenteRegistrato esperto= new Esperto();
                                ClubForm clubForm = new ClubForm();
                                Set<String> generes= new HashSet<String>();
                                 when(clubService.getTuttiGeneri()).thenReturn(generes);
                                 this.mockMvc.perform(MockMvcRequestBuilders.get("/club-del-libro/crea")
                                 .param("id","1")
                                 .sessionAttr("loggedUser", esperto))
                                         .andExpect(model().attribute("club",clubForm))
                                         .andExpect(model().attribute("generi",generes))
                                         .andExpect(view().name("club-del-libro/creazione-club"));
            }



            /********************* Tests for visualizzaListaEventiClub ***************/


    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la visualizzazione di una lista eventi
     *  di un club simulando la richiesta http.
     * @param club Un club per la simulazione
     * @throws Exception Eccezione per MovkMvc
     */

            @ParameterizedTest
            @MethodSource("provideClubDelLibro")
            public void visualizzaListaEventiClubTest(final ClubDelLibro club) throws Exception{
                                List<Evento> eventi_club= new ArrayList<>();
                                List<Evento> eventi_lettore = new ArrayList<>();
                                club.setIdClub(1);
                                Evento evento = new Evento();
                                Evento evento1 = new Evento();
                                evento.setClub(club);
                                evento1.setClub(club);
                                evento.setIdEvento(1);
                                evento1.setIdEvento(2);
                                eventi_club.add(evento);
                                eventi_club.add(evento1);
                                eventi_lettore.add(evento);
                                club.setEventi(eventi_club);
                                Lettore lettore = new Lettore();
                                lettore.setEventi(eventi_lettore);
                                when(clubService.getClubByID(1)).thenReturn(club);
                                this.mockMvc.perform(MockMvcRequestBuilders.get("/club-del-libro/1/eventi")
                                .param("idClub","1")
                                .sessionAttr("loggedUser", lettore))
                                        .andExpect(model().attribute("club",club))
                                        .andExpect(model().attribute("eventi",club.getEventi()))
                                        .andExpect(model().attribute("mieiEventi", lettore.getEventi()))
                                        .andExpect(view().name("club-del-libro/visualizza-eventi"));

            }


   /********************************** Tests fot visualizzaModificaDatiClub **********************************/

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
        // Creo l'attributo per la sessione
        UtenteRegistrato utente = club.getEsperto();
        // Creo l'attributo per il genere
        Genere genere = new Genere("Fantasy", "Fantasy");
        List<Genere> genereList = new ArrayList<>();
        genereList.add(genere);
        club.setGeneri(genereList);
        // Creo l'attributo form
        ClubForm clubForm =
                new ClubForm(
                        club.getNome(),
                        club.getDescrizione(),
                        club.getGeneri().stream().map(Genere::getNome).collect(
                                Collectors.toList())
                );
        // Mocking
        when(clubService.getClubByID(1)).thenReturn(club);
        Set<String> set = new HashSet<String>();
        set.add("Fantasy");
        when(clubService.getTuttiGeneri()).thenReturn(set);
        // Test
        this.mockMvc
                .perform(get("/club-del-libro/1/modifica")
                        .param("id", "1")
                        .sessionAttr("loggedUser", utente))
                .andExpect(model().attribute("club", clubForm))
                .andExpect(model().attribute("id", 1))
                .andExpect(model().attribute("generi", set))
                .andExpect(view().name("club-del-libro/modifica-club"));
    }

        /******************************************* Tests for partecipaClub ***************************/
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
        // Creo l'attributo per la sessione
        UtenteRegistrato utente = new Lettore();
        utente.setTipo("Lettore");
        club.setLettori(new ArrayList<>());
        // Mocking
        when(clubService.getClubByID(1)).thenReturn(club);
        this.mockMvc
                .perform(MockMvcRequestBuilders
                .multipart("/club-del-libro/1/iscrizione")
                .param("id", "1")
                .sessionAttr("loggedUser", utente))
            .andExpect(view().name("redirect:/club-del-libro/"));
    }

    /************************************** Tests for visualizzaCreaEvento ****************************/

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
        // Vars
        EventoForm eventoForm = new EventoForm();
        // Mocking
        when(clubService.getClubByID(1)).thenReturn(club);
        // Testing
        this.mockMvc
                .perform(get("/club-del-libro/1/eventi/crea"))
                .andExpect(model().attribute("club", club))
                .andExpect(model().attribute("evento", eventoForm))
                .andExpect(view().name("club-del-libro/aggiungi-evento"));
    }

    /*
    * TODO: rifare da zero
    *
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

     */
 /******************************* Tests for visualizzaDatiClub ******************************/
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
                .perform(get("/club-del-libro/1")
                .param("id", "1"))
                .andExpect(model().attribute("club", club))
                .andExpect(view().name("club-del-libro/visualizza-singolo-club"));
    }
/*************************************+ Tests for elimnaEvento *********************************/
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
                .perform(MockMvcRequestBuilders.get(
                        "/club-del-libro/1/eventi/1"
                ))
                .andExpect(view().name("redirect:/club-del-libro/1"));
    }

        /***************************** Tests for visualizzaListaClubs *******************************/

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la visualizzazione di tutti i club
     *  presenti, simulando la richiesta http.
     * @param club Un club per la simulazione
     * @throws Exception Eccezione per MovkMvc
     */

    @ParameterizedTest
    @MethodSource("provideClubDelLibro")
    public void visualizzaListaClubsFilterGenre(final ClubDelLibro club) throws  Exception {
        List<ClubDelLibro> list = new ArrayList<>();
        list.add(club);
        List<Genere> list_club_genre = new ArrayList<>();
        Genere genre= new Genere();
        Genere genre1 = new Genere();
        genre.setNome("Fantasy");
        genre1.setNome("Horror");
        genre.setClubs(list);
        genre1.setClubs(list);
        list_club_genre.add(genre);
        list_club_genre.add(genre1);
        club.setGeneri(list_club_genre);
        List<String> generi = new ArrayList<>();
        generi.add(genre.toString());
        generi.add(genre1.toString());
        when(clubService.getGeneri(Optional.of(generi).get())).thenReturn(list_club_genre);
        this.mockMvc.perform(get("/club-del-libro/")
                .param("generi", String.valueOf(generi)))
                .andExpect(model().attributeExists("listaClubs"))
                .andExpect(model().attribute("generi", clubService.getTuttiGeneri()))
                .andExpect(model().attribute("citta", this.clubService.getCitta()))
                .andExpect(view().name("club-del-libro/visualizza-clubs"));

    }



    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la visualizzazione di tutti i club
     *  presenti, simulando la richiesta http.
     * @param club Un club per la simulazione
     * @throws Exception Eccezione per MovkMvc
     */

    @ParameterizedTest
    @MethodSource("provideClubDelLibro")
    public void visualizzaListaClubsFilterCity(final ClubDelLibro club) throws  Exception {
        List<ClubDelLibro> list = new ArrayList<>();
        List<String> città = new ArrayList<>();
        città.add("Salerno");
        list.add(club);
        when(clubService.getCittaFromClubDelLibro(club)).thenReturn(String.valueOf(list));
        this.mockMvc.perform(get("/club-del-libro/")
                .param("città", String.valueOf(città)))
                .andExpect(model().attributeExists("listaClubs"))
                .andExpect(model().attribute("citta", clubService.getCitta()))
                .andExpect(view().name("club-del-libro/visualizza-clubs"));
    }



/******************************************** Tests for partecipaEvento *******************************/

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la partecipazione ad un evento
     * simulando la richiesta http.
     * @throws Exception Eccezione per MovkMvc
     */
    @Test
    public void partecipaEventoIfTrue() throws Exception {
        when(eventiService.partecipaEvento("a", 1)).thenReturn(new Lettore());
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/club-del-libro/1/eventi/15/iscrizione")
                .sessionAttr("loggedUser", new Biblioteca()));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la partecipazione ad un evento
     * simulando la richiesta http.
     * @throws Exception Eccezione per MovkMvc
     */
    @Test
    public void partecipaEventoIfFalse() throws Exception {
        UtenteRegistrato u = new Lettore();
        Lettore lettore = (Lettore) u;
        when(eventiService.partecipaEvento("a", 1)).thenReturn(lettore);
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/club-del-libro/1/eventi/15/iscrizione")
                .sessionAttr("loggedUser", u))
                .andExpect(view().name("redirect:/club-del-libro/1/eventi"));
    }

    /**************************************** Tests for abbandonaEvento ***************************/

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per l'abbandono di un un evento
     * simulando la richiesta http.
     * @throws Exception Eccezione per MovkMvc
     */
    @Test
    public void abbandonaEventoIfTrue() throws Exception {
        when(eventiService.partecipaEvento("a", 1)).thenReturn(new Lettore());
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/club-del-libro/1/eventi/15/abbandono")
                .sessionAttr("loggedUser", new Biblioteca()));
    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per l'abbandono di un un evento
     * simulando la richiesta http.
     * @throws Exception Eccezione per MovkMvc
     */
    @Test
    public void abbandonaEventoIfFalse() throws Exception {
        UtenteRegistrato u = new Lettore();
        Lettore lettore = (Lettore) u;
        when(eventiService.partecipaEvento("a", 1)).thenReturn(lettore);
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/club-del-libro/1/eventi/15/abbandono")
                .sessionAttr("loggedUser", u))
                .andExpect(view().name("redirect:/club-del-libro/1/eventi"));
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
                        .post("/club-del-libro/1/eventi/crea")
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

        this.mockMvc.perform(MockMvcRequestBuilders.post("/club-del-libro/1/eventi/crea")
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

        this.mockMvc.perform(MockMvcRequestBuilders.post("/club-del-libro/1/eventi/crea")
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
        this.mockMvc.perform(MockMvcRequestBuilders.post("/club-del-libro/1/eventi/crea")
                .param("nome", "TestNome")
                .param("descrizione", "TestDescrizione")
                .param("data", "1985-11-10")
                .param("ora", "14:24")
                .param("libro", "4"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result ->
                        assertEquals("400 BAD_REQUEST \"Data non valida.\"", result.getResolvedException().getMessage()));
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
        this.mockMvc.perform(MockMvcRequestBuilders.post("/club-del-libro/1/eventi/crea")
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
        this.mockMvc.perform(MockMvcRequestBuilders.get("/club-del-libro/1/eventi/crea")
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
        this.mockMvc.perform(MockMvcRequestBuilders.get("/club-del-libro/1/eventi/1")
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

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per la visualizzazione di una modifica ad
     *  un evento simulando la richiesta http.
     * @throws Exception Eccezione per MovkMvc
     */
        @Test
    public void visualizzaModificaEventoFirstException() throws Exception {
                // Creo esperto
                    Esperto esperto= new Esperto();
                    // Mocking
                    when(eventiService.getEventoById(1)).thenReturn(Optional.empty());
                    // Assert Test
                    this.mockMvc.perform(MockMvcRequestBuilders.get("/club-del-libro/1/eventi/1/modifica")
                    .param("idClub","1")
                    .param("idEvento", "1")
                     .param("loggedUser", String.valueOf(esperto)))
                            .andExpect(status().isNotFound())
                            .andExpect(result -> assertTrue(result.getResolvedException() instanceof  ResponseStatusException))
                            .andExpect(result ->
                                    assertEquals("404 NOT_FOUND \"Evento Inesistente\"", result.getResolvedException().getMessage()));

        }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per visualizzazione di modifica di un
     *  evento simulando la richiesta http.
     * @param club Un club per la simulazione
     * @throws Exception Eccezione per MovkMvc
     */

    @ParameterizedTest
    @MethodSource("provideClubDelLibro")
    public void visualizzaModificaEventoSecondException(final ClubDelLibro club) throws Exception{
        Evento evento= new Evento();
        evento.setIdEvento(1);    // creare metodo provideEvento errore nel creazione evento
        evento.setClub(club);
        UtenteRegistrato esperto = new Esperto();
        esperto.setEmail("mamix56@gmail.it");
        club.setIdClub(1);
        when(eventiService.getEventoById(1)).thenReturn(Optional.of(evento));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/club-del-libro/1/eventi/1/modifica")
                .param("idClub","1")
                .param("idEvento","1")
                .sessionAttr("loggedUser", esperto))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));

    }

    /**
     * Implementa il test della funzionalità gestita dal
     * controller per visualizzazione di modifica di un
     *  evento simulando la richiesta http.
     * @param club Un club per la simulazione
     * @throws Exception Eccezione per MovkMvc
     */
        @ParameterizedTest
        @MethodSource("provideClubDelLibro")
    public void visualizzaModificaEventoThirdException(final ClubDelLibro club) throws Exception{
                     // Creo Evento da asssociare al club
                    Evento evento = new Evento();
                    evento.setClub(club);

                    // Mocking
                    when(eventiService.getEventoById(1)).thenReturn(Optional.of(evento));
                    //Assert del test
                    this.mockMvc.perform(MockMvcRequestBuilders.get("/club-del-libro/1/eventi/1/modifica")
                                    .param("idClub","1")
                                        .param("idEvento","1")
                                        .param("loggedUser", "null"))
                            .andExpect(status().isBadRequest())
                            .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result ->
                            assertEquals("400 BAD_REQUEST \"L'evento con id 1non è associato al club con id 1.\"",result.getResolvedException().getMessage()));



        }
        @Test
        public void visualizzaCreaClubDelLibroException() throws Exception {
                        UtenteRegistrato registrato = new Lettore();
                    this.mockMvc.perform(MockMvcRequestBuilders.get("/club-del-libro/crea")
                    .sessionAttr("loggedUser",registrato))
                            .andExpect(status().isUnauthorized())
                            .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));

        }


        @Test
    public void partecipaClubFirstException() throws Exception{
                            UtenteRegistrato esperto= new Esperto();
                            this.mockMvc.perform(MockMvcRequestBuilders.post("/club-del-libro/1/iscrizione")
                            .param("id","1")
                            .sessionAttr("loggedUser", esperto))
                                    .andExpect(status().isUnauthorized())
                                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
        }
        @ParameterizedTest
        @MethodSource("provideClubDelLibro")
    public void partecipaClubSecondException(final ClubDelLibro club) throws Exception{
                        Lettore lettore= new Lettore();
                        lettore.setTipo("Lettore");
                        lettore.setEmail("mammi56@gamial.it");
                        lettore.setUsername("MikeM");
                        lettore.setCognome("Mills");
                        lettore.setNome("Mike");
                        List<Lettore> list_lett= new ArrayList<>();
                        list_lett.add(lettore);
                        club.setLettori(list_lett);
                        when(clubService.getClubByID(1)).thenReturn(club);
                        this.mockMvc.perform(MockMvcRequestBuilders.post("/club-del-libro/1/iscrizione")
                        .param("id","1")
                        .sessionAttr("loggedUser", lettore))
                                .andExpect(status().isNotAcceptable())
                                .andExpect(result -> assertTrue(result.getResolvedException() instanceof  ResponseStatusException));

        }

        @Test
    public void visualizzaModificaDatiClubFirstException() throws Exception {
                                when(clubService.getClubByID(1)).thenReturn(null);
                                this.mockMvc.perform(MockMvcRequestBuilders.get("/club-del-libro/1/modifica")
                                .param("id","1")
                                .sessionAttr("loggedUser",new Lettore()))
                                        .andExpect(status().isNotFound())
                                        .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
        }
        @Test
    public void visualizzaModificaDatiClubSecondException() throws Exception{
                    ClubDelLibro club = new ClubDelLibro();
                    Esperto esperto = new Esperto();
                    Esperto esperto1 = new Esperto();
                    esperto.setEmail("patriarca57@outlook.com");
                    esperto1.setEmail("liberale@gmail.com");
                    club.setEsperto(esperto);
                    when(clubService.getClubByID(1)).thenReturn(club);
                    this.mockMvc.perform(MockMvcRequestBuilders.get("/club-del-libro/1/modifica")
                    .param("id","1")
                    .sessionAttr("loggedUser", esperto1))
                            .andExpect(status().isUnauthorized())
                            .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
        }

        @Test
        public void visualizzaListaEventiClubFirstException() throws Exception{
                        when(clubService.getClubByID(1)).thenReturn(null);
                        this.mockMvc.perform(MockMvcRequestBuilders.get("/club-del-libro/1/eventi")
                        .param("id","1")
                        .sessionAttr("loggedUser", "null"))
                                .andExpect(status().isNotFound())
                                .andExpect(result -> assertTrue(result.getResolvedException() instanceof  ResponseStatusException));
        }

        @Test
    public void visualizzaListaEventiClubSecondException() throws Exception{
                        ClubDelLibro club = new ClubDelLibro();
                        club.setIdClub(1);
                        Esperto esperto = new Esperto();
                        when(clubService.getClubByID(1)).thenReturn(club);
                        this.mockMvc.perform(MockMvcRequestBuilders.get("/club-del-libro/1/eventi")
                        .param("id","1")
                        .sessionAttr("loggedUser", esperto))
                                .andExpect(status().isUnauthorized())
                                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));

        }

}
