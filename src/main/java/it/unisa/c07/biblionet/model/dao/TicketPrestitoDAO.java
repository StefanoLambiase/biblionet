package it.unisa.c07.biblionet.model.dao;

import it.unisa.c07.biblionet.model.entity.TicketPrestito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Questa classe rappresenta il DAO di un TicketPrestito.
 */
@Repository
public interface TicketPrestitoDAO
            extends JpaRepository<TicketPrestito, Integer> {

    /**
     * Seleziona la lista dei ticket di una determinata
     * biblioteca.
     * @param bibliotecaEmail L'email della biblioteca
     * @return I ticket della biblioteca
     */
    List<TicketPrestito>
                findAllByBibliotecaEmail(String bibliotecaEmail);
}
