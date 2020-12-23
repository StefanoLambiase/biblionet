package it.unisa.c07.biblionet.registrazione.service;

import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.dao.utente.EspertoDAO;
import it.unisa.c07.biblionet.model.dao.utente.LettoreDAO;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RegistrazioneServiceImpl implements RegistrazioneService{

    final private EspertoDAO espertoDAO;
    final private BibliotecaDAO bibliotecaDAO;
    final private LettoreDAO lettoreDAO;

    @Override
    public UtenteRegistrato registraLettore(Lettore lettore) {
        return lettoreDAO.save(lettore);
    }

    @Override
    public UtenteRegistrato registraEsperto(Esperto esperto) {
        return espertoDAO.save(esperto);
    }

    @Override
    public UtenteRegistrato registraBiblioteca(Biblioteca biblioteca) {
        return bibliotecaDAO.save(biblioteca);
    }

    @Override
    public Biblioteca findBibliotecaByEmail(String email) {

        Optional<UtenteRegistrato> b= bibliotecaDAO.findById(email);
        if(b.isPresent())
            return (Biblioteca) b.get();
        else
            return null;
    }
}