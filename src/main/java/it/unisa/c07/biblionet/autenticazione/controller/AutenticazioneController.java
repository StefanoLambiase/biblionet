package it.unisa.c07.biblionet.autenticazione.controller;

import it.unisa.c07.biblionet.autenticazione.service.AutenticazioneService;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.support.SessionStatus;


/**
 * Implementa il controller per il sottosistema
 * Autenticazione.
 * @author Ciro Maiorino , Giulio Triggiani
 */
@Controller
@SessionAttributes("loggedUser")
@RequiredArgsConstructor
@RequestMapping("/autenticazione")
public class AutenticazioneController {
    /**
     * Il service per effettuare le operazioni di persistenza.
     */
    private final AutenticazioneService autenticazioneService;

    /**
     * Implementa la funzionalità che permette
     * di visualizzare la view del login.
     * @return la pagina dove è visualizzato
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String visualizzaLogin() {

        return "autenticazione/login";
    }

    /**
     * Implementa la funzionalità di login come utente.
     * @param email dell'utente.
     * @param password password dell'utente.
     * @param model la sessione in cui salvare l'utente.
     * @return rimanda alla pagina di home.
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam final String email,
                        @RequestParam final String password,
                        final Model model) {
        UtenteRegistrato utente = autenticazioneService.login(email,
                                                                password);
            if (utente == null) {
                return "autenticazione/login";
            } else {
                model.addAttribute("loggedUser", utente);
            }
            return "index";

    }

    /**
     * Implenta la funzionalità che permette
     * di effettuare il logout dell'utente
     * togliendolo dalla sessione.
     * @param status contiene i dati della sessione.
     * @return Rimanda alla pagina di index.
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)

    public String logout(final SessionStatus status) {
        status.setComplete();
        return "index";
    }


    /**
     * Implementa la funzionalità che permette
     * di aggiungere un utente alla sessione.
     * @return dell'utente in sessione.
     */
    @ModelAttribute("loggedUser")
    public UtenteRegistrato utenteRegistrato() {
        return new UtenteRegistrato();
    }

}
