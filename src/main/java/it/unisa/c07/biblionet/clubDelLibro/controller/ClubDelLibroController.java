package it.unisa.c07.biblionet.clubDelLibro.controller;

import it.unisa.c07.biblionet.clubDelLibro.service.ClubDelLibroService;
import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Viviana Pentangelo, Gianmario Voria
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/club-del-libro")
public class ClubDelLibroController {

    /**
     * Il service per effettuare le operazioni di persistenza.
     */
    private final ClubDelLibroService clubService;

    /**
     * Si occupa del caricamento dell pagina iniziale
     * per l'inserimento dei campi
     * SOLO A SCOPO DI TEST.
     * @return La view col form
     */
    @RequestMapping("/")
    public String init() {
        return "crea-club";
    }

    /**
     * Metodo del controller che si occupa
     * di gestire la chiamata POST
     * per creare un club del libro.
     * @param club Il club del libro passato dalla view
     * @return ModelAndView: model per passare un attributo
     * alla view che dovrà visualizzarlo
     */
    @RequestMapping(value = "/crea", method = RequestMethod.POST)
    public ModelAndView creaClubDelLibro(final ClubDelLibro club) {
        Esperto esperto = new Esperto(
                "eliaviviani@gmail.com",
                "EspertoPassword",
                "Napoli",
                "Torre del Greco",
                "Via Roma 2",
                "2345678901",
                "Espertissimo",
                "Elia",
                "Viviani",
                new Biblioteca(
                        "bibliotecacarrisi@gmail.com",
                        "BibliotecaPassword",
                        "Napoli",
                        "Torre del Greco",
                        "Via Carrisi 47",
                        "1234567890",
                        "Biblioteca Carrisi"
                )
        );
        club.setEsperto(esperto);
        ClubDelLibro nuovoClub = clubService.creaClubDelLibro(club);
        ModelAndView mav = new ModelAndView("visualizza-club");
        mav.addObject("club", nuovoClub);
        return mav;
    }




}
