package it.unisa.c07.biblionet.autenticazione.service;

import it.unisa.c07.biblionet.clubDelLibro.service.ClubDelLibroService;
import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.dao.utente.EspertoDAO;
import it.unisa.c07.biblionet.model.dao.utente.LettoreDAO;
import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

/**
 *Implementa la classe che esplicita i metodi
 * definiti nell'interfaccia service per il
 * sottosistema Autenticazione.
 * @author Ciro Maiorino , Giulio Triggiani
 */
@Service
@RequiredArgsConstructor
public class AutenticazioneServiceImpl implements AutenticazioneService {

    /**
     *Si occupa delle operazioni CRUD per un lettore.
     */
    private final LettoreDAO lettoreDAO;

    /**
     * Si occupa delle operazioni CRUD per una biblioteca.
     */
    private final BibliotecaDAO bibliotecaDAO;

    /**
     * Si occupa delle operazioni CRUD un esperto.
     */
    private final EspertoDAO espertoDAO;

    /**
     * I.
     */
    private final ClubDelLibroService clubDelLibroService;

    /**
     * Implementa la funzionalità di login
     * per un Utente registrato.
     * @param email dell'utente.
     * @param password dell'utente.
     * @return un utente registrato.
     */
    @Override
    public UtenteRegistrato login(final String email, final String password) {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-256");
            byte[] arr = md.digest(password.getBytes());
            UtenteRegistrato u;

            if ((u = lettoreDAO.findByEmailAndPassword(email, arr)) != null) {
                return u;
            } else if ((u =
                    bibliotecaDAO.findByEmailAndPassword(email, arr)) != null) {
                return u;
            } else {
                u = espertoDAO.findByEmailAndPassword(email, arr);
                return u;
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Implementa la funzionalità di
     * identifica di un utente in sessione.
     * @param utente registrato che si trova già in sessione.
     * @return true se l'utente è un lettore altrimenti false.
     */
    @Override
    public boolean isLettore(final UtenteRegistrato utente) {
        return "Lettore".equals(utente.getClass().getSimpleName());
    }

    /**
     * Implementa la funzionalità di
     * identifica di un utente in sessione.
     * @param utente registrato che si trova già in sessione.
     * @return true se l'utente è un esperto altrimenti false.
     */
    @Override
    public boolean isEsperto(final UtenteRegistrato utente) {
        return "Esperto".equals(utente.getClass().getSimpleName());
    }

    /**
     * Implementa la funzionalità di
     * identifica di un utente in sessione.
     * @param utente registrato che si trova già in sessione.
     * @return true se l'utente è una biblioteca altrimenti false.
     */
    @Override
    public boolean isBiblioteca(final UtenteRegistrato utente) {
        return "Biblioteca".equals(utente.getClass().getSimpleName());
    }

    /**
     * Implementa la funzionalità di salvataggio delle modifiche
     * all'account biblioteca.
     * @param utente La biblioteca da aggiornare
     * @return la biblioteca aggiornata
     */
    public Biblioteca aggiornaBiblioteca(final Biblioteca utente) {
        return bibliotecaDAO.save(utente);
    }

    /**
     * Implementa la funzionalità di salvataggio delle modifiche
     * all'account esperto.
     * @param utente L'esperto da aggiornare
     * @return l'esperto aggiornato
     */
    public Esperto aggiornaEsperto(final Esperto utente) {
        return espertoDAO.save(utente);
    }

    /**
     * Implementa la funzionalità di salvataggio delle modifiche
     * all'account lettore.
     * @param utente Lettore da aggiornare
     * @return il lettore aggiornato
     */
    public Lettore aggiornaLettore(final Lettore utente) {
        return lettoreDAO.save(utente);
    }

    /**
     * Implementa la funzionalità di trovare una biblioteca.
     * @param email La mail della biblioteca
     * @return La biblioteca se c'è, altrimenti null
     */
    @Override
    public final Biblioteca findBibliotecaByEmail(final String email) {

        Optional<UtenteRegistrato> b = bibliotecaDAO.findById(email);
        return (Biblioteca) b.orElse(null);
    }

    /**
     * Implementa la funzionalità di trovare un esperto.
     * @param email La mail dell esperto
     * @return L'esperto se c'è, altrimenti null
     */
    @Override
    public final Esperto findEspertoByEmail(final String email) {

        Optional<UtenteRegistrato> b = espertoDAO.findById(email);
        return (Esperto) b.orElse(null);
    }

    /**
     * Implementa la funzionalità di trovare un lettore.
     * @param email La mail dell lettore
     * @return Il lettore se c'è, altrimenti null
     */
    @Override
    public final Lettore findLettoreByEmail(final String email) {

        Optional<UtenteRegistrato> b = lettoreDAO.findById(email);
        return (Lettore) b.orElse(null);
    }

    /**
     * Implementa la funzionalità di prendere una lista di club
     * del libro a cui un lettore partecipa.
     * @param lettore il lettore preso in esame
     * @return la lista dei club del libro a cui partecipa
     */
    @Override
    public List<ClubDelLibro> findAllByLettori(final Lettore lettore) {
        return clubDelLibroService.findAllByLettori(lettore);
    }

    /**
     * Implementa la funzionalità di prendere una lista di club
     * del libro di cui un esperto è proprietario.
     * @param esperto l' esperto preso in esame
     * @return la lista dei club del libro a cui partecipa
     */
    @Override
    public List<ClubDelLibro> findAllByEsperto(final Esperto esperto) {
        return clubDelLibroService.findAllByEsperto(esperto);
    }

}
