package it.unisa.c07.biblionet.model.dao.utente;

import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Questa classe rappresenta il DAO di un Utente Registrato, usato
 * per estendere gli attori core del sistema.
 */
@NoRepositoryBean
public interface UtenteRegistratoDAO
        extends JpaRepository<UtenteRegistrato, String> {

}
