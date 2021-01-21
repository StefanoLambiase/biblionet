package it.unisa.c07.biblionet.prenotazioneLibri.service;

import it.unisa.c07.biblionet.BiblionetApplication;
import it.unisa.c07.biblionet.model.dao.GenereDAO;
import it.unisa.c07.biblionet.model.dao.LibroDAO;
import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.dao.utente.LettoreDAO;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.Libro;
import it.unisa.c07.biblionet.model.entity.TicketPrestito;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.preferenzeDiLettura.service.PreferenzeDiLetturaService;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Implementa l'integration testing del service per il sottosistema
 * Prenotazione Libri.
 * @author Antonio Della Porta
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblionetApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PrenotazioneLibriServiceIntegrationTest {

    @Autowired
    @Setter
    @Getter
    private ApplicationContext applicationContext;

    @Autowired
    private PrenotazioneLibriService prenotazioneLibriService;

    @Autowired
    private GenereDAO genereDAO;

    @Autowired
    private LibroDAO libroDAO;

    @Autowired
    private LettoreDAO lettoreDAO;

    @Autowired
    private BibliotecaDAO bibliotecaDAO;

    @BeforeEach
    public void init() {
        BiblionetApplication.init(applicationContext);
    }

    @Test
    public void getBiblioteca(){
        Biblioteca biblioteca = bibliotecaDAO.findByID("aldomoronocera@gmail.com");
        assertEquals(biblioteca.getNomeBiblioteca(),prenotazioneLibriService.getBibliotecaById("aldomoronocera@gmail.com").getNomeBiblioteca());
    }


}
