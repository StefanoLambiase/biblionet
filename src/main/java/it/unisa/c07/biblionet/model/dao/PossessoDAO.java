package it.unisa.c07.biblionet.model.dao;

import it.unisa.c07.biblionet.model.entity.Possesso;
import it.unisa.c07.biblionet.model.entity.compositeKey.PossessoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Questa classe rappresenta il DAO di un Possesso.
 */
@Repository
public interface PossessoDAO extends JpaRepository<Possesso, PossessoId> {
}
