package it.unisa.c07.biblionet.registrazione.service;

import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;

public interface RegistrazioneService {
    UtenteRegistrato registraLettore(Lettore lettore);
    UtenteRegistrato registraBiblioteca(Biblioteca biblioteca);
}
