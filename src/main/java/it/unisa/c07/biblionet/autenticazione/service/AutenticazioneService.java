package it.unisa.c07.biblionet.autenticazione.service;


import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;

/**
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

     /**
      * Firma del metodo che implementa la funzione di logout.
      * @param utenteRegistrato è l'utente a cui far fare logout.
      * @return true se il logout va a buon fine, false altrimenti.
      */
     boolean logout(UtenteRegistrato utenteRegistrato);
}
