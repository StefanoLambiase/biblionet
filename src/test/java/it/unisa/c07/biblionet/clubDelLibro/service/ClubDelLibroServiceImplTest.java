package it.unisa.c07.biblionet.clubDelLibro.service;

import it.unisa.c07.biblionet.model.dao.ClubDelLibroDAO;
import it.unisa.c07.biblionet.model.dao.GenereDAO;
import it.unisa.c07.biblionet.model.dao.utente.LettoreDAO;
import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Implementa il testing di unità per la classe
 * ClubDelLibroServiceImpl.
 * @author Viviana Pentangelo, Gianmario Voria
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ClubDelLibroServiceImplTest {

    /**
     * Inject del service per simulare
     * le operazioni del service.
     */
    @InjectMocks
    private ClubDelLibroServiceImpl clubService;

    /**
     * Mocking del dao per simulare le
     * CRUD su clubDelLibro.
     */
    @Mock
    private ClubDelLibroDAO clubDAO;

    /**
     * Mocking del dao per simulare le
     * CRUD su genere.
     */
    @Mock
    private GenereDAO genereDAO;

    /**
     * Mocking del dao per simulare le
     * CRUD su lettore.
     */
    @Mock
    private LettoreDAO lettoreDAO;

    /**
     * Implementa il test della funzionalità di
     * creazione  di un club del libro in service.
     */
    @Test
    public void creaClubDelLibro() {
        ClubDelLibro club = new ClubDelLibro();
        when(clubDAO.save(club)).thenReturn(club);
        assertEquals(club, clubService.creaClubDelLibro(club));
    }

    /**
     * Implementa il test della funzionalità
     * di recupero di
     * tutti i club del libro in service.
     */
    @Test
    public void visualizzaClubsDelLibro() {
        ClubDelLibro club = new ClubDelLibro();
        List<ClubDelLibro> list = new ArrayList<>();
        list.add(club);
        when(clubDAO.findAll()).thenReturn(list);
        assertEquals(list, clubService.visualizzaClubsDelLibro());
    }

    /**
     * Implementa il test della funzionalità di selezione
     * di una lista di generi dato il loro nome in service.
     */
    @Test
    public void getGeneri() {
        List<Genere> listaGeneri = new ArrayList<>();
        List<String> nomi = new ArrayList<>();
        when(genereDAO.findByName("")).thenReturn(new Genere());
        assertEquals(listaGeneri, clubService.getGeneri(nomi));
    }

    /**
     * Implementa il test della funzionalità di
     * modifica dei dati
     * di un club del libro in service.
     */
    @Test
    public void modificaDatiClub() {
        ClubDelLibro club = new ClubDelLibro();
        when(clubDAO.save(club)).thenReturn(club);
        assertEquals(club, clubService.modificaDatiClub(club));
    }

    /**
     * Implementa il test della funzionalità di select di un
     * club dato il suo ID in service.
     */
    @Test
    public void getClubByID() {
        ClubDelLibro club = new ClubDelLibro();
        when(clubDAO.findById(1)).thenReturn(java.util.Optional.of(club));
        assertEquals(club, clubService.getClubByID(1));
    }

    /**
     * Implementa il test della funzionalità di iscrizione
     * di un lettore ad un club del libro in service.
     */
    @Test
    public void partecipaClub() {
        Lettore l = new Lettore();
        when(lettoreDAO.save(l)).thenReturn(l);
        assertEquals(true, clubService.partecipaClub(new ClubDelLibro(), l));
    }


}
