package it.unisa.c07.biblionet.gestioneEventi.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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

}
