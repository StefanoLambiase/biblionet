
package it.unisa.c07.biblionet.registrazione.service;

import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;

public interface RegistrazioneService {

    /**
     * Implementa la funzionalitá di registrare un Lettore.
     * @param lettore Il lettore da registrare
     * @return Il lettore registrato
     */
    UtenteRegistrato registraLettore(Lettore lettore);
}
