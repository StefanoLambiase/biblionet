package it.unisa.c07.biblionet.comunicazioneEsperto.controller;

import it.unisa.c07.biblionet.comunicazioneEsperto.service.ComunicazioneEspertoService;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

/**
 * @author Alessio Casolaro
 * @author Della Porta Antonio
 */
@Controller
@RequiredArgsConstructor
@SessionAttributes("loggedUser")
@RequestMapping("/comunicazione-esperto")
public class ComunicazioneEspertoController {

    /**
     * Il service per effettuare le operazioni di persistenza.
     */
    private final ComunicazioneEspertoService comunicazioneEspertoService;

    /**
     * Implementa la funzionalità di mostrare gli esperti in base
     * ai generi preferiti del lettore.
     * @param model utilizzato per gestire la sessione
     * @return la view contenente la lista
     */
    @RequestMapping(value = "/visualizza-esperti-genere",
                    method = RequestMethod.GET)
    public final String visualizzaEspertiGeneri(final Model model) {

        Lettore lettore = (Lettore) model.getAttribute("loggedUser");
        List<Esperto> listaEsperti = comunicazioneEspertoService.
                                       getEspertiByGeneri(lettore.getGeneri());
        model.addAttribute("esperti", listaEsperti);
        return "comunicazione-esperto/lista-esperti";
    }

    /**
     * Implementa la funzionalità di visualizzare tutti gli Esperti
     * presenti sulla piattaforma.
     * @param model il model per la richiesta
     * @return la view che visualizza tutti gli Esperti
     */
    @RequestMapping(value = "/lista-esperti", method = RequestMethod.GET)
    public final String visualizzaListaEsperti(final Model model) {
        List<Esperto> listaEsperti =
                comunicazioneEspertoService.getAllEsperti();
        model.addAttribute("listaEsperti", listaEsperti);
        return "comunicazione-esperto/lista-completa-esperti";
    }

    /**
     * Implementa la funzionalità di visualizzare tutti gli Esperti
     * presenti sulla piattaforma.
     * @param model il model per la richiesta
     * @return la view che visualizza tutti gli Esperti
     */
    @RequestMapping(value = "/ricerca", method = RequestMethod.GET)
    public final String visualizzaListaEspertiFiltrati(
            @RequestParam("stringa") final String stringa,
            @RequestParam("filtro") final String filtro,
                                    final Model model) {
        switch (filtro) {
            case "nome":
                model.addAttribute("listaEsperti", comunicazioneEspertoService.
                        getEsperiByName(stringa));
                break;
            case "genere":
                model.addAttribute("listaEsperti", comunicazioneEspertoService.
                        visualizzaEspertiPerGenere(stringa));
                break;
            default:
                model.addAttribute("listaEsperti", comunicazioneEspertoService.
                        getAllEsperti());
                break;
        }
        return "comunicazione-esperto/lista-completa-esperti";
    }
}
