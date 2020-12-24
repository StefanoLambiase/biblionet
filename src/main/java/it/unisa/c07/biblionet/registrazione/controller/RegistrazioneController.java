package it.unisa.c07.biblionet.registrazione.controller;

import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.registrazione.service.RegistrazioneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author Alessio Casolaro, Antonio Della Porta
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/registrazione")
public final class RegistrazioneController {

    /**
     * Il service per effettuare le operazioni di persistenza.
     */
    private final RegistrazioneService registrazioneService;

    /**
     * Implementa la funzionalitá di visualizzare
     * la scelta di registrazione.
     * @return La pagina di visualizzazione
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String visualizzaRegistrazione() {
        return "registrazione";
    }

    /**
     * Implementa la funzionalitá di registrazione di
     * scegliere il tipo di utente da registrare.
     * @param scelta Il tipo di utente da registrare
     * @return La view che visualizza il form di registrazione scelto.
     */
    @RequestMapping(value = "/scegli", method = RequestMethod.POST)
    public String scegliRegistrazione(final @RequestParam("scelta")
                                              String scelta) {
        return "registrazione_" + scelta.toLowerCase();
    }

    /**
     * Implementa la funzionalità di registrazione di un esperto.
     * @param esperto l'esperto da registrare
     * @param password il campo conferma password del form per controllare
     *                 il corretto inserimento della stessa
     * @param bibliotecaEmail la mail dell'account della biblioteca
     *                         dove l'esperto lavora
     * @return la view per effettuare il login
     */
    @RequestMapping(value = "/esperto", method = RequestMethod.POST)
    public String registrazioneEsperto(final Esperto esperto,
                                       final @RequestParam("conferma_password")
                                               String password,
                                       final @RequestParam("email_biblioteca")
                                               String bibliotecaEmail,
                                       final @RequestParam("genere") String[]generi) {

        Biblioteca biblioteca
                = registrazioneService.findBibliotecaByEmail(bibliotecaEmail);


        if (biblioteca == null) {
            System.out.println("Questa biblioteca non va bene");
            return "registrazione_esperto";
        }
        esperto.setBiblioteca(biblioteca);

        if(generi!=null){
            esperto.setGeneri(registrazioneService.findGeneriByName(generi));
        }

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

    /**
     * Implementa la funzionalità di registrazione di una biblioteca.
     * @param biblioteca la biblioteca da registrare
     * @param password la password di conferma
     * @return la view di login
     */
    @RequestMapping(value = "/biblioteca", method = RequestMethod.POST)
    public String registrazioneBiblioteca(final Biblioteca biblioteca,
                                        final @RequestParam("conferma_password")
                                                String password) {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-256");
            byte[] arr = md.digest(password.getBytes());
            if (Arrays.compare(arr, biblioteca.getPassword()) != 0) {
                System.out.println("Questa password non va bene");
                return "registrazione_biblioteca";
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        registrazioneService.registraBiblioteca(biblioteca);
        return "registrazione";
    }



     /** Implementa la funzionalitá di registrazione di
     * un lettore
     * di gestire la chiamata POST
     * per creare un nuovo lettore.
     * @param lettore Il lettore da registrare
     * @param password il campo conferma password del form per controllare
     * il corretto inserimento della stessa.
     * @return La view per effettuare il login
     */
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
}
