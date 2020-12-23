package it.unisa.c07.biblionet.autenticazione.service;

import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.dao.utente.EspertoDAO;
import it.unisa.c07.biblionet.model.dao.utente.LettoreDAO;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AutenticazioneServiceImplTest {

    @InjectMocks
    private AutenticazioneServiceImpl autenticazioneService;

    @Mock
    private LettoreDAO lettoreDAO;

    @Mock
    private EspertoDAO espertoDAO;

    @Mock
    private BibliotecaDAO bibliotecaDAO;

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
