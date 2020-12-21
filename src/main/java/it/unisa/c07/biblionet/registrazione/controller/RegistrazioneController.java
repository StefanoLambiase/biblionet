package it.unisa.c07.biblionet.registrazione.controller;

import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import it.unisa.c07.biblionet.registrazione.service.RegistrazioneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Controller
@RequiredArgsConstructor
@RequestMapping("/registrazione")
public class RegistrazioneController {
    private final RegistrazioneService registrazioneService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String visualizzaRegistrazione() {
        return "registrazione";
    }

    @RequestMapping(value = "/scegli", method = RequestMethod.GET)
    public String scegliRegistrazione(final @RequestParam("scelta") String scelta) {
        System.out.println(scelta);
        return "redirect:/registrazione/"+scelta.toLowerCase();
    }



    @RequestMapping(value = "/lettore", method = RequestMethod.GET)
    public String registrazioneLettore(final Lettore lettore){
        System.out.println(lettore);
        return "registrazione_lettore";
    }

}
