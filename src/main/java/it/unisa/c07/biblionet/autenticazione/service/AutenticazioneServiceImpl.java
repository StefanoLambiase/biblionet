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

    @Override
    public boolean isLettore(UtenteRegistrato utente) {
        if (utente != null)
            return "Lettore".equals(utente.getClass().getSimpleName());
        else
            return false;
    }

    @Override
    public boolean isEsperto(UtenteRegistrato utente) {
        if (utente != null)
            return "Esperto".equals(utente.getClass().getSimpleName());
        else
            return false;
    }

    @Override
    public boolean isBiblioteca(UtenteRegistrato utente) {
        if (utente != null)
            return "Biblioteca".equals(utente.getClass().getSimpleName());
        else
            return false;
    }


}
