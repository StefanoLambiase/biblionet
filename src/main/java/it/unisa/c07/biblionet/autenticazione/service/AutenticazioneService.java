package it.unisa.c07.biblionet.autenticazione.service;


import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;

/**
 * @author Ciro Maiorino , Giulio Triggiani
 * Interfaccia per i metodi del sottosistema Autenticazione.
 */
public interface AutenticazioneService {
     /**
      * Firma del metodo che implementa la funzione di login.
      * @param email dell'utente da loggare.
      * @param password dell'utente da loggare.
      * @return dell'utente da loggato.
      */
     UtenteRegistrato login(String email, String password);
}
