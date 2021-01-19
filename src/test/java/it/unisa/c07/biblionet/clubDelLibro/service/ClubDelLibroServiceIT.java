package it.unisa.c07.biblionet.clubDelLibro.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import it.unisa.c07.biblionet.BiblionetApplication;
import it.unisa.c07.biblionet.model.dao.ClubDelLibroDAO;
import it.unisa.c07.biblionet.model.dao.utente.EspertoDAO;
import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import lombok.Getter;
import lombok.Setter;

/**
 * Implementa l'integration testing del service per il sottosistema
 * ClubDelLibro.
 * @author Luca Topo
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblionetApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ClubDelLibroServiceIT implements ApplicationContextAware {
    
    @Autowired
    @Setter
    @Getter
    private ApplicationContext applicationContext;

    @Autowired
    private ClubDelLibroDAO clubDAO;

    @Autowired
    private EspertoDAO espertoDAO;

    @Autowired
    private ClubDelLibroService clubService;

    @BeforeAll
    public void init() {
        BiblionetApplication.init(applicationContext);
    }

    /**
     * Verifica il corretto funzionamento della funzionalità di creazione
     * di un Club del Libro.
     */
    @Test
    public void creaClubDelLibroValido() {
        var esperto = (Esperto) espertoDAO.findById("eliaviviani@gmail.com")
                                          .get();
        var c = new ClubDelLibro(
            "Test",
            "ClubDiProva",
            esperto
        );
        var club = this.clubService.creaClubDelLibro(c);
        assertNotEquals(club, null);
        assertEquals(club.getNome(), c.getNome());
        assertEquals(club.getDescrizione(), c.getDescrizione());
        assertEquals(club.getEsperto(), c.getEsperto());
        var clubInDB = this.clubDAO.findById(club.getIdClub());
        assertFalse(clubInDB.isEmpty());
        assertEquals(club, clubInDB.get());
    }

    /**
     * Verifica il corretto funzionamento della funzionalità di modifica
     * di un Club del Libro.
     */
    @Test
    @Transactional
    public void modificaClubDelLibroValido() {
        var clubBase = clubDAO.findAll().get(1);
        var club = clubDAO.findAll().get(1);
        var id = club.getIdClub();
        club.setNome("Nuovo nome");

        var clubModificato = this.clubService.modificaDatiClub(club);
        var clubInDB = this.clubDAO.findById(id);
        assertTrue(clubInDB.isPresent());

        assertNotEquals(clubBase, clubInDB.get());
        assertEquals(clubModificato, clubInDB.get());
    }

}
