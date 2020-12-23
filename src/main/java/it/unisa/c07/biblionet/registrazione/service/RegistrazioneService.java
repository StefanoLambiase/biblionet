
package it.unisa.c07.biblionet.registrazione.service;

import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;

import java.util.List;

public interface RegistrazioneService {
    UtenteRegistrato registraLettore(Lettore lettore);
    UtenteRegistrato registraBiblioteca(Biblioteca biblioteca);
    UtenteRegistrato registraEsperto(Esperto utente);
    Biblioteca findBibliotecaByEmail(String email);

}