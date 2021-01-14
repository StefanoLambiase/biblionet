
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
     * Implementa la funzionalità di controllare se una mail è
     * presente già associata ad un altro utente nel database
     * @param email la mail da controllare
     * @return true se la mail è già associata, false altrimenti
     */
    boolean isEmailRegistrata(String email);

    /**
     * Implementa la funzionalità di trovare dei generi.
     * @param generi Un'array di nomi di generi da trovare
     * @return Una lista contenente i generi trovati
     */
    List<Genere> findGeneriByName(String[] generi);

    /**
     * Implementa la funzionalità di prendere una biblioteca
     * data la sua mail, utilizzando il service del package
     * Autenticazione.
     * @param email la mail dell'account
     * @return la biblioteca se presente, null altrimenti
     */
    Biblioteca getBibliotecaByEmail(String email);



}
