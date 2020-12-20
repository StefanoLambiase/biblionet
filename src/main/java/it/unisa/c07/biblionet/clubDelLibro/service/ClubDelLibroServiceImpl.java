package it.unisa.c07.biblionet.clubDelLibro.service;

import it.unisa.c07.biblionet.model.dao.ClubDelLibroDAO;
import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Viviana Pentangelo, Gianmario Voria
 */
@Service
@RequiredArgsConstructor
public class ClubDelLibroServiceImpl implements ClubDelLibroService {

    /**
     * Si occupa delle operazioni CRUD.
     */
    private final ClubDelLibroDAO clubDAO;

    /**
     * Il metodo consente ad un Esperto di creare un Club del Libro.
     * @param club Il Club del Libro da memorizzare
     * @return Il Club del Libro appena creato
     */
    @Override
    public ClubDelLibro creaClubDelLibro(final ClubDelLibro club) {
        return (ClubDelLibro) clubDAO.save(club);
    }
}
