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
     * Metodo che si occupa di testare
     * la funzione di login nel service.
     */
    @Test
    public void login() {
        byte[] password = new byte[0];

        Lettore lettore = new Lettore();
        when(lettoreDAO.findByEmailAndPassword("",  password)).thenReturn(lettore);
        assertEquals(null, autenticazioneService.login("", ""));

        Esperto esperto = new Esperto();
        when(espertoDAO.findByEmailAndPassword("", password)).thenReturn(esperto);
        assertEquals(null, autenticazioneService.login("", ""));

        Biblioteca biblioteca = new Biblioteca();
        when(bibliotecaDAO.findByEmailAndPassword("", password)).thenReturn(biblioteca);
        assertEquals(null, autenticazioneService.login("", ""));
    }
}
