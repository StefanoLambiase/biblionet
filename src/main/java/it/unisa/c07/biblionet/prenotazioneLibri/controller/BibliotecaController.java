package it.unisa.c07.biblionet.prenotazioneLibri.controller;

import it.unisa.c07.biblionet.model.entity.Libro;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import it.unisa.c07.biblionet.prenotazioneLibri.service.PrenotazioneLibriService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

/**
 * Implementa il controller per il sottosistema
 * PrenotazioneLibri, in particolare la gestione
 * delle Biblioteche.
 *
 * @author Viviana Pentangelo, Gianmario Voria
 */
@SessionAttributes("loggedUser")
@Controller
@RequiredArgsConstructor
@RequestMapping("/biblioteca")
public class BibliotecaController {

    /**
     * Il service per effettuare le operazioni di
     * persistenza.
     */
    private final PrenotazioneLibriService prenotazioneService;

    /**
     * Implementa la funzionalità che permette di
     * visualizzare tutte le biblioteche iscritte.
     *
     * @param model Il model in cui salvare la lista
     * @return La view per visualizzare le biblioteche
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String visualizzaListaBiblioteche(final Model model) {

        return "/biblioteca/visualizza-lista-biblioteche";
    }


    /**
     * Implementa la funzionalità che permette di
     * visualizzare la pagina per l'inserimento di
     * nuovi libri prenotabili.
     * @param model Il model per recuperare l'utente
     * @return La view
     */
    @RequestMapping(value = "/inserisci-nuovo-libro", method = RequestMethod.GET)
    public String visualizzaInserimentoLibro(final Model model) {

        UtenteRegistrato utente = (UtenteRegistrato) model.getAttribute("loggedUser");
        if (utente == null || utente.getTipo() != "Biblioteca") {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return "/biblioteca/inserimento-nuovo-libro-prenotabile";
    }

    /**
     * Implementa la funzionalità che permette inserire
     * un libro tramite l'isbn ed una Api di Google.
     * @param isbn l'isbn del libro
     * @param generi la lista dei generi del libro
     * @param numCopie il numero di copie possedute
     * @param model Il model per recuperare l'utente
     * @return La view per visualizzare il libro
     */
    @RequestMapping(value = "/inserimento-isbn", method = RequestMethod.POST)
    public String inserisciPerIsbn(final Model model,
                                   @RequestParam final String isbn,
                                   @RequestParam final String[] generi,
                                   @RequestParam final int numCopie) {

        if(isbn == null) {
            return "redirect:/biblioteca/inserisci-nuovo-libro";
        }
        UtenteRegistrato utente = (UtenteRegistrato) model.getAttribute("loggedUser");
        if (utente == null || utente.getTipo() != "Biblioteca") {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        Biblioteca b = (Biblioteca) utente;
        List<String> glist = Arrays.asList(generi.clone());
        Libro l = prenotazioneService.inserimentoPerIsbn(isbn,b.getEmail(),numCopie,glist);
        return "/prenotazione-libri/"+l.getIdLibro()+"/visualizza-libro";
    }
}
