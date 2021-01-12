package it.unisa.c07.biblionet.model.dao;

import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Questa classe rappresenta il DAO di un Club del Libro.
 */
@Repository
public interface ClubDelLibroDAO extends JpaRepository<ClubDelLibro, Integer> {

    List<ClubDelLibro> findAllByLettori(Lettore lettore);

    public List<ClubDelLibro> findAllByEsperto(Esperto esperto);
}
