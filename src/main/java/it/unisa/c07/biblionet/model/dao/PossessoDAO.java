package it.unisa.c07.biblionet.model.dao;

import it.unisa.c07.biblionet.model.entity.Possesso;
import it.unisa.c07.biblionet.model.entity.compositeKey.PossessoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Questa classe rappresenta il DAO di un Possesso.
 */
@Repository
public interface PossessoDAO extends JpaRepository<Possesso, PossessoId> {

    /**
     * Query custom per il recuper dal DB di una lista dei possessi
     * di una determinata biblioteca identificata dall'ID.
     * @param bibliotecaID L'ID della biblioteca
     * @return La lista di possessi di quella biblioteca
     */
    @Query("SELECT p FROM Possesso p WHERE p.possessoID.bibliotecaID=?1")
    List<Possesso> findByBibliotecaID(String bibliotecaID);
}
