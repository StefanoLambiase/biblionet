package it.unisa.c07.biblionet.autenticazione.controller;

import it.unisa.c07.biblionet.autenticazione.service.AutenticazioneService;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
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
public class AreaUtenteController {

    /**
     * Il service per effettuare le operazioni di persistenza.
     */
    private final AutenticazioneService autenticazioneService;

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
            if (autenticazioneService.isBiblioteca(utente)) {
                Biblioteca biblioteca = (Biblioteca) utente;
                model.addAttribute("biblioteca", biblioteca);
                return "area-utente/modifica-dati-biblioteca";

            } else if (autenticazioneService.isEsperto(utente)) {
                Esperto esperto = (Esperto) utente;
                model.addAttribute("esperto", esperto);
                return "area-utente/modifica-dati-esperto";

            } else if (autenticazioneService.isLettore(utente)) {
                Lettore lettore = (Lettore) utente;
                model.addAttribute("lettore", lettore);
                return "area-utente/modifica-dati-lettore";

            }
        }
        return "autenticazione/login";
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


        Biblioteca toUpdate = autenticazioneService
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
                    return "modifica-dati-biblioteca";
                }

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        } else {
            biblioteca.setHashedPassword(toUpdate.getPassword());
        }

        autenticazioneService.aggiornaBiblioteca(biblioteca);
        model.addAttribute("loggedUser", biblioteca);
        return "autenticazione/login";

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


        Esperto toUpdate = autenticazioneService
                .findEspertoByEmail(esperto.getEmail());

        Biblioteca b = autenticazioneService
                .findBibliotecaByEmail(emailBiblioteca);

        if (b != null) {
            esperto.setBiblioteca(b);
        } else {
            esperto.setBiblioteca(toUpdate.getBiblioteca());
            return "area-utente/modifica-dati-esperto";
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
                    return "area-utente/modifica-dati-esperto";
                }

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } else {
            esperto.setHashedPassword(toUpdate.getPassword());
        }


        System.out.println(esperto.getEmail());
        autenticazioneService.aggiornaEsperto(esperto);
        model.addAttribute("loggedUser", esperto);
        return "autenticazione/login";
    }

    /**
     * Implementa la funzionalità di modifica dati di un lettore.
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


        Lettore toUpdate = autenticazioneService
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
                    return "area-utente/modifica-dati-lettore";
                }

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } else {
            lettore.setHashedPassword(toUpdate.getPassword());
        }

        System.out.println(lettore.getEmail());
        autenticazioneService.aggiornaLettore(lettore);
        model.addAttribute("loggedUser", lettore);
        return "autenticazione/login";
    }



}
