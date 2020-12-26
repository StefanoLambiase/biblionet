package it.unisa.c07.biblionet.prenotazioneLibri.service;

import it.unisa.c07.biblionet.model.dao.LibroDAO;
import it.unisa.c07.biblionet.model.dao.PossessoDAO;
import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.entity.Libro;
import it.unisa.c07.biblionet.model.entity.Possesso;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementa la classe che esplicita i metodi
 * definiti nell'interfaccia service per il
 * sottosustema PrenotazioneLibri.
 * @author Viviana Pentangelo, Gianmario Voria
 */
@Service
@RequiredArgsConstructor
public class PrenotazioneLibriServiceImpl implements PrenotazioneLibriService {

    /**
     *Si occupa delle operazioni CRUD per libro.
     */
    private final LibroDAO libroDAO;

    /**
     *Si occupa delle operazioni CRUD per biblioteca.
     */
    private final BibliotecaDAO bibliotecaDAO;

    /**
     *Si occupa delle operazioni CRUD per possesso.
     */
    private final PossessoDAO possessoDAO;


    /**
     * Implementa la funzionalità che permette
     * di visualizzare la lista completa dei libri
     * prenotabili sulla piattaforma.
     * @return La lista di libri
     */
    @Override
    public List<Libro> visualizzaListaLibriCompleta() {
        return libroDAO.findAll(Sort.by("titolo"));
    }

    /**
     * Implementa la funzionalità che permette
     * di visualizzare una lista di libri prenotabili
     * filtrata per titolo.
     * @param titolo Stringa che deve essere contenuta
     * nel titolo
     * @return La lista di libri
     */
    @Override
    public List<Libro> visualizzaListaLibriPerTitolo(final String titolo) {
        return libroDAO.findByTitoloLike(titolo);
    }

    /**
     * Implementa la funzionalità che permette
     * di visualizzare la lista completa dei libri
     * prenotabili da una determinata biblioteca.
     * MI RIFIUTO DI TESTARLO.
     * @param nomeBiblioteca Il nome della biblioteca
     * @return La lista di libri
     */
    @Override
    public List<Libro> visualizzaListaLibriPerBiblioteca(
                    final String nomeBiblioteca) {
        Biblioteca b = bibliotecaDAO.findByNome(nomeBiblioteca);
        String bibID = b.getEmail();
        List<Possesso> possessi = possessoDAO.findByBibliotecaID(bibID);
        List<Libro> libri = new ArrayList<>();
        for (Possesso p : possessi) {
            Optional<Libro> l =
                    libroDAO.findById(p.getPossessoID().getLibroID());
            libri.add(l.orElse(null));
        }
        return libri;
    }
}
