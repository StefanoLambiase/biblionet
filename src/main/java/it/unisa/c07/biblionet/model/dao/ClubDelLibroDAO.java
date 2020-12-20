package it.unisa.c07.biblionet.model.dao;

import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Questa classe rappresenta il DAO di un Club del Libro.
 */
@Repository
public interface ClubDelLibroDAO extends JpaRepository<ClubDelLibro, Integer> {

}
