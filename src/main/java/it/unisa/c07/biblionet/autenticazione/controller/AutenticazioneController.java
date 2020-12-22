package it.unisa.c07.biblionet.autenticazione.controller;

import it.unisa.c07.biblionet.autenticazione.service.AutenticazioneService;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("loggedUser")
@RequiredArgsConstructor
@RequestMapping("/autenticazione")
public class AutenticazioneController {
    private final AutenticazioneService autenticazioneService;

    @RequestMapping(value = "/")
    public String visualizzaLogin() {
        return "autenticazione";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(final String email,
                        final String password, final Model model) {

            UtenteRegistrato utente = autenticazioneService.login(email,
                                                                password);

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

    @ModelAttribute("loggedUser")
    public UtenteRegistrato utenteRegistrato() {
        return new UtenteRegistrato();
    }
}
