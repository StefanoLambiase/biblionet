package it.unisa.c07.biblionet.autenticazione.service;


import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;

public interface AutenticazioneService {
    public UtenteRegistrato login(String mail, String password);
    public boolean logout(UtenteRegistrato utenteRegistrato);
}
