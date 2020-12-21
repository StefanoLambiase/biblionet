package it.unisa.c07.biblionet.clubDelLibro.service;

import it.unisa.c07.biblionet.model.dao.ClubDelLibroDAO;
import it.unisa.c07.biblionet.model.dao.GenereDAO;
import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.Genere;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
     * Si occupa delle operazioni CRUD.
     */
    private final GenereDAO genereDAO;

    /**
     * Il metodo consente ad un Esperto di creare un Club del Libro.
     * @param club Il Club del Libro da memorizzare
     * @return Il Club del Libro appena creato
     */
    @Override
    public ClubDelLibro creaClubDelLibro(final ClubDelLibro club) {
        return (ClubDelLibro) clubDAO.save(club);
    }

    /**
     * Il metodo consente di visualizzare tutti i club del libro.
     * @return La lista dei club
     */
    @Override
    public List<ClubDelLibro> visualizzaClubsDelLibro() {
        return clubDAO.findAll();
    }

    /**
     * Il metodo serve a recuperare un oggetto
     * della classe genere dato il nome.
     * @param generi Lista dei generi sottoforma di stringa
     * @return Lista dei generi sottoforma di entità
     */
    @Override
    public List<Genere> getGeneri(final List<String> generi) {
        List<Genere> g = new ArrayList<Genere>();
        for (String s : generi) {
            g.add(genereDAO.findByName(s));
        }
        return g;
    }

    /**
     * Il metodo serve a modificare ed
     * effettuare l'update di un club.
     * @param club Il club da modificare
     * @return Il club modificato
     */
    @Override
    public ClubDelLibro modificaDatiClub(final ClubDelLibro club) {
        return (ClubDelLibro) clubDAO.save(club);
    }

    /**
     * Il metodo serve a recuperare un
     * club dato il suo ID.
     * @param id L'ID del club da recuperare
     * @return Il club recuperato
     */
    @Override
    public ClubDelLibro getClubByID(final int id) {
        return (ClubDelLibro) clubDAO.findById(id).get();
    }


}
