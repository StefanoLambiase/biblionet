package it.unisa.c07.biblionet.clubDelLibro.service;

import it.unisa.c07.biblionet.model.dao.ClubDelLibroDAO;
import it.unisa.c07.biblionet.model.dao.GenereDAO;
import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.Genere;
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
 * @author Viviana Pentangelo, Gianmario Voria
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ClubDelLibroServiceImplTest {

    /**
     * Inject del service per simulare
     * le operazioni.
     */
    @InjectMocks
    private ClubDelLibroServiceImpl clubService;

    /**
     * Mocking del dao per simulare le
     * CRUD.
     */
    @Mock
    private ClubDelLibroDAO clubDAO;

    /**
     * Mocking del dao per simulare le
     * CRUD.
     */
    @Mock
    private GenereDAO genereDAO;

    /**
     * Metodo che si occupa di testare
     * la funzione di creazione di un
     * club del libro nel service.
     */
    @Test
    public void creaClubDelLibro() {
        ClubDelLibro club = new ClubDelLibro();
        when(clubDAO.save(club)).thenReturn(club);
        assertEquals(club, clubService.creaClubDelLibro(club));
    }

    /**
     * Metodo che si occupa di testare
     * la funzione di recupero di
     * tutti i club nel service.
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
     * Metodo che si occupa di testare
     * la funzione di select di una
     * lista di generi dato il loro nome.
     */
    @Test
    public void getGeneri() {
        List<Genere> listaGeneri = new ArrayList<Genere>();
        List<String> nomi = new ArrayList<String>();
        when(genereDAO.findByName("")).thenReturn(new Genere());
        assertEquals(listaGeneri, clubService.getGeneri(nomi));
    }


}
