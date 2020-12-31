package it.unisa.c07.biblionet.prenotazioneLibri.controller;

import it.unisa.c07.biblionet.model.entity.Libro;
import it.unisa.c07.biblionet.model.entity.TicketPrestito;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import it.unisa.c07.biblionet.prenotazioneLibri.service.PrenotazioneLibriService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.ArrayList;
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
        UtenteRegistrato u =
                (UtenteRegistrato) model.getAttribute("loggedUser");
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
        List<Biblioteca> listaBiblioteche =
                        prenotazioneService.getBibliotecheLibro(libro);
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
    @RequestMapping(value = "/conferma-prenotazione",
                                    method = RequestMethod.POST)
    public String confermaPrenotazione(@RequestParam final String idBiblioteca,
                                        @RequestParam final String idLibro,
                                        final Model model) {

        UtenteRegistrato utente =
                    (UtenteRegistrato) model.getAttribute("loggedUser");
        assert utente != null;
        if (utente.getClass().getSimpleName().equals("Lettore")) {
            Lettore l = (Lettore) utente;
            prenotazioneService.richiediPrestito(l,
                                    idBiblioteca,
                                    Integer.parseInt(idLibro));
        }
        return "redirect:/prenotazione-libri";
    }

    /**
     * Implementa la funzionalità che permette di
     * ad una biblioteca di visualizzare le richieste di
     * prenotazione ricevute.
     * @param model Il model per recuperare l'utente loggato
     * @return La view che visualizza la lista delle richieste
     */
    @RequestMapping(value = "/visualizza-richieste",
                             method = RequestMethod.GET)
    public String visualizzaRichieste(final Model model) {
        UtenteRegistrato utente =
                (UtenteRegistrato) model.getAttribute("loggedUser");
        assert utente != null;
        if (utente.getClass().getSimpleName().equals("Biblioteca")) {
            Biblioteca biblioteca = (Biblioteca) utente;
            List<TicketPrestito> lista =
                    prenotazioneService.getTicketsByBiblioteca(biblioteca);
            List<TicketPrestito> list1 = new ArrayList<>();
            List<TicketPrestito> list2 = new ArrayList<>();
            List<TicketPrestito> list3 = new ArrayList<>();
            for (TicketPrestito t : lista) {
                if (t.getStato().equals(
                            TicketPrestito.Stati.IN_ATTESA_DI_CONFERMA)) {
                    list1.add(t);
                } else if (t.getStato().equals(
                        TicketPrestito.Stati.IN_ATTESA_DI_RESTITUZIONE)) {
                    list2.add(t);
                } else if (t.getStato().equals(
                        TicketPrestito.Stati.CHIUSO)) {
                    list3.add(t);
                }
            }
            model.addAttribute("listaTicketDaAccettare", list1);
            model.addAttribute("listaTicketAccettati", list2);
            model.addAttribute("listaTicketChiusi", list3);
        }
        return "visualizza-richieste";
    }

    /**
     * Implementa la funzionalità che permette di
     * richiedere il prestito di un libro.
     * @param id l'ID del ticket da accettare
     * @param giorni il tempo di concessione del prestito
     * @return La view che visualizza la lista delle prenotazioni
     */
    @RequestMapping(value = "/ticket/{id}/accetta",
            method = RequestMethod.POST)
    public String accettaPrenotazione(final @PathVariable int id,
                  final @RequestParam(value = "giorni") int giorni) {
        TicketPrestito ticket = prenotazioneService.getTicketByID(id);
        prenotazioneService.accettaRichiesta(ticket, giorni);
        return "redirect:/prenotazione-libri/visualizza-richieste";
    }

    /**
     * Implementa la funzionalità che permette di
     * richiedere il prestito di un libro.
     * @param id l'ID del ticket da rifiutare
     * @return La view che visualizza la lista delle prenotazioni
     */
    @RequestMapping(value = "/ticket/{id}/rifiuta",
            method = RequestMethod.POST)
    public String rifiutaPrenotazione(final @PathVariable int id) {
        TicketPrestito ticket = prenotazioneService.getTicketByID(id);
        prenotazioneService.rifiutaRichiesta(ticket);
        return "redirect:/prenotazione-libri/visualizza-richieste";
    }


}
