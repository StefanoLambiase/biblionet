package it.unisa.c07.biblionet.registrazione.service;

import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.dao.utente.EspertoDAO;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
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

    @InjectMocks
    private RegistrazioneServiceImpl registrazioneService;

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
        assertEquals(biblioteca, registrazioneService.registraBiblioteca(biblioteca));
    }

}