package it.unisa.c07.biblionet.model.dao.utente;

import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Questa classe rappresenta il DAO di un Esperto.
 */
@Repository
public interface EspertoDAO extends UtenteRegistratoDAO {

    Esperto findByEmailAndPassword(String email, byte[] password);
}
