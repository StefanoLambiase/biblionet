package it.unisa.c07.biblionet.autenticazione.controller;

import it.unisa.c07.biblionet.autenticazione.service.AutenticazioneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
@RequestMapping("/autenticazione")
public class AutenticazioneController {
    private final AutenticazioneService autenticazioneService;

    //@RequestMapping(value = "/", method = RequestMethod.POST)

}
