package it.unisa.c07.biblionet.registrazione.service;

import it.unisa.c07.biblionet.model.dao.GenereDAO;
import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.dao.utente.EspertoDAO;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Alessio Casolaro, Antonio Della Porta.
 */
@Service
@RequiredArgsConstructor
public class RegistrazioneServiceImpl implements RegistrazioneService {

    /**
     * Si occupa di gestire le operazioni CRUD dell'Esperto.
     */
    private final EspertoDAO espertoDAO;

    /**
     * Si occupa di gestire le operazioni CRUD della Biblioteca.
     */
    private final BibliotecaDAO bibliotecaDAO;

    /**
     * Si occupa di gestire le operazioni CRUD del Genere.
     */
    private final GenereDAO genereDAO;


    /**
     * Implementa la funzionalità di registrazione un Esperto.
     * @param esperto L'Esperto da registrare
     * @return L'utente registrato
     */
    @Override
    public final UtenteRegistrato registraEsperto(final Esperto esperto) {
        return espertoDAO.save(esperto);
    }

    /**
     * Implementa la funzionalità di trovare una biblioteca.
     * @param email La mail della biblioteca
     * @return La biblioteca se c'è, altrimenti null
     */
    @Override
    public final Biblioteca findBibliotecaByEmail(final String email) {

        Optional<UtenteRegistrato> b = bibliotecaDAO.findById(email);
        if (b.isPresent()) {
            return (Biblioteca) b.get();
        }
        else {
            return null;
        }
    }

    /**
     * Implementa la funzionalità di trovare dei generi
     * @param generi Un'array di nomi di generi da trovare
     * @return Una lista contenente i generi trovati
     */
    @Override
    public final List<Genere> findGeneriByName(final String[] generi) {
        List<Genere> toReturn = new ArrayList<>();
        for (String g: generi) {
            Genere gen = genereDAO.findByName(g);
            if (gen != null) {
                toReturn.add(gen);
            }

        }
        return toReturn;
    }


}
