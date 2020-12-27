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

     /**
      * Firma del metodo che implementa l'identificazione di un lettore.
      * @param utente registrato che si trova in sessione.
      * @return true se l'utente è un lettore altrimenti false.
      */
     boolean isLettore(UtenteRegistrato utente);

     /**
      * Firma del metodo che implementa l'identificazione di un esperto.
      * @param utente registrato che si trova in sessione.
      * @return true se l'utente è un esperto altrimenti false.
      */
     boolean isEsperto(UtenteRegistrato utente);

     /**
      * Firma del metodo che implementa l'identificazione di una biblioteca.
      * @param utente registrato che si trova in sessione.
      * @return true se l'utente è una biblioteca altrimenti false.
      */
     boolean isBiblioteca(UtenteRegistrato utente);
}
