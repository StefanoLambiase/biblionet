package it.unisa.c07.biblionet.clubDelLibro.service;

import it.unisa.c07.biblionet.model.dao.ClubDelLibroDAO;
import it.unisa.c07.biblionet.model.dao.GenereDAO;
import it.unisa.c07.biblionet.model.dao.utente.LettoreDAO;
import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Implementa la classe che esplicita i metodi
 * definiti nell'interfaccia service per il
 * sottosustema ClubDelLibro.
 * @author Viviana Pentangelo, Gianmario Voria
 */
@Service
@RequiredArgsConstructor
public class ClubDelLibroServiceImpl implements ClubDelLibroService {

    /**
     * Si occupa delle operazioni CRUD per un club.
     */
    private final ClubDelLibroDAO clubDAO;

    /**
     * Si occupa delle operazioni CRUD per un genere.
     */
    private final GenereDAO genereDAO;

    /**
     * Si occupa delle operazioni CRUD per un lettore.
     */
    private final LettoreDAO lettoreDAO;


    /**
     * Implementa la funzionalità che permette
     * ad un Esperto di creare un Club del Libro.
     * @param club Il Club del Libro da memorizzare
     * @return Il Club del Libro appena creato
     */
    @Override
    public ClubDelLibro creaClubDelLibro(final ClubDelLibro club) {
        return clubDAO.save(club);
    }

    /**
     * Implementa la funzionalità che permette
     * di visualizzare tutti i club del libro.
     * @return La lista dei club
     */
    @Override
    public List<ClubDelLibro> visualizzaClubsDelLibro() {
        return this.visualizzaClubsDelLibro(x -> true);
    }

    /**
     * Implementa la funzionalità che permette
     * di filtrare tutti i club del libro.
     * @param filtro Un predicato che descrive come filtrare i Club
     * @return La lista dei club
     */
    public List<ClubDelLibro> visualizzaClubsDelLibro(final Predicate<ClubDelLibro> filtro) {

        var clubs = this.clubDAO.findAll();

        return clubs.stream().filter(
            filtro
        ).collect(Collectors.toList());

    };


    /**
     * Implementa la funzionalità che permette
     * di recuperare un oggetto
     * della classe genere dato il nome.
     * @param generi Lista dei generi sottoforma di stringa
     * @return Lista dei generi sottoforma di entità
     */
    @Override
    public List<Genere> getGeneri(final List<String> generi) {
        List<Genere> g = new ArrayList<>();
        for (String s : generi) {
            g.add(genereDAO.findByName(s));
        }
        return g;
    }

    /**
     * Implementa la funzionalità che permette
     * di modificare ed
     * effettuare l'update di un club.
     * @param club Il club da modificare
     * @return Il club modificato
     */
    @Override
    public ClubDelLibro modificaDatiClub(final ClubDelLibro club) {
        return clubDAO.save(club);
    }

    /**
     * Implementa la funzionalità che permette
     * di recuperare un
     * club dato il suo ID.
     * @param id L'ID del club da recuperare
     * @return Il club recuperato
     */
    @Override
    public ClubDelLibro getClubByID(final int id) {
        Optional<ClubDelLibro> club = clubDAO.findById(id);
        return club.orElse(null);
    }

    /**
     * Implementa la funzionalità che permette
     * ad un lettore di effettuare
     * l'iscrizione ad un club del libro.
     * @param club Il club al quale iscriversi
     * @param lettore Il lettore che si iscrive
     * @return true se è andato a buon fine, false altrimenti
     */
    @Override
    public Boolean partecipaClub(final ClubDelLibro club,
                                 final Lettore lettore) {
        List<ClubDelLibro> listaClubs = lettore.getClubs();
        if (listaClubs == null) {
            listaClubs = new ArrayList<>();
        }
        listaClubs.add(club);
        lettore.setClubs(listaClubs);
        lettoreDAO.save(lettore);
        return true;
    }

    /**
     * Funzione di utilità che permette di leggere la città
     * in cui si trova un Club del Libro.
     * @param club
     * @return
     */
    public String getCittaFromClubDelLibro(final ClubDelLibro club) {
        return club.getEsperto().getBiblioteca().getCitta();
    }

    /**
     * Restituisce tutti i generi nel sistema
     * @return Tutti i generi nel sistema
     */
    public Set<String> getTuttiGeneri() {
        return this.clubDAO.findAll().stream()
                                     .map(ClubDelLibro::getGeneri)
                                     .flatMap(List::stream)
                                     .map(x -> x.getNome())
                                     .collect(Collectors.toSet());
    }

    /**
     * Restituisce tutte le citta nel sistema
     * @return Tutte le citta nel sistema
     */
    public Set<String> getCitta() {
        return this.clubDAO.findAll().stream()
                                     .map(this::getCittaFromClubDelLibro)
                                     .collect(Collectors.toSet());
    }

}
