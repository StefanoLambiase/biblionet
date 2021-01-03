package it.unisa.c07.biblionet.preferenzeDiLettura.service;

import it.unisa.c07.biblionet.model.dao.GenereDAO;
import it.unisa.c07.biblionet.model.entity.Genere;
import org.junit.jupiter.api.Test;
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
 * @author Alessio Casolaro
 * @author Antonio Della Porta
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PreferenzeDiLetturaServiceTest {


    /**
     * Inject del service per simulare le operazioni.
     */
    @InjectMocks
    private PreferenzeDiLetturaServiceImpl preferenzeDiLetturaService;

    /**
     * Mock per le operazioni CRUD.
     */
    @Mock
    private GenereDAO genereDAO;


    /**
     * Metodo che si occupa di testare
     * la funzione di ricerca di un
     * genere facendo 0 iterazioni nel service.
     */
    @Test
    public void getGeneriByName0IT() {
        List<Genere> list = new ArrayList<>();
        String[] generi = {""};
        assertEquals(list, preferenzeDiLetturaService.getGeneriByName(generi));
    }

    /**
     * Metodo che si occupa di testare
     * la funzione di ricerca di un
     * genere facendo una iterazione nel service.
     */
    @Test
    public void getGeneriByName1IT() {

        List<Genere> list = new ArrayList<>();
        list.add(new Genere());
        String[] generi = {"test"};
        when(genereDAO.findByName("test")).thenReturn(new Genere());
        assertEquals(list, preferenzeDiLetturaService.getGeneriByName(generi));
    }

    /**
     * Metodo che si occupa di testare
     * la funzione di ricerca di un
     * genere facendo due iterazioni nel service.
     */
    @Test
    public void getGeneriByName2IT() {

        List<Genere> list = new ArrayList<>();
        list.add(new Genere());
        list.add(new Genere());
        String[] generi = {"test", "test2"};
        when(genereDAO.findByName("test")).thenReturn(new Genere());
        when(genereDAO.findByName("test2")).thenReturn(new Genere());
        assertEquals(list, preferenzeDiLetturaService.getGeneriByName(generi));
    }

    /**
     * Metodo che si occupa di testare
     * la funzione di ricerca di un genere
     * facendo fallire l'if nel service.
     */
    @Test
    public void getGeneriByName() {

        List<Genere> list = new ArrayList<>();
        String[] generi = {"test"};
        when(genereDAO.findByName("test")).thenReturn(null);
        assertEquals(list, preferenzeDiLetturaService.getGeneriByName(generi));
    }

    /**
     * Metodo che si occupa di testare
     * la funzione di ricerca di un genere
     * facendo riuscire l'if nel service.
     */
    @Test
    public void getGeneriByName2() {

        List<Genere> list = new ArrayList<>();
        list.add(new Genere());
        String[] generi = {"test"};
        when(genereDAO.findByName("test")).thenReturn(new Genere());
        assertEquals(list, preferenzeDiLetturaService.getGeneriByName(generi));
    }

}
