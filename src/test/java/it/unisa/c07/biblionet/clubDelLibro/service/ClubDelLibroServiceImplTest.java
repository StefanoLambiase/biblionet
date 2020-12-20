package it.unisa.c07.biblionet.clubDelLibro.service;

import it.unisa.c07.biblionet.model.dao.ClubDelLibroDAO;
import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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


}
