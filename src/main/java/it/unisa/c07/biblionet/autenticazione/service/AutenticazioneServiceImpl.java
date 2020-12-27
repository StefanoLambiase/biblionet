package it.unisa.c07.biblionet.autenticazione.service;

import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.dao.utente.EspertoDAO;
import it.unisa.c07.biblionet.model.dao.utente.LettoreDAO;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
                if ((u = lettoreDAO.findByEmailAndPassword(email,
                                                            arr)) != null) {
                    return u;
                } else if ((u = bibliotecaDAO.findByEmailAndPassword(email,
                        arr)) != null) {
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


}
