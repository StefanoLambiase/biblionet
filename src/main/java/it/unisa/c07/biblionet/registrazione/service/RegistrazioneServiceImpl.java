package it.unisa.c07.biblionet.registrazione.service;

import it.unisa.c07.biblionet.autenticazione.service.AutenticazioneService;
import it.unisa.c07.biblionet.model.dao.GenereDAO;
import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.dao.utente.EspertoDAO;
import it.unisa.c07.biblionet.model.dao.utente.LettoreDAO;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Alessio Casolaro
 * @author Antonio Della Porta.
 */
@Service
@RequiredArgsConstructor
public class RegistrazioneServiceImpl implements RegistrazioneService {

    /**
     * Si occupa di gestire le operazioni CRUD dell'Esperto.
     */
    private final EspertoDAO espertoDAO;

    /**
     * Si occupa di gestire le operazioni CRUD della Biblioteca.
     */
    private final BibliotecaDAO bibliotecaDAO;

    /**
     * Si occupa di gestire le operazioni CRUD del Genere.
     */
    private final GenereDAO genereDAO;

    /**
     * Si occupa delle operazioni CRUD.
     */
    private final LettoreDAO lettoreDAO;

    /**
     *  Si occupa di controllare il tipo di account.
     */
    private final AutenticazioneService autenticazioneService;

    /**
     * Implementa la funzionalità di registrazione un Esperto.
     * @param esperto L'Esperto da registrare
     * @return L'utente registrato
     */
    @Override
    public final UtenteRegistrato registraEsperto(final Esperto esperto) {
        return espertoDAO.save(esperto);
    }

    /**
     * Implementa la funzionalità di registrazione una Biblioteca.
     * @param biblioteca La Biblioteca da registrare
     * @return L'utente registrato
     */
    @Override
    public UtenteRegistrato registraBiblioteca(final Biblioteca biblioteca) {
        return bibliotecaDAO.save(biblioteca);
    }

    /**
     * Implementa la funzionalitá di registrare un Lettore.
     * @param lettore Il lettore da registrare
     * @return Il lettore registrato
     */
    @Override
    public final UtenteRegistrato registraLettore(final Lettore lettore) {
        return lettoreDAO.save(lettore);
    }

    /**
     * Implementa la funziolitá di ritorno di un esperto.
     * @param utenteRegistrato L'utente esperto
     * @return L'utente esperto relativo all'utenteRegistrato
     */
    @Override
    public final boolean isUserEsperto(
            final UtenteRegistrato utenteRegistrato) {
        return autenticazioneService.isEsperto(utenteRegistrato);
    }

    /**
     * Implementa la funziolitá di ritorno di un lettore.
     * @param utenteRegistrato L'utente lettore
     * @return L'utente lettore relativo all'utenteRegistrato
     */
    @Override
    public final boolean isUserLettore(
            final UtenteRegistrato utenteRegistrato) {
        return autenticazioneService.isLettore(utenteRegistrato);
    }

    /**
     * Implementa la funziolitá di ritorno di una biblioteca.
     * @param utenteRegistrato L'utente biblioteca
     * @return L'utente biblioteca relativo all'utenteRegistrato
     */
    @Override
    public final boolean isUserBiblioteca(
            final UtenteRegistrato utenteRegistrato) {
        return autenticazioneService.isBiblioteca(utenteRegistrato);
    }

    /**
     * Implementa la funzionalità di trovare una biblioteca.
     * @param email La mail della biblioteca
     * @return La biblioteca se c'è, altrimenti null
     */
    @Override
    public final Biblioteca findBibliotecaByEmail(final String email) {

        Optional<UtenteRegistrato> b = bibliotecaDAO.findById(email);
        if (b.isPresent()) {
            return (Biblioteca) b.get();
        } else {
            return null;
        }
    }

    /**
     * Implementa la funzionalità di trovare un esperto.
     * @param email La mail dell esperto
     * @return L'esperto se c'è, altrimenti null
     */
    @Override
    public final Esperto findEspertoByEmail(final String email) {

        Optional<UtenteRegistrato> b = espertoDAO.findById(email);
        if (b.isPresent()) {
            return (Esperto) b.get();
        } else {
            return null;
        }
    }

    /**
     * Implementa la funzionalità di trovare un lettore.
     * @param email La mail dell lettore
     * @return Il lettore se c'è, altrimenti null
     */
    @Override
    public final Lettore findLettoreByEmail(final String email) {

        Optional<UtenteRegistrato> b = lettoreDAO.findById(email);
        if (b.isPresent()) {
            return (Lettore) b.get();
        } else {
            return null;
        }
    }

    /**
     * Implementa la funzionalità di trovare dei generi.
     * @param generi Un'array di nomi di generi da trovare
     * @return Una lista contenente i generi trovati
     */
    @Override
    public final List<Genere> findGeneriByName(final String[] generi) {
        List<Genere> toReturn = new ArrayList<>();
        for (String g: generi) {
            Genere gen = genereDAO.findByName(g);
            if (gen != null) {
                toReturn.add(gen);
            }

        }
        return toReturn;
    }

    /**
     * Implementa la funziolitá di salvataggio delle modifiche
     * all'account biblioteca.
     * @param utente La biblioteca da aggiornare
     */
    public void aggiornaBiblioteca(final Biblioteca utente) {
        bibliotecaDAO.save(utente);
    }

    /**
     * Implementa la funziolitá di salvataggio delle modifiche
     * all'account esperto.
     * @param utente L'esperto da aggiornare
     */
    public void aggiornaEsperto(final Esperto utente) {
        espertoDAO.save(utente);
    }

    /**
     * Implementa la funziolitá di salvataggio delle modifiche
     * all'account lettore.
     * @param utente Lettore da aggiornare
     */
    public void aggiornaLettore(final Lettore utente) {
        lettoreDAO.save(utente);
    }
}
