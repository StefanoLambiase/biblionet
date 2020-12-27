package it.unisa.c07.biblionet.prenotazioneLibri.controller;

import it.unisa.c07.biblionet.model.entity.Libro;
import it.unisa.c07.biblionet.model.entity.Possesso;
import it.unisa.c07.biblionet.model.entity.compositeKey.PossessoId;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import it.unisa.c07.biblionet.prenotazioneLibri.service.PrenotazioneLibriService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Implementa il controller per il sottosistema
 * PrenotazioneLibri.
 * @author Viviana Pentangelo, Gianmario Voria
 */
@SessionAttributes("loggedUser")
@Controller
@RequiredArgsConstructor
@RequestMapping("/prenotazione-libri")
public class PrenotazioneLibriController {

    /**
     * Il service per effettuare le operazioni di
     * persistenza.
     */
    private final PrenotazioneLibriService prenotazioneService;

    /**
     * Implementa la funzionalità che permette di
     * visualizzare tutti i libri prenotabili.
     * @param model Il model in cui salvare la lista
     * @return La view per visualizzare i libri
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String visualizzaListaLibri(final Model model) {
        model.addAttribute("listaLibri",
                prenotazioneService.visualizzaListaLibriCompleta());
        return "visualizza-libri-prenotabili";
    }

    /**
     * Implementa la funzionalità che permette di
     * visualizzare tutti i libri che contengono
     * una determinata stringa nel titolo.
     * @param titolo La stringa contenuta nel titolo
     * @param model Il model per salvare la lista
     * @return La view che visualizza la lista
     */
    @RequestMapping(value = "/ricerca-titolo", method = RequestMethod.GET)
    public String visualizzaLibriPerTitolo(
                           @RequestParam("titolo") final String titolo,
                           final Model model) {
        model.addAttribute("listaLibri",
                prenotazioneService.visualizzaListaLibriPerTitolo(titolo));
        return "visualizza-libri-prenotabili";
    }

    /**
     * Implementa la funzionalità che permette di
     * visualizzare le biblioteche presso cui è
     * possibile prentoare il libro.
     * @param id L'ID del libro di cui effettuare la prenotazione
     * @param model Il model per salvare il libro
     * @return La view che visualizza la lista delle biblioteche
     */
    @RequestMapping(value = "/{id}/prenota-libro", method = RequestMethod.POST)
    public String prenotaLibro(@PathVariable final int id, final Model model) {

        Libro libro = prenotazioneService.getLibroByID(id);
        List<Biblioteca> listaBiblioteche = prenotazioneService.getBibliotecheLibro(libro);
        model.addAttribute("lista", listaBiblioteche);
        model.addAttribute("libro", libro);
        return "prenota-libro";
    }

    /**
     * Implementa la funzionalità che permette di
     * richiedere il prestito di un libro.
     * @param idBiblioteca L'ID della biblioteca che possiede il libro
     * @param idLibro L'ID del libro di cui effettuare la prenotazione
     * @param model Il model per recuperare l'utente loggato
     * @return La view che visualizza la lista dei libri prenotabili
     */
    @RequestMapping(value= "/conferma-prenotazione", method=RequestMethod.POST)
    public String confermaPrenotazione(@RequestParam final String idBiblioteca,
                                        @RequestParam final String idLibro,
                                        final Model model) {

        UtenteRegistrato utente = (UtenteRegistrato) model.getAttribute("loggedUser");
        if(utente.getClass().getSimpleName().equals("Lettore")) {
            Lettore l = (Lettore) utente;
            prenotazioneService.richiediPrestito(l,
                                    idBiblioteca,
                                    Integer.parseInt(idLibro));
        }
        return "redirect:/prenotazione-libri";
    }

}
