package it.unisa.c07.biblionet.registrazione.controller;

import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import it.unisa.c07.biblionet.registrazione.service.RegistrazioneService;
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

/**
 * @author Alessio Casolaro, Antonio Della Porta
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
     * Implementa la funzionalitá di visualizzare
     * la scelta di registrazione.
     *
     * @return La pagina di visualizzazione
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String visualizzaRegistrazione() {
        return "registrazione";
    }

    /**
     * Implementa la funzionalitá di registrazione di
     * scegliere il tipo di utente da registrare.
     *
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
     *
     * @param esperto         l'esperto da registrare
     * @param password        il campo conferma password del form per controllare
     *                        il corretto inserimento della stessa
     * @param bibliotecaEmail la mail dell'account della biblioteca
     *                        dove l'esperto lavora
     * @param generi          gli eventuali generi definiti per l'esperto
     * @return la view per effettuare il login
     */
    @RequestMapping(value = "/esperto", method = RequestMethod.POST)
    public String registrazioneEsperto(final Esperto esperto,
                                       final @RequestParam("conferma_password")
                                               String password,
                                       final @RequestParam("email_biblioteca")
                                               String bibliotecaEmail,
                                       final @RequestParam("genere")
                                               String[] generi) {

        Biblioteca biblioteca
                = registrazioneService.findBibliotecaByEmail(bibliotecaEmail);


        if (biblioteca == null) {
            System.out.println("Questa biblioteca non va bene");
            return "registrazione_esperto";
        }
        esperto.setBiblioteca(biblioteca);

        if (generi != null) {
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
     *
     * @param biblioteca la biblioteca da registrare
     * @param password   la password di conferma
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


    /**
     * Implementa la funzionalitá di registrazione di
     * un lettore
     * di gestire la chiamata POST
     * per creare un nuovo lettore.
     *
     * @param lettore  Il lettore da registrare
     * @param password il campo conferma password del form per controllare
     *                 il corretto inserimento della stessa.
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


    @RequestMapping(value = "/modifica-dati", method = RequestMethod.GET)
    public String modificaDati(final Model model) {
        UtenteRegistrato utente = (UtenteRegistrato) model.getAttribute("loggedUser");

        if (utente != null) {
            if (registrazioneService.isUserBiblioteca(utente)) {
                Biblioteca biblioteca = (Biblioteca) utente;
                model.addAttribute("biblioteca", biblioteca);
                return "modifica_dati_biblioteca";

            } else if (registrazioneService.isUserEsperto(utente)) {
                Esperto esperto = (Esperto) utente;
                model.addAttribute("esperto", esperto);
                return "modifica_dati_esperto";

            } else if (registrazioneService.isUserLettore(utente)) {
                Lettore lettore = (Lettore) utente;
                model.addAttribute("lettore", lettore);
                return "modifica_dati_lettore";

            }
        }
        return "autenticazione";
    }

    @RequestMapping(value = "/conferma-modifica-biblioteca",method = RequestMethod.POST)
    public String confermaModificaBiblioteca(final Model model,final Biblioteca biblioteca,
                                   @RequestParam("vecchia_password")String vecchia,
                                   @RequestParam("nuova_password")String nuova,
                                   @RequestParam("conferma_password")String conferma,
                                   @RequestParam("current_email")String email){


    Biblioteca toUpload=registrazioneService.findBibliotecaByEmail(email);

        if(!vecchia.isEmpty() && !nuova.isEmpty() && !conferma.isEmpty()) {
            System.out.println(vecchia+nuova+conferma);
            try {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA-256");
                byte[] vecchia_hash = md.digest(vecchia.getBytes());

                if (Arrays.compare(vecchia_hash, biblioteca.getPassword()) == 0) {
                    biblioteca.setPassword(nuova);
                    registrazioneService.aggiornaBiblioteca(biblioteca);
                    model.addAttribute("loggedUser",biblioteca);
                    return "autenticazione";
                }
                else
                    return "modifica_dati_biblioteca";
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        biblioteca.setPassword(toUpload.getPassword());
        registrazioneService.aggiornaBiblioteca(biblioteca);
        model.addAttribute("loggedUser",biblioteca);
        return "autenticazione";

    }

    @RequestMapping(value = "/conferma-modifica-esperto",method = RequestMethod.POST)
    public String confermaModificaEsperto(final Model model,final Esperto esperto,
                                             @RequestParam("vecchia_password")String vecchia,
                                             @RequestParam("nuova_password")String nuova,
                                             @RequestParam("conferma_password")String conferma,
                                             @RequestParam("current_email")String email){


        Esperto toUpload=registrazioneService.findEspertoByEmail(email);

        if(!vecchia.isEmpty() && !nuova.isEmpty() && !conferma.isEmpty()) {
            System.out.println(vecchia+nuova+conferma);
            try {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA-256");
                byte[] vecchia_hash = md.digest(vecchia.getBytes());

                if (Arrays.compare(vecchia_hash, esperto.getPassword()) == 0) {
                    esperto.setPassword(nuova);
                    registrazioneService.aggiornaEsperto(esperto);
                    model.addAttribute("loggedUser",esperto);
                    return "autenticazione";
                }
                else
                    return "modifica_dati_esperto";
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        esperto.setPassword(toUpload.getPassword());
        registrazioneService.aggiornaEsperto(esperto);
        model.addAttribute("loggedUser",esperto);
        return "autenticazione";

    }
}
