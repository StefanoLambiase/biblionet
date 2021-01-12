package it.unisa.c07.biblionet.model.dao.utente;

import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import org.springframework.stereotype.Repository;

/**
 * Questa classe rappresenta il DAO di un Esperto.
 */
@Repository
public interface EspertoDAO extends UtenteRegistratoDAO {

    /**
     * Implementa la funzionalità di ricerca di un utente Esperto nel DB.
     * @param email dell'utente da cercare.
     * @param password dell'utente da cercare.
     * @return dell'utente trovato.
     */
    Esperto findByEmailAndPassword(String email, byte[] password);
}
