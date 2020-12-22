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
 *Questa classe implementa i metodi di AutenticazioneService.
 */
@Service
@RequiredArgsConstructor
public class AutenticazioneServiceImpl implements AutenticazioneService {


    private final LettoreDAO lettoreDAO;
    private final BibliotecaDAO bibliotecaDAO;
    private final EspertoDAO espertoDAO;
    /**
     * @param email dell'utente.
     * @param password dell'utente.
     * @return un utente registrato.
     */
    @Override
    public UtenteRegistrato login(final String email, final String password, final String tipo) {
        try {
            System.out.println("Sono nel service");
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-256");
            byte[] arr = md.digest(password.getBytes());
                UtenteRegistrato u=null;
            switch (tipo){
                case "Lettore":
                    u = lettoreDAO.findByEmailAndPassword(email,arr);
                    System.out.println(u.getPassword());
                    break;
                case "Biblioteca":
                    u = bibliotecaDAO.findByEmailAndPassword(email,arr);
                    break;
                case "Esperto":
                    u = espertoDAO.findByEmailAndPassword(email,arr);
                    break;
            }
            return u;

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
