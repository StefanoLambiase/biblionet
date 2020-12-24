package it.unisa.c07.biblionet.autenticazione.service;

import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.dao.utente.EspertoDAO;
import it.unisa.c07.biblionet.model.dao.utente.LettoreDAO;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Ciro Maiorino , Giulio Triggiani
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AutenticazioneServiceImplTest {

    /**
     * Inject del service per simulare le operazioni.
     */
    @InjectMocks
    private AutenticazioneServiceImpl autenticazioneService;

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
    private EspertoDAO espertoDAO;
    /**
     * Mocking del dao per simulare le
     * CRUD.
     */
    @Mock
    private BibliotecaDAO bibliotecaDAO;

    /**
     * Implementa il test della
     * funzionalità di login di un lettore
     * nel service.
     */
    @Test
    public void loginLettore() throws NoSuchAlgorithmException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-256");

        String email = "lettore@lettore.com";
        String password = "mipiaccionoglialberi";

        byte[] arr = md.digest(password.getBytes());

        Lettore lettore = new Lettore();

        when(lettoreDAO.findByEmailAndPassword(email,
                                            arr)).thenReturn(lettore);

        assertEquals(lettore, autenticazioneService.login(email,
                                                        password));
    }
    /**
     * Implementa il test della
     * funzionalità di login di una biblioteca
     * nel service.
     */
    @Test
    public void loginBiblioteca() throws NoSuchAlgorithmException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-256");

        String email = "biblioteca@biblioteca.com";
        String password = "mipiacelacarta";

        byte[] arr = md.digest(password.getBytes());

        Biblioteca biblioteca = new Biblioteca();

        when(lettoreDAO.findByEmailAndPassword(email,
                                                arr)).thenReturn(null);

        when(bibliotecaDAO.findByEmailAndPassword(email,
                                                arr)).thenReturn(biblioteca);

        assertEquals(biblioteca, autenticazioneService.login(email,
                                                            password));
    }
    /**
     * Implementa il test della
     * funzionalità di login di un esperto
     * nel service.
     */
    @Test
    public void loginEsperto() throws NoSuchAlgorithmException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-256");

        String email = "esperto@esperto.com";
        String password = "mipiacelaconsocenza";

        byte[] arr = md.digest(password.getBytes());

        Esperto esperto = new Esperto();

        when(lettoreDAO.findByEmailAndPassword(email,
                                            arr)).thenReturn(null);

        when(bibliotecaDAO.findByEmailAndPassword(email,
                                            arr)).thenReturn(null);

        when(espertoDAO.findByEmailAndPassword(email,
                                            arr)).thenReturn(esperto);

        assertEquals(esperto, autenticazioneService.login(email,
                                                        password));
    }
}
