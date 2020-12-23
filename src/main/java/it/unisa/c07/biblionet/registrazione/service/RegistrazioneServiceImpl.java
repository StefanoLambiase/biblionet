package it.unisa.c07.biblionet.registrazione.service;

import it.unisa.c07.biblionet.model.dao.utente.LettoreDAO;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrazioneServiceImpl implements RegistrazioneService {

    /**
     * Si occupa delle operazioni CRUD.
     */
    private LettoreDAO lettoreDAO;

    /**
     * Implementa la funzionalitá di registrare un Lettore.
     * @param lettore Il lettore da registrare
     * @return Il lettore registrato
     */
    @Override
    public final UtenteRegistrato registraLettore(final Lettore lettore) {
        return lettoreDAO.save(lettore);
    }

}
