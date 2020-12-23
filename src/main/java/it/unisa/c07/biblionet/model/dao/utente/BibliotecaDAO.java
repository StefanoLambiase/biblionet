package it.unisa.c07.biblionet.model.dao.utente;

import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import org.springframework.stereotype.Repository;

/**
 * Questa classe rappresenta il DAO di una Biblioteca.
 */
@Repository
public interface BibliotecaDAO extends UtenteRegistratoDAO {
    /**
     * Implementa la funzionalità di ricerca di un utente Biblioteca nel DB.
     * @param email dell'utente da cercare.
     * @param password dell'utente da cercare.
     * @return dell'utente trovato.
     */
    Biblioteca findByEmailAndPassword(String email, byte[] password);
}
