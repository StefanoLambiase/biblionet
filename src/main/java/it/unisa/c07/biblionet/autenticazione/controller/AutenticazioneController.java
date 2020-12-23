package it.unisa.c07.biblionet.autenticazione.controller;

import it.unisa.c07.biblionet.autenticazione.service.AutenticazioneService;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * Questa è la classe controller per il sottosistema di Autenticazione.
 */
@Controller
@SessionAttributes("loggedUser")
@RequiredArgsConstructor
@RequestMapping("/autenticazione")
public class AutenticazioneController {
    /**
     * È una dichiarazione di un oggetto AutenticazioneService.
     */
    private final AutenticazioneService autenticazioneService;

    /**
     * Implementa la funzionalità di visualizzazione del login.
     * @return la pagina dove è visualizzato
     */
    @RequestMapping(value = "/")
    public String visualizzaLogin() {
        return "autenticazione";
    }

    /**
     * Implementa la funzionionalità di login come utente.
     * @param email dell'utente.
     * @param password password dell'utente.
     * @param model la sessione in cui salvare l'utente.
     * @return rimanda alla pagina di home.
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(final String email,
                        final String password, final Model model) {
        /**
         * Dichiarazione di un utente avente i dati appena
         * inseriti in login per cercarlo nel DB.
         */
        UtenteRegistrato utente = autenticazioneService.login(email,
                                                                password);
        //Da eliminare i system.out
            if (utente == null) {
                System.out.println("Dati errati\n");
                return "autenticazione";
            } else {
                model.addAttribute("loggedUser", utente);
                System.out.println("login di : " + utente.toString()
                        + "come " + utente.getClass().getSimpleName());
            }
            return "index";
    }

    /**
     * Metodo per avere l'utente in sessione.
     * @return dell'utente in sessione.
     */
    @ModelAttribute("loggedUser")
    public UtenteRegistrato utenteRegistrato() {
        return new UtenteRegistrato();
    }

    @RequestMapping(value = "/logout")
    public String logout(final Model model) {
        model.addAttribute("loggedUser", null);

        return "index";
    }

}
