package it.unisa.c07.biblionet.prenotazioneLibri.service;

import it.unisa.c07.biblionet.model.dao.LibroDAO;
import it.unisa.c07.biblionet.model.dao.PossessoDAO;
import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.entity.Libro;
import it.unisa.c07.biblionet.model.entity.Possesso;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Implementa il testing di unità per la classe
 * PrenotazioneLibriServiceImpl.
 * @author Viviana Pentangelo, Gianmario Voria
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PrenotazioneLibriServiceImplTest {

    /**
     * Inject del service per simulare
     * le operazioni del service.
     */
    @InjectMocks
    private PrenotazioneLibriServiceImpl prenotazioneService;

    /**
     * Mocking del dao per simulare le
     * CRUD su libro.
     */
    @Mock
    private LibroDAO libroDAO;

    /**
     * Mocking del dao per simulare le
     * CRUD su biblioteca.
     */
    @Mock
    private BibliotecaDAO bibliotecaDAO;

    /**
     * Mocking del dao per simulare le
     * CRUD su possesso.
     */
    @Mock
    private PossessoDAO possessoDAO;

    /**
     * Implementa il test della funzionalità di
     * selezione di tutti i libri prenotabili.
     */
    @Test
    public void visualizzaListaLibriCompleta() {
        List<Libro> list = new ArrayList<>();
        list.add(new Libro());
        when(libroDAO.findAll(Sort.by("titolo"))).thenReturn(list);
        assertEquals(list, prenotazioneService.visualizzaListaLibriCompleta());
    }

    /**
     * Implementa il test della funzionalità di
     * selezione di tutti i libri prenotabili che contengono
     * una determinata stringa nel titolo.
     */
    @Test
    public void visualizzaListaLibriPerTitolo() {
        List<Libro> list = new ArrayList<>();
        list.add(new Libro());
        when(libroDAO.findByTitoloLike("a")).thenReturn(list);
        assertEquals(list,
                prenotazioneService.visualizzaListaLibriPerTitolo("a"));
    }

    /**
     * Implementa il test della funzionalità di
     * selezione di tutti i libri prenotabili di
     * una determinata biblioteca.
     */
    @Test
    public void visualizzaListaLibriPerBiblioteca() {
        when(bibliotecaDAO.findByNome("nome")).thenReturn(new Biblioteca());
        List<Possesso> listPos = new ArrayList<>();
        when(possessoDAO.findByBibliotecaID("a")).thenReturn(listPos);
        List<Libro> libri = new ArrayList<>();
        Optional<Libro> l = Optional.empty();
        for (int i = 0; i < 2; i++) {
            when(libroDAO.findById(1)).thenReturn(l);
        }
        assertEquals(libri,
                prenotazioneService.visualizzaListaLibriPerBiblioteca("nome"));
    }

}
