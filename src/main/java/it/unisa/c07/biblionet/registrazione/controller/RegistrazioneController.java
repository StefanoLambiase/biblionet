package it.unisa.c07.biblionet.registrazione.controller;

import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.registrazione.service.RegistrazioneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
@RequestMapping("/registrazione")
public class RegistrazioneController {
    private final RegistrazioneService registrazioneService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String visualizzaRegistrazione() {
        return "lettore";
    }


    @RequestMapping(value = "/lettore", method = RequestMethod.POST)
    public String registrazioneLettore(final Lettore lettore){
        System.out.println(lettore);
        return "registrazione";
    }

}
