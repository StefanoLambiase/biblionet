
package it.unisa.c07.biblionet.registrazione.service;

import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import java.util.List;

/**
 * @author Alessio Casolaro
 * @author Antonio Della Porta.
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
     * Implementa la funzionalità di trovare un esperto.
     * @param email La mail dell esperto
     * @return L'esperto se c'è, altrimenti null
     */
    Esperto findEspertoByEmail(String email);

    /**
     * Implementa la funzionalità di trovare un lettore.
     * @param email La mail dell lettore
     * @return Il lettore se c'è, altrimenti null
     */
    Lettore findLettoreByEmail(String email);


    /**
     * Implementa la funzionalità di trovare dei generi.
     * @param generi Un'array di nomi di generi da trovare
     * @return Una lista contenente i generi trovati
     */
    List<Genere> findGeneriByName(String[] generi);

    /**
     * Implementa la funzionalità di registrazione una Biblioteca.
     * @param biblioteca La biblioteca da registrare
     * @return L'utente registrato
     */
    UtenteRegistrato registraBiblioteca(Biblioteca biblioteca);

    /**
     * Implementa la funzionalità di registrare un Lettore.
     * @param lettore Il lettore da registrare
     * @return Il lettore registrato
     */
    UtenteRegistrato registraLettore(Lettore lettore);

    /**
     * Implementa la funzionalità di ritorno di un esperto.
     * @param utenteRegistrato L'utente esperto
     * @return L'utente esperto relativo all'utenteRegistrato
     */
    boolean isUserEsperto(UtenteRegistrato utenteRegistrato);

    /**
     * Implementa la funzionalità di ritorno di un lettore.
     * @param utenteRegistrato L'utente lettore
     * @return L'utente lettore relativo all'utenteRegistrato
     */
    boolean isUserLettore(UtenteRegistrato utenteRegistrato);

    /**
     * Implementa la funzionalità di ritorno di una biblioteca.
     * @param utenteRegistrato L'utente biblioteca
     * @return L'utente biblioteca relativo all'utenteRegistrato
     */
    boolean isUserBiblioteca(UtenteRegistrato utenteRegistrato);

    /**
     * Implementa la funzionalità di salvataggio delle modifiche
     * all'account biblioteca.
     * @param utente La biblioteca da aggiornare
     */
    void aggiornaBiblioteca(Biblioteca utente);

    /**
     * Implementa la funzionalità di salvataggio delle modifiche
     * all'account esperto.
     * @param utente L'esperto da aggiornare
     */
    void aggiornaEsperto(Esperto utente);

    /**
     * Implementa la funzionalità di salvataggio delle modifiche
     * all'account lettore.
     * @param utente Lettore da aggiornare
     */
    void aggiornaLettore(Lettore utente);
}
