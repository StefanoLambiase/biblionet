package it.unisa.c07.biblionet.autenticazione.service;

import it.unisa.c07.biblionet.model.dao.utente.UtenteRegistratoDAO;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *Questa classe implementa i metodi di AutenticazioneService.
 */
@Service
@RequiredArgsConstructor
public class AutenticazioneServiceImpl implements AutenticazioneService {

    /**
     * Rapprensenta il DAO dell'utente.
     */
    private final UtenteRegistratoDAO utenteDAO;

    /**
     * @param mail dell'utente.
     * @param password dell'utente.
     * @return un utente registrato.
     */
    @Override
    public UtenteRegistrato login(final String mail, final String password) {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-256");
            byte[] arr = md.digest(password.getBytes());
            return utenteDAO.login(mail, arr);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean logout(UtenteRegistrato utenteRegistrato) {
        return false;
    }
}
