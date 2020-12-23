package it.unisa.c07.biblionet.registrazione.controller;

import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.registrazione.service.RegistrazioneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Controller
@RequiredArgsConstructor
@RequestMapping("/registrazione")
public class RegistrazioneController {
    private final RegistrazioneService registrazioneService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String visualizzaRegistrazione() {

        return "registrazione";
    }

    @RequestMapping(value = "/scegli", method = RequestMethod.POST)
    public String scegliRegistrazione(Model model,final @RequestParam("scelta") String scelta) {
        model.addAttribute("esperto",new Esperto());
        return "registrazione_" + scelta.toLowerCase();
    }

    @RequestMapping(value = "/lettore", method = RequestMethod.POST)
    public String registrazioneLettore(final Lettore lettore,
                                       final @RequestParam("conferma_password")
                                               String password) {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-256");
            byte[] arr = md.digest(password.getBytes());
            if (Arrays.compare(arr, lettore.getPassword()) != 0) {
                System.out.println("Questa password non va bene");
                return "registrazione_lettore";
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        registrazioneService.registraLettore(lettore);
        return "registrazione";
    }

    @RequestMapping(value = "/biblioteca", method = RequestMethod.POST)
    public String registrazioneBiblioteca( final Biblioteca biblioteca,
                                        final @RequestParam("conferma_password")
                                                String password){
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-256");
            byte[] arr = md.digest(password.getBytes());
            if(Arrays.compare(arr,biblioteca.getPassword())!=0){
                System.out.println("Questa password non va bene");
                return "registrazione_biblioteca";
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        registrazioneService.registraBiblioteca(biblioteca);
        return "registrazione";

    }

}