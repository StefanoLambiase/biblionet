package it.unisa.c07.biblionet.registrazione.service;

import it.unisa.c07.biblionet.autenticazione.service.AutenticazioneServiceImpl;
import it.unisa.c07.biblionet.model.dao.GenereDAO;
import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.dao.utente.EspertoDAO;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.dao.utente.LettoreDAO;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Alessio Casolaro, Antonio Della Porta
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RegistrazioneServiceImplTest {

    /**
     * Si occupa di gestire le operazioni CRUD dell'Esperto.
     */
    @Mock
    private EspertoDAO espertoDAO;

    /**
     * Si occupa di gestire le operazioni CRUD della biblioteca.
     */
    @Mock
    private BibliotecaDAO bibliotecaDAO;

    /**
     * Mocking del dao per simulare le
     * CRUD.
     */
    @Mock
    private LettoreDAO lettoreDAO;

    /**
     * Mocking del dao per simulare le
     * CRUD.
     */
    @Mock
    private GenereDAO genereDAO;

    /**
     * Inject del service per simulare
     * le operazioni.
     */
    @InjectMocks
    private RegistrazioneServiceImpl registrazioneService;

    /**
     * Inject del service per simulare
     * le operazioni.
     */
    @Mock
    private AutenticazioneServiceImpl autenticazioneService;

    /**
     * Testa la funzionalità di registrazione di un Esperto.
     */
    @Test
    public void registraEsperto() {
        Esperto esperto = new Esperto();
        when(espertoDAO.save(esperto)).thenReturn(esperto);
        assertEquals(esperto, registrazioneService.registraEsperto(esperto));
    }

    /**
     * Testa la funzionalità di registrazione di una Biblioteca.
     */
    @Test
    public void registraBiblioteca() {
        Biblioteca biblioteca = new Biblioteca();
        when(bibliotecaDAO.save(biblioteca)).thenReturn(biblioteca);
        assertEquals(biblioteca, registrazioneService.
                registraBiblioteca(biblioteca));
    }

    /**
     * Metodo che si occupa di testare
     * la funzione di registrare un
     * lettore nel service.
     */
    @Test
    public void registraLettore() {
        Lettore lettore = new Lettore();
        when(lettoreDAO.save(lettore)).thenReturn(lettore);
        assertEquals(lettore, registrazioneService.registraLettore(lettore));
    }

    /**
     * Metodo che si occupa di testare
     * la funzione di ricerca di una
     * biblioteca nel service.
     */
    @Test
    public void getBibliotecaByEmail() {
        Biblioteca dummy = new Biblioteca();
        String email = "";
        when(bibliotecaDAO.findById(email))
                .thenReturn(Optional.of(dummy));
        assertEquals(dummy, registrazioneService.getBibliotecaByEmail(email));
    }

    /**
     * Metodo che si occupa di testare
     * la funzione di ricerca di un
     * genere facendo 0 iterazioni nel service.
     */
    @Test
    public void findGeneriByName0IT() {

        List<Genere> list = new ArrayList<>();
        String[] generi = {""};
        assertEquals(list, registrazioneService.findGeneriByName(generi));
    }

    /**
     * Metodo che si occupa di testare
     * la funzione di ricerca di un
     * genere facendo una iterazione nel service.
     */
    @Test
    public void findGeneriByName1IT() {

        List<Genere> list = new ArrayList<>();
        list.add(new Genere());
        String[] generi = {"test"};
        when(genereDAO.findByName("test")).thenReturn(new Genere());
        assertEquals(list, registrazioneService.findGeneriByName(generi));
    }

    /**
     * Metodo che si occupa di testare
     * la funzione di ricerca di un
     * genere facendo due iterazioni nel service.
     */
    @Test
    public void findGeneriByName2IT() {

        List<Genere> list = new ArrayList<>();
        list.add(new Genere());
        list.add(new Genere());
        String[] generi = {"test", "test2"};
        when(genereDAO.findByName("test")).thenReturn(new Genere());
        when(genereDAO.findByName("test2")).thenReturn(new Genere());
        assertEquals(list, registrazioneService.findGeneriByName(generi));
    }

    /**
     * Metodo che si occupa di testare
     * la funzione di ricerca di un genere
     * facendo fallire l'if nel service.
     */
    @Test
    public void findGeneriByName() {

        List<Genere> list = new ArrayList<>();
        String[] generi = {"test"};
        when(genereDAO.findByName("test")).thenReturn(null);
        assertEquals(list, registrazioneService.findGeneriByName(generi));
    }

    /**
     * Metodo che si occupa di testare
     * la funzione di ricerca di un genere
     * facendo riuscire l'if nel service.
     */
    @Test
    public void findGeneriByName2() {

        List<Genere> list = new ArrayList<>();
        list.add(new Genere());
        String[] generi = {"test"};
        when(genereDAO.findByName("test")).thenReturn(new Genere());
        assertEquals(list, registrazioneService.findGeneriByName(generi));
    }



}
