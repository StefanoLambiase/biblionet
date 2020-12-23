package it.unisa.c07.biblionet.registrazione.service;


import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.dao.utente.EspertoDAO;
import it.unisa.c07.biblionet.model.dao.utente.LettoreDAO;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
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
public class RegistrazioneServiceImplTest {

    @InjectMocks
    private RegistrazioneServiceImpl registrazioneService;

    @Mock
    private EspertoDAO espertoDAO;

    @Mock
    private BibliotecaDAO bibliotecaDAO;

    @Mock
    private LettoreDAO lettoreDAO;

    @Test
    public void registraLettore() {
        Lettore lettore = new Lettore();
        when(lettoreDAO.save(lettore)).thenReturn(lettore);
        assertEquals(lettore, registrazioneService.registraLettore(lettore));
    }

    @Test
    public void registraEsperto() {
        Esperto esperto = new Esperto();
        when(espertoDAO.save(esperto)).thenReturn(esperto);
        assertEquals(esperto, registrazioneService.registraEsperto(esperto));
    }

    @Test
    public void registraBiblioteca() {
        Biblioteca biblioteca = new Biblioteca();
        when(bibliotecaDAO.save(biblioteca)).thenReturn(biblioteca);
        assertEquals(biblioteca, registrazioneService.registraBiblioteca(biblioteca));
    }

}
