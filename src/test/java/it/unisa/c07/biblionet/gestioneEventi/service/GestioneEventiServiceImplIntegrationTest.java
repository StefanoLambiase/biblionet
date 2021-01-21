package it.unisa.c07.biblionet.gestioneEventi.service;

import it.unisa.c07.biblionet.BiblionetApplication;
import it.unisa.c07.biblionet.comunicazioneEsperto.service.ComunicazioneEspertoService;
import it.unisa.c07.biblionet.model.entity.Evento;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Implementa l'integration testing del service per il sottosistema
 * Gestione Eventi.
 * @author Antonio Della Porta
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblionetApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GestioneEventiServiceImplIntegrationTest {

    @Autowired
    @Setter
    @Getter
    private ApplicationContext applicationContext;

    @Autowired
    private GestioneEventiService gestioneEventiService;

    @BeforeEach
    public void init() {
        BiblionetApplication.init(applicationContext);
    }

    @Test
    public void modificaEventoIntegrationTest(){
        Optional<Evento> evento = gestioneEventiService.getEventoById(61);
        assertEquals(evento,gestioneEventiService.modificaEvento(evento.get()));
    }

    @Test
    public void modificaEventoFaultIntegrationTest(){
        Evento evento = new Evento();
        evento.setIdEvento(1);
        assertEquals(Optional.empty(),gestioneEventiService.modificaEvento(evento));
    }



}
