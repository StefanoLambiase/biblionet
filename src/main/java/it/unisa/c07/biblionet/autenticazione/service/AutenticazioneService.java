package it.unisa.c07.biblionet.autenticazione.service;


import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;

public interface AutenticazioneService {
    public UtenteRegistrato login(String email, String password);
    public boolean logout(UtenteRegistrato utenteRegistrato);
}
