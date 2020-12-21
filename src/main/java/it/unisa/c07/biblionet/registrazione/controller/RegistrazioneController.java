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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
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
        return "registrazione_"+scelta.toLowerCase();
    }

    @RequestMapping(value = "/lettore", method = RequestMethod.POST)
    public String registrazioneLettore(final Lettore lettore,final @RequestParam("conferma_password") String password){
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-256");
            byte[] arr = md.digest(password.getBytes());
            if(Arrays.compare(arr,lettore.getPassword())!=0){
                System.out.println("La password non coincide\n");
                return "registrazione_lettore";
            }
            else{
                this.registrazioneService.registraLettore(lettore);
                return "registrazione";//Questo redirect dovra portarci alla pagina Login
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println(lettore);
        return null;
    }
}
