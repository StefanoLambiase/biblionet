package it.unisa.c07.biblionet.preferenzeDiLettura.controller;

import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.preferenzeDiLettura.service.PreferenzeDiLetturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Alessio Casolaro
 * @author Antonio Della Porta
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/preferenze-di-lettura")
public class PreferenzeDiLetturaController {

    /**
     * Il service per effettuare le operazioni di persistenza.
     */
    private final PreferenzeDiLetturaService preferenzeDiLetturaService;

    /**
     * Implementa la funzionalità di inserimento di generi letterari
     * preferiti di un esperto.
     * @param esperto l'esperto a cui inserire i generi
     * @param generi i nomi dei generi da eseguire
     * @return la view della home
     */
    @RequestMapping(value = "/inserisci-generi-esperto",
                                              method = RequestMethod.POST)
    public String inserimentoConoscenzeLetturaEsperto(final Esperto esperto,
                              final @RequestParam("genere")String[] generi) {

        preferenzeDiLetturaService.addGeneriEsperto(
                preferenzeDiLetturaService.getGeneriByName(generi),
                                                           esperto);
        return "index";

    }





}
