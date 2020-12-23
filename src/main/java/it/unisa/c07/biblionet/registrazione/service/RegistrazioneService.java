
package it.unisa.c07.biblionet.registrazione.service;

import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import java.util.List;

/**
 * @author Alessio Casolaro, Antonio Della Porta.
 */
public interface RegistrazioneService {
    /**
     * Implementa la funzionalità di registrazione un Esperto.
     * @param utente L'Esperto da registrare
     * @return L'utente registrato
     */
    UtenteRegistrato registraEsperto(Esperto utente);

    /**
     * Implementa la funzionalità di trovare una biblioteca.
     * @param email La mail della biblioteca
     * @return La biblioteca se c'è, altrimenti null
     */
    Biblioteca findBibliotecaByEmail(String email);

    /**
     * Implementa la funzionalità di trovare dei generi.
     * @param generi Un'array di nomi di generi da trovare
     * @return Una lista contenente i generi trovati
     */
    List<Genere> findGeneriByName(String[] generi);


}