package it.unisa.c07.biblionet.model.dao.utente;

import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Questa classe rappresenta il DAO di un Lettore.
 */
@Repository
public interface LettoreDAO extends UtenteRegistratoDAO {

    @Query("SELECT l FROM Lettore l JOIN UtenteRegistrato u WHERE l.email=?1 AND u.email = l.email AND u.password=?2")
    Lettore login(String email, byte[] password);
}
