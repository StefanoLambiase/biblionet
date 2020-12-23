package it.unisa.c07.biblionet.registrazione.service;

import it.unisa.c07.biblionet.model.dao.utente.LettoreDAO;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Alessio Casolaro, Antonio Della Porta
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RegistrazioneServiceImplTest {

    /**
     * Inject del service per simulare
     * le operazioni.
     */
    @InjectMocks
    private RegistrazioneServiceImpl registrazioneService;

    /**
     * Mocking del dao per simulare le
     * CRUD.
     */
    @Mock
    private LettoreDAO lettoreDAO;

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

}
