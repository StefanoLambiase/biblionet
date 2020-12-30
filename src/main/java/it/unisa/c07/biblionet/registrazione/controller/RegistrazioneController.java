package it.unisa.c07.biblionet.registrazione.controller;

import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import it.unisa.c07.biblionet.registrazione.service.RegistrazioneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

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
     * @param esperto l'esperto da registrare
     * @param password il campo conferma password del form per controllare
     *                 il corretto inserimento della stessa
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
        return "login";


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
        return "login";
    }


    /**
     * Implementa la funzionalitá di registrazione di
     * un lettore.
     * Gestisce la chiamata POST
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
        return "login";
    }

    /**
     * Implementa la funzionalità di smistare l'utente sulla view di
     * modifica dati corretta.
     * @param model Utilizzato per gestire la sessione.
     *
     * @return modifica_dati_biblioteca se l'account
     * da modificare é una biblioteca.
     *
     * modifica_dati_esperto se l'account
     * da modificare é un esperto.
     *
     * modifica_dati_lettore se l'account
     * da modificare é un lettore.
     */
    @RequestMapping(value = "/modifica-dati", method = RequestMethod.GET)
    public String modificaDati(final Model model) {
        UtenteRegistrato utente = (UtenteRegistrato)
                model.getAttribute("loggedUser");

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
        return "login";
    }

    /**
     * Implementa la funzionalità di modifica dati di una bibilioteca.
     *
     * @param model Utilizzato per gestire la sessione.
     * @param biblioteca Una biblioteca da modificare.
     * @param vecchia La vecchia password dell'account.
     * @param nuova La nuova password dell'account.
     * @param conferma La password di conferma password dell'account.
     *
     * @return login Se la modifica va a buon fine.
     * modifica_dati_biblioteca Se la modifica non va a buon fine
     */
    @RequestMapping(value = "/conferma-modifica-biblioteca",
                    method = RequestMethod.POST)
    public String confermaModificaBiblioteca(final Model model,
                    final Biblioteca biblioteca,
                    final @RequestParam("vecchia_password")String vecchia,
                    final @RequestParam("nuova_password")String nuova,
                    final @RequestParam("conferma_password")String conferma) {


    Biblioteca toUpdate = registrazioneService
                                 .findBibliotecaByEmail(biblioteca.getEmail());

        if (!vecchia.isEmpty() && !nuova.isEmpty() && !conferma.isEmpty()) {
            try {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA-256");
                byte[] vecchiaHash = md.digest(vecchia.getBytes());

                if (Arrays.compare(vecchiaHash,
                        toUpdate.getPassword()) == 0
                        &&
                    nuova.equals(conferma)
                ) {
                    biblioteca.setPassword(nuova);
                } else {
                    return "modifica_dati_biblioteca";
                }

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        } else {
            biblioteca.setHashedPassword(toUpdate.getPassword());
        }

        registrazioneService.aggiornaBiblioteca(biblioteca);
        model.addAttribute("loggedUser", biblioteca);
        return "login";

    }

    /**
     * Implementa la funzionalità di modifica dati di un esperto.
     *
     * @param model Utilizzato per gestire la sessione.
     * @param esperto Un esperto da modificare.
     * @param vecchia La vecchia password dell'account.
     * @param nuova La nuova password dell'account.
     * @param conferma La password di conferma password dell'account.
     * @param emailBiblioteca L'email della biblioteca scelta.
     *
     * @return login Se la modifica va a buon fine.
     * modifica_dati_esperto Se la modifica non va a buon fine
     */
    @RequestMapping(value = "/conferma-modifica-esperto",
            method = RequestMethod.POST)
    public String confermaModificaEsperto(final Model model,
                   final Esperto esperto,
                   final @RequestParam("vecchia_password")String vecchia,
                   final @RequestParam("nuova_password")String nuova,
                   final @RequestParam("conferma_password")String conferma,
                   final @RequestParam("email_biblioteca")
                                                      String emailBiblioteca) {


        Esperto toUpdate = registrazioneService
                .findEspertoByEmail(esperto.getEmail());

        Biblioteca b = registrazioneService
                .findBibliotecaByEmail(emailBiblioteca);

        if (b != null) {
            esperto.setBiblioteca(b);
        } else {
            esperto.setBiblioteca(toUpdate.getBiblioteca());
            return "modifica_dati_esperto";
        }

        if (!vecchia.isEmpty() && !nuova.isEmpty() && !conferma.isEmpty()) {
            try {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA-256");
                byte[] vecchiaHash = md.digest(vecchia.getBytes());

                if (Arrays.compare(vecchiaHash, toUpdate.getPassword()) == 0
                        && nuova.equals(conferma)
                ) {
                    System.out.println("password giusta");
                    esperto.setPassword(nuova);
                } else {
                    System.out.println("password sbagliata");
                    return "modifica_dati_esperto";
                }

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } else {
            esperto.setHashedPassword(toUpdate.getPassword());
        }


        System.out.println(esperto.getEmail());
        registrazioneService.aggiornaEsperto(esperto);
        model.addAttribute("loggedUser", esperto);
        return "login";
    }

    /**
     * Implementa la funzionalitá di modifica dati di un lettore.
     *
     * @param model Utilizzato per gestire la sessione.
     * @param lettore Un lettore da modificare.
     * @param vecchia La vecchia password dell'account.
     * @param nuova La nuova password dell'account.
     * @param conferma La password di conferma password dell'account.
     *
     * @return login Se la modifica va a buon fine.
     * modifica_dati_lettore Se la modifica non va a buon fine
     */
    @RequestMapping(value = "/conferma-modifica-lettore",
            method = RequestMethod.POST)
    public String confermaModificaLettore(final Model model,
                     final Lettore lettore,
                     final @RequestParam("vecchia_password")String vecchia,
                     final @RequestParam("nuova_password")String nuova,
                     final @RequestParam("conferma_password")String conferma) {


        Lettore toUpdate = registrazioneService
                .findLettoreByEmail(lettore.getEmail());

        if (!vecchia.isEmpty() && !nuova.isEmpty() && !conferma.isEmpty()) {
            try {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA-256");
                byte[] vecchiaHash = md.digest(vecchia.getBytes());

                if (Arrays.compare(vecchiaHash, toUpdate.getPassword()) == 0
                        && nuova.equals(conferma)
                ) {
                    lettore.setPassword(nuova);
                } else {
                    return "modifica_dati_lettore";
                }

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } else {
            lettore.setHashedPassword(toUpdate.getPassword());
        }

        System.out.println(lettore.getEmail());
        registrazioneService.aggiornaLettore(lettore);
        model.addAttribute("loggedUser", lettore);
        return "login";
    }

    /**
     * Implementa la funzionalitá di visualizzazione area utente
     * in base al tipo.
     *
     * @param model
     * @return La view di visualizzazione area utente
     */
    @RequestMapping(value = "/area-utente", method = RequestMethod.GET)
    public String areaUtente(final Model model) {
        UtenteRegistrato utente = (UtenteRegistrato)
                model.getAttribute("loggedUser");

        if (utente != null) {
            if (registrazioneService.isUserBiblioteca(utente)) {
                Biblioteca biblioteca = (Biblioteca) utente;
                model.addAttribute("biblioteca", biblioteca);
                return "visualizza-biblioteca";

            } else if (registrazioneService.isUserEsperto(utente)) {
                Esperto esperto = (Esperto) utente;
                model.addAttribute("esperto", esperto);
                return "visualizza-esperto";

            } else if (registrazioneService.isUserLettore(utente)) {
                Lettore lettore = (Lettore) utente;
                model.addAttribute("lettore", lettore);
                return "visualizza-lettore";

            }
        }
        return "login";
    }
}
