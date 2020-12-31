package it.unisa.c07.biblionet.autenticazione.service;

import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.dao.utente.EspertoDAO;
import it.unisa.c07.biblionet.model.dao.utente.LettoreDAO;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
     * @throws NoSuchAlgorithmException L'eccezione che può essere lanciata
     * dal metodo getInstance().
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
     * @throws NoSuchAlgorithmException L'eccezione che può
     * essere lanciata dal metodo getInstance().
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
     * @throws NoSuchAlgorithmException L'eccezione che può
     * essere lanciata dal metodo getInstance().
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

    /**
     * Metodo che si occupa di testare
     * se l'utente è un esperto.
     */
    @Test
    public void isEsperto() {
        UtenteRegistrato utenteRegistrato = new Esperto();

        when(autenticazioneService.isEsperto(utenteRegistrato))
                .thenReturn(true);

        assertTrue(autenticazioneService.isEsperto(utenteRegistrato));
    }

    /**
     * Metodo che si occupa di testare
     * se l'utente è un lettore.
     */
    @Test
    public void isLettore() {
        UtenteRegistrato utenteRegistrato = new Lettore();

        when(autenticazioneService.isLettore(utenteRegistrato))
                .thenReturn(true);

        assertTrue(autenticazioneService.isLettore(utenteRegistrato));
    }

    /**
     * Metodo che si occupa di testare
     * la funzione di aggiornamento una
     * biblioteca nel service.
     */
   @Test
    public void aggiornaBiblioteca() {
        Biblioteca utente = new Biblioteca();
        when(bibliotecaDAO.save(utente))
                .thenReturn(utente);
        assertEquals(utente, autenticazioneService.aggiornaBiblioteca(utente));
    }

    /**
     * Metodo che si occupa di testare
     * la funzione di aggiornamento un
     * esperto nel service.
     */
    @Test
    public void aggiornaEsperto() {
        Esperto utente = new Esperto();
        when(espertoDAO.save(utente))
                .thenReturn(utente);
        assertEquals(utente, autenticazioneService.aggiornaEsperto(utente));
    }

    /**
     * Metodo che si occupa di testare
     * la funzione di aggiornamento un
     * lettore nel service.
     */
    @Test
    public void aggiornaLettore() {
        Lettore utente = new Lettore();
        when(lettoreDAO.save(utente))
                .thenReturn(utente);
        assertEquals(utente, autenticazioneService.aggiornaLettore(utente));
    }

    /**
     * Metodo che si occupa di testare
     * la funzione di ricerca di un Esperto
     * nel service.
     */
    @Test
    public void findEspertoByEmail() {
        Esperto dummy = new Esperto();
        String email = "";
        when(espertoDAO.findById(email))
                .thenReturn(Optional.of(dummy));
        assertEquals(dummy, autenticazioneService.findEspertoByEmail(email));
    }

    /**
     * Metodo che si occupa di testare
     * la funzione di ricerca di un
     * Lettore nel service.
     */
    @Test
    public void findLettoreByEmail() {
        Lettore dummy = new Lettore();
        String email = "";
        when(lettoreDAO.findById(email))
                .thenReturn(Optional.of(dummy));
        assertEquals(dummy, autenticazioneService.findLettoreByEmail(email));
    }
}
