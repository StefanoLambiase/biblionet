package it.unisa.c07.biblionet.registrazione.controller;

import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
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
public final class RegistrazioneController {

    private final RegistrazioneService registrazioneService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String visualizzaRegistrazione() {
        return "registrazione";
    }

    @RequestMapping(value = "/scegli", method = RequestMethod.POST)
    public String scegliRegistrazione(
            final @RequestParam("scelta") String scelta) {

        return "registrazione_" + scelta.toLowerCase();
    }

    /**
     * Implementa la funzionalità di registrazione di un esperto
     * @param esperto l'esperto da registrare
     * @param password il campo conferma password del form per controllare
     *                 il corretto inserimento della stessa
     * @param biblioteca_email la mail dell'account della biblioteca
     *                         dove l'esperto lavora
     * @return la view per effettuare il login
     */
    @RequestMapping(value = "/esperto", method = RequestMethod.POST)
    public String registrazioneEsperto(final Esperto esperto,
                                       final @RequestParam("conferma_password")
                                               String password,
                                       final @RequestParam("email_biblioteca")
                                               String biblioteca_email) {

        Biblioteca biblioteca
                = registrazioneService.findBibliotecaByEmail(biblioteca_email);


        if (biblioteca == null) {
            System.out.println("Questa biblioteca non va bene");
            return "registrazione_esperto";
        }
        esperto.setBiblioteca(biblioteca);

        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-256");
            byte[] arr = md.digest(password.getBytes());
            if (Arrays.compare(arr, esperto.getPassword()) != 0) {
                System.out.println("Questa password non va bene");
                return "registrazione_esperto";
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        registrazioneService.registraEsperto(esperto);
        return "registrazione";


    }

}
