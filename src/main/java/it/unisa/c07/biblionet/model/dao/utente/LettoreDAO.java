package it.unisa.c07.biblionet.model.dao.utente;

import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import org.springframework.stereotype.Repository;

/**
 * Questa classe rappresenta il DAO di un Lettore.
 */
@Repository
public interface LettoreDAO extends UtenteRegistratoDAO {

    Lettore findByEmailAndPassword(String email, byte[] password);


}
