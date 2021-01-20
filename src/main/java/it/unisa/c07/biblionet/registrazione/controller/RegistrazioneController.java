package it.unisa.c07.biblionet.registrazione.controller;

import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.registrazione.service.RegistrazioneService;
import it.unisa.c07.biblionet.utils.validazione.RegexTester;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Alessio Casolaro
 * @author Antonio Della Porta
 */
@Controller
@SessionAttributes("loggedUser")
@RequiredArgsConstructor
@RequestMapping("/registrazione")
public final class RegistrazioneController {

    /**
     * Il service per effettuare le operazioni di persistenza.
     */
    private final RegistrazioneService registrazioneService;

    /**
     * Implementa la funzionalità di visualizzare
     * la scelta di registrazione.
     *
     * @return La pagina di visualizzazione
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String visualizzaRegistrazione() {
        return "registrazione/registrazione";
    }

    /**
     * Implementa la funzionalità di registrazione di
     * scegliere il tipo di utente da registrare.
     *
     * @param scelta Il tipo di utente da registrare
     * @return La view che visualizza il form di registrazione scelto.
     */
    @RequestMapping(value = "/scegli", method = RequestMethod.POST)
    public String scegliRegistrazione(final @RequestParam("scelta")
                                              String scelta) {
        return "registrazione/registrazione_" + scelta.toLowerCase();
    }

    /**
     * Implementa la funzionalità di registrazione di un esperto.
     *
     * @param esperto l'esperto da registrare
     * @param password il campo conferma password del form per controllare
     *                 il corretto inserimento della stessa
     * @param bibliotecaEmail la mail dell'account della biblioteca
     *                        dove l'esperto lavora
     * @param model utilizzato per la sessione
     * @return la view per effettuare il login
     */
    @RequestMapping(value = "/esperto", method = RequestMethod.POST)
    public String registrazioneEsperto(final Esperto esperto,
                                       final @RequestParam("conferma_password")
                                               String password,
                                       final @RequestParam("email_biblioteca")
                                               String bibliotecaEmail,
                                       final Model model) {

        if (registrazioneService.isEmailRegistrata(esperto.getEmail())) {
            return "registrazione/registrazione_esperto";
        }

        HashMap<String,String> tester = new HashMap<>();
        tester.put(esperto.getNome(),"^[A-zÀ-ù ‘-]{2,30}$");
        tester.put(esperto.getCognome() ,"^[A-zÀ-ù ‘-]{2,30}$");
        tester.put(esperto.getRecapitoTelefonico(),"^\\d{10}$");
        tester.put(esperto.getVia(), "^[0-9A-zÀ-ù ‘-]{2,30}$");

        RegexTester regexTester = new RegexTester();
        if(!regexTester.toTest(tester)){
            return "registrazione/registrazione_esperto";
        }


        Biblioteca biblioteca
                = registrazioneService.getBibliotecaByEmail(bibliotecaEmail);


        if (biblioteca == null) {
            System.out.println("Questa biblioteca non va bene");
            return "registrazione/registrazione_esperto";
        }
        esperto.setBiblioteca(biblioteca);

        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-256");
            byte[] arr = md.digest(password.getBytes());
            if (Arrays.compare(arr, esperto.getPassword()) != 0) {
                System.out.println("Questa password non va bene");
                return "registrazione/registrazione_esperto";
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        registrazioneService.registraEsperto(esperto);
        model.addAttribute("loggedUser", esperto);
        return "redirect:/preferenze-di-lettura/generi";


    }

    /**
     * Implementa la funzionalità di registrazione di una biblioteca.
     *
     * @param biblioteca la biblioteca da registrare
     * @param password   la password di conferma
     * @return la view di login
     */
    @RequestMapping(value = "/biblioteca", method = RequestMethod.POST)
    public String registrazioneBiblioteca(final Biblioteca biblioteca,
                                 final @RequestParam("conferma_password")
                                                  String password) {

        if (registrazioneService.isEmailRegistrata(biblioteca.getEmail())) {
            return "registrazione/registrazione_biblioteca";
        }


        HashMap<String,String> tester = new HashMap<>();
        tester.put(biblioteca.getNomeBiblioteca(), "^[A-zÀ-ù ‘-]{2,60}$");
        tester.put(biblioteca.getRecapitoTelefonico(), "^\\d{10}$");
        tester.put(biblioteca.getVia(), "^[0-9A-zÀ-ù ‘-]{2,30}$");

        RegexTester regexTester = new RegexTester();

        if(!regexTester.toTest(tester)){
            return "registrazione/registrazione_biblioteca";
        }
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-256");
            byte[] arr = md.digest(password.getBytes());
            if (Arrays.compare(arr, biblioteca.getPassword()) != 0) {
                System.out.println("Questa password non va bene");
                return "registrazione/registrazione_biblioteca";
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        registrazioneService.registraBiblioteca(biblioteca);
        return "autenticazione/login";
    }


    /**
     * Implementa la funzionalità di registrazione di
     * un lettore.
     * Gestisce la chiamata POST
     * per creare un nuovo lettore.
     *
     * @param lettore  Il lettore da registrare
     * @param password il campo conferma password del form per controllare
     *                 il corretto inserimento della stessa.
     * @param model    utilizzato per gestire la sessione
     * @return La view per effettuare il login
     */
    @RequestMapping(value = "/lettore", method = RequestMethod.POST)
    public String registrazioneLettore(final Lettore lettore,
                                       final @RequestParam("conferma_password")
                                               String password,
                                       final Model model) {

        if (registrazioneService.isEmailRegistrata(lettore.getEmail())) {
            return "registrazione/registrazione_lettore";
        }

        HashMap<String,String> tester = new HashMap<>();
        tester.put(lettore.getNome(),"^[A-zÀ-ù ‘-]{2,30}$");
        tester.put(lettore.getCognome() ,"^[A-zÀ-ù ‘-]{2,30}$");
        tester.put(lettore.getRecapitoTelefonico(),"^\\d{10}$");
        tester.put(lettore.getVia(), "^[0-9A-zÀ-ù ‘-]{2,30}$");

        RegexTester regexTester = new RegexTester();

        if(!regexTester.toTest(tester)){
            return "registrazione/registrazione_lettore";
        }
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-256");
            byte[] arr = md.digest(password.getBytes());
            if (Arrays.compare(arr, lettore.getPassword()) != 0) {
                System.out.println("Questa password non va bene");
                return "registrazione/registrazione_lettore";
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        registrazioneService.registraLettore(lettore);
        model.addAttribute("loggedUser", lettore);
        return "redirect:/preferenze-di-lettura/generi";
    }

}
