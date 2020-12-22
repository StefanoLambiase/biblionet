package it.unisa.c07.biblionet.model.dao.utente;

import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Questa classe rappresenta il DAO di un Esperto.
 */
@Repository
public interface EspertoDAO extends UtenteRegistratoDAO {

    @Query("SELECT e FROM Esperto e WHERE e.email=?1 AND e.password=?2")
    Esperto login(String email, byte[] password);
}
