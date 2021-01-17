package it.unisa.c07.biblionet.gestioneEventi.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import it.unisa.c07.biblionet.model.dao.utente.LettoreDAO;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.unisa.c07.biblionet.model.dao.EventoDAO;
import it.unisa.c07.biblionet.model.entity.Evento;

/**
 * Implementa il testing di unità per la classe GestioneEventiServiceImpl.
 *
 * @author Nicola Pagliara
 * @author Luca Topo
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GestioneEventiServiceImplTest {

    /**
     * Servizio di Gestione Eventi, ossia la classe da testare. Vengono iniettati i
     * mock tramite InjectMocks.
     */
    @InjectMocks
    private GestioneEventiServiceImpl gestioneEventi;

    /**
     * Mocking del DAO per simulare le CRUD su Evento.
     */
    @Mock
    private EventoDAO eventoDAO;

    /**
     * Mocking del DAO per simulare le CRUD su Lettore.
     */
    @Mock
    private LettoreDAO lettoreDAO;

    /**
     * Prepara i mock bindando le chiamate tramite Mockito.
     */
    @BeforeAll
    public void preparaMock() {
        Mockito.doAnswer(
            AdditionalAnswers.returnsFirstArg()
        ).when(eventoDAO).save(
            Mockito.any(Evento.class)
        );

        Mockito.when(
            eventoDAO.findById(Mockito.anyInt())
        ).thenAnswer(
            invocazione -> {
                var evento = new Evento();
                evento.setIdEvento((int) invocazione.getArgument(0));
                return Optional.of(evento);
            }
        );
    }

    /**
     * Implementa il test della funzionalità di creazione
     * di un evento.
     */
    @Test
    public void creaEvento() {
        var evento = new Evento();
        evento.setIdEvento(896);

        var eventoCreato = gestioneEventi.creaEvento(evento);

        assertEquals(
            "La funzione dovrebbe restituire l'evento inserito",
            evento,
            eventoCreato
        );
    }

    /**
     * Implementa il test della funzionalità di eliminazione
     * di un evento.
     */
    @Test
    public void eliminaEvento() {
        var evento = new Evento();
        evento.setIdEvento(1);

        var eventoEliminato = this.gestioneEventi.eliminaEvento(1);

        assertTrue(eventoEliminato.isPresent());
        assertEquals(evento, eventoEliminato.get());
    }

    /**
     * Implementa il test della funzionalità di
     * partecipare ad un Evento.
     */
    @ParameterizedTest
    @MethodSource("provideLettore")
    public void partecipaEvento(Lettore lettore) {
        Evento evento = new Evento();
        Mockito.when(eventoDAO.getOne(1)).thenReturn(evento);
        Mockito.when(lettoreDAO.findByID("a")).thenReturn(lettore);
        Mockito.when(lettoreDAO.save(lettore)).thenReturn(lettore);

        assertEquals(gestioneEventi.partecipaEvento("a", 1), lettore);
    }

    /**
     * Implementa il test della funzionalità di
     * partecipare ad un Evento.
     */
    @ParameterizedTest
    @MethodSource("provideLettore")
    public void partecipaEventoNonNull(Lettore lettore) {
        Evento evento = new Evento();
        List<Evento> list = new ArrayList<>();
        list.add(evento);
        lettore.setEventi(list);

        Mockito.when(eventoDAO.getOne(1)).thenReturn(evento);
        Mockito.when(lettoreDAO.findByID("a")).thenReturn(lettore);
        Mockito.when(lettoreDAO.save(lettore)).thenReturn(lettore);

        assertEquals(gestioneEventi.partecipaEvento("a", 1), lettore);
    }

    /**
     * Implementa il test della funzionalità di
     * abbandonare un Evento.
     */
    @ParameterizedTest
    @MethodSource("provideLettore")
    public void abbandonaEventoSize0(Lettore lettore) {
        Evento evento = new Evento();
        lettore.setEventi(new ArrayList<>());

        Mockito.when(eventoDAO.getOne(1)).thenReturn(evento);
        Mockito.when(lettoreDAO.findByID("a")).thenReturn(lettore);
        Mockito.when(lettoreDAO.save(lettore)).thenReturn(lettore);

        assertEquals(gestioneEventi.abbandonaEvento("a", 1), lettore);

    }

    /**
     * Implementa il test della funzionalità di
     * abbandonare un Evento.
     */
    @ParameterizedTest
    @MethodSource("provideLettore")
    public void abbandonaEvento(Lettore lettore) {
        Evento evento = new Evento();
        List<Evento> list = new ArrayList<>();
        list.add(evento);
        lettore.setEventi(list);

        Mockito.when(eventoDAO.getOne(1)).thenReturn(evento);
        Mockito.when(lettoreDAO.findByID("a")).thenReturn(lettore);
        Mockito.when(lettoreDAO.save(lettore)).thenReturn(lettore);

        assertEquals(gestioneEventi.abbandonaEvento("a", 1), lettore);

    }

    /**
     * Simula i dati inviati da un metodo
     * http attraverso uno stream.
     * @return Lo stream di dati.
     */
    private static Stream<Arguments> provideLettore() {
        return Stream.of(Arguments.of(new Lettore("giuliociccione@gmail.com",
                "LettorePassword",
                "Salerno",
                "Baronissi",
                "Via Barone 11",
                "3456789012",
                "SuperLettore",
                "Giulio",
                "Ciccione"
        )));
    }

}
