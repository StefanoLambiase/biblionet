package it.unisa.c07.biblionet.registrazione.service;

import it.unisa.c07.biblionet.autenticazione.service.AutenticazioneService;
import it.unisa.c07.biblionet.autenticazione.service.AutenticazioneServiceImpl;
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
     * autenticazione
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
     * Implementa la funzionalità di registrare un Lettore.
     * @param lettore Il lettore da registrare
     * @return Il lettore registrato
     */
    @Override
    public final UtenteRegistrato registraLettore(final Lettore lettore) {
        return lettoreDAO.save(lettore);
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
     * Implementa la funzionalità di trovare una biblioteca.
     * @param email La mail della biblioteca
     * @return La biblioteca se c'è, altrimenti null
     */
    @Override
    public final Biblioteca getBibliotecaByEmail(final String email) {

        return autenticazioneService.findBibliotecaByEmail(email);
    }


}
