package it.unisa.c07.biblionet.model.dao;

import it.unisa.c07.biblionet.model.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Questa classe rappresenta il DAO di un Libro.
 */
@Repository
public interface LibroDAO extends JpaRepository<Libro, Integer> {

    @Query("SELECT l FROM Libro l WHERE l.titolo LIKE %?1%")
    List<Libro> findByTitoloLike(String titolo);
}
