package it.unisa.c07.biblionet.registrazione.controller;

import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.registrazione.service.RegistrazioneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
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

    @RequestMapping(value = "/scegli", method = RequestMethod.GET)
    public String scegliRegistrazione(final @RequestParam("scelta") String scelta) {
        return "registrazione_"+scelta.toLowerCase();
    }

    @RequestMapping(value = "/biblioteca", method = RequestMethod.POST)
    public String registrazioneBiblioteca(final Biblioteca biblioteca, final @RequestParam("conferma_password") String password){
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-256");
            byte[] arr = md.digest(password.getBytes());
            if(Arrays.compare(arr,biblioteca.getPassword())!=0){
                System.out.println("La password non coincide\n");
                return "registrazione_biblioteca";
            }
            else{
                this.registrazioneService.registraBiblioteca(biblioteca);
                return "registrazione";//Questo redirect dovra portarci alla pagina Login
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
