package it.unisa.c07.biblionet.model.dao.utente;

import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Questa classe rappresenta il DAO di una Biblioteca.
 */
@Repository
public interface BibliotecaDAO extends UtenteRegistratoDAO {

    @Query("SELECT b FROM Biblioteca b WHERE b.email=?1 AND b.password=?2")
    Biblioteca login(String email, byte[] password);
}
