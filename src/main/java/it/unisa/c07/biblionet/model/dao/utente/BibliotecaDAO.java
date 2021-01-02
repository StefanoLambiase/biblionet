package it.unisa.c07.biblionet.model.dao.utente;

import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Questa classe rappresenta il DAO di una Biblioteca.
 */
@Repository
public interface BibliotecaDAO extends UtenteRegistratoDAO {
    /**
     * Implementa la funzionalità di ricerca di un utente Biblioteca nel DB.
     * @param email dell'utente da cercare.
     * @param password dell'utente da cercare.
     * @return dell'utente trovato.
     */
    Biblioteca findByEmailAndPassword(String email, byte[] password);

    /**
     * Query custom che recupera dal DB una lista
     * di biblioteche che contengono il nome passato.
     * @param nome Il nome della biblioteca
     * @return Biblioteca trovata
     */
    @Query("SELECT b FROM Biblioteca b " +
            "WHERE UPPER(b.nomeBiblioteca) LIKE UPPER(concat('%', ?1,'%'))")
    List<Biblioteca> findByNome(String nome);

    /**
     * Query custom che recupera dal DB una biblioteca dato il
     * suo id.
     * @param email L'ID della biblioteca
     * @return Biblioteca trovata
     */
    @Query("SELECT b FROM Biblioteca b WHERE b.email=?1")
    Biblioteca findByID(String email);
}
