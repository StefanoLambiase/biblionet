package it.unisa.c07.biblionet.model.dao;

import it.unisa.c07.biblionet.model.entity.Genere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Questa classe rappresenta il DAO di un Genere.
 */
@Repository
public interface GenereDAO extends JpaRepository<Genere, String> {
}
