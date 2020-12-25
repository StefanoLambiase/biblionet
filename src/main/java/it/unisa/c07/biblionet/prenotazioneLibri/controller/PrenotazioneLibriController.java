package it.unisa.c07.biblionet.prenotazioneLibri.controller;

import it.unisa.c07.biblionet.prenotazioneLibri.service.PrenotazioneLibriService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Implementa il controller per il sottosistema
 * PrenotazioneLibri.
 * @author Viviana Pentangelo, Gianmario Voria
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/prenotazione-libri")
public class PrenotazioneLibriController {

    private final PrenotazioneLibriService prenotazioneService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String visualizzaListaLibri(final Model model) {
        model.addAttribute("listaLibri", prenotazioneService.visualizzaListaLibriCompleta());
        return "visualizza-libri-prenotabili";
    }

    @RequestMapping(value = "/ricerca-titolo", method = RequestMethod.GET)
    public String visualizzaLibriPerTitolo(
                           @RequestParam("titolo") String titolo,
                           final Model model) {
        model.addAttribute("listaLibri", prenotazioneService.visualizzaListaLibriPerTitolo(titolo));
        return "visualizza-libri-prenotabili";
    }

}
