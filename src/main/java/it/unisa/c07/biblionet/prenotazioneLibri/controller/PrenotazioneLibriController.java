package it.unisa.c07.biblionet.prenotazioneLibri.controller;

import it.unisa.c07.biblionet.model.dao.customQueriesResults.ILibroIdAndName;
import it.unisa.c07.biblionet.model.entity.Libro;
import it.unisa.c07.biblionet.model.entity.TicketPrestito;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import it.unisa.c07.biblionet.prenotazioneLibri.service.PrenotazioneLibriService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

/**
 * Implementa il controller per il sottosistema
 * PrenotazioneLibri.
 *
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
     *
     * @param model Il model in cui salvare la lista
     * @return La view per visualizzare i libri
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String visualizzaListaLibri(final Model model) {
        model.addAttribute("listaLibri",
                prenotazioneService.visualizzaListaLibriCompleta());
        return "prenotazione-libri/visualizza-libri-prenotabili";
    }

    /**
     * Implementa la funzionalità che permette di
     * visualizzare tutti i libri filtrati.
     *
     * @param stringa La stringa di ricerca
     * @param filtro  L'informazione su cui filtrare
     * @param model   Il model per salvare la lista
     * @return La view che visualizza la lista
     */
    @RequestMapping(value = "/ricerca", method = RequestMethod.GET)
    public String visualizzaListaFiltrata(
            @RequestParam("stringa") final String stringa,
            @RequestParam("filtro") final String filtro,
            final Model model) {

        switch (filtro) {
            case "titolo":
                model.addAttribute("listaLibri", prenotazioneService.
                        visualizzaListaLibriPerTitolo(stringa));
                break;
            case "genere":
                model.addAttribute("listaLibri", prenotazioneService.
                        visualizzaListaLibriPerGenere(stringa));
                break;
            case "biblioteca":
                model.addAttribute("listaLibri", prenotazioneService.
                        visualizzaListaLibriPerBiblioteca(stringa));
                break;
            default:
                model.addAttribute("listaLibri", prenotazioneService.
                        visualizzaListaLibriCompleta());
                break;
        }

        return "prenotazione-libri/visualizza-libri-prenotabili";
    }

    /**
     * Implementa la funzionalità che permette di
     * visualizzare le biblioteche presso cui è
     * possibile prentoare il libro.
     *
     * @param id    L'ID del libro di cui effettuare la prenotazione
     * @param model Il model per salvare il libro
     * @return La view che visualizza la lista delle biblioteche
     */
    @RequestMapping(value = "/{id}/visualizza-libro",
            method = RequestMethod.POST)
    public String prenotaLibro(@PathVariable final int id, final Model model) {

        Libro libro = prenotazioneService.getLibroByID(id);
        List<Biblioteca> listaBiblioteche =
                prenotazioneService.getBibliotecheLibro(libro);
        model.addAttribute("lista", listaBiblioteche);
        model.addAttribute("libro", libro);
        return "prenotazione-libri/visualizza-prenota-libro";
    }

    /**
     * Implementa la funzionalità che permette di
     * richiedere il prestito di un libro.
     *
     * @param idBiblioteca L'ID della biblioteca che possiede il libro
     * @param idLibro      L'ID del libro di cui effettuare la prenotazione
     * @param model        Il model per recuperare l'utente loggato
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
        if (utente.getTipo().equals("Lettore")) {
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
     *
     * @param model Il model per recuperare l'utente loggato
     * @return La view che visualizza la lista delle richieste
     */
    @RequestMapping(value = "/visualizza-richieste",
            method = RequestMethod.GET)
    public String visualizzaRichieste(final Model model) {
        UtenteRegistrato utente =
                (UtenteRegistrato) model.getAttribute("loggedUser");
        assert utente != null;
        if (utente.getTipo().equals("Biblioteca")) {
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
        return "/prenotazione-libri/visualizza-richieste-biblioteca";
    }

    /**
     * Implementa la funzionalità che permette di
     * richiedere il prestito di un libro.
     *
     * @param id     l'ID del ticket da accettare
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
     *
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

    /**
     * Implementa la funzionalità che permette di
     * chiudere una prenotazione di un libro quando
     * questo viene riconsegnato.
     *
     * @param id l'ID del ticket da chiudere
     * @return La view che visualizza la lista delle prenotazioni
     */
    @RequestMapping(value = "/ticket/{id}/chiudi",
            method = RequestMethod.POST)
    public String chiudiPrenotazione(final @PathVariable int id) {
        TicketPrestito ticket = prenotazioneService.getTicketByID(id);
        prenotazioneService.chiudiTicket(ticket);
        return "redirect:/prenotazione-libri/visualizza-richieste";
    }

    /**
     * Implementa la funzionalità che permette di
     * ottenere la lista di ticket di un lettore.
     *
     * @param model Il model per recuperare l'utente loggato
     * @return La view che visualizza la lista delle prenotazioni del lettore
     */
    @RequestMapping(value = "/visualizza-prenotazioni",
            method = RequestMethod.GET)
    public String visualizzaPrenotazioniLettore(final Model model) {
        UtenteRegistrato utente =
                (UtenteRegistrato) model.getAttribute("loggedUser");
        assert utente != null;
        if (utente.getTipo().equals("Lettore")) {
            Lettore lettore = (Lettore) utente;

            List<TicketPrestito> listaTicket =
                    prenotazioneService.getTicketsLettore(lettore);
            List<TicketPrestito> list1 = new ArrayList<>();
            List<TicketPrestito> list2 = new ArrayList<>();
            List<TicketPrestito> list3 = new ArrayList<>();
            List<TicketPrestito> list4 = new ArrayList<>();
            for (TicketPrestito t : listaTicket) {
                if (t.getStato().equals(
                        TicketPrestito.Stati.IN_ATTESA_DI_CONFERMA)) {
                    list1.add(t);
                } else if (t.getStato().equals(
                        TicketPrestito.Stati.IN_ATTESA_DI_RESTITUZIONE)) {
                    list2.add(t);
                } else if (t.getStato().equals(
                        TicketPrestito.Stati.CHIUSO)) {
                    list3.add(t);
                } else if (t.getStato().equals(
                        TicketPrestito.Stati.RIFIUTATO)) {
                    list4.add(t);
                }
            }
            model.addAttribute("listaTicketDaAccettare", list1);
            model.addAttribute("listaTicketAccettati", list2);
            model.addAttribute("listaTicketChiusi", list3);
            model.addAttribute("listaTicketRifiutati", list4);
        }
        return "prenotazione-libri/visualizza-richieste-lettore";
    }

    /**
     * Implementa la funzionalità che permette di
     * ottenere una lista di id e titoli di libri
     * sulla base di un titolo dato
     *
     * ! Controllare prima di consegnare
     *
     * @param titolo il titolo che deve mathcare
     * @return la lista di informazioni
     */
    @RequestMapping(value = "/find-libri-by-titolo-contains")
    public @ResponseBody List<ILibroIdAndName> findLibriByTitoloContaining(
            @RequestParam("stringa") String titolo
    ) {
        return prenotazioneService.findByTitoloContains(titolo);
    }

}
