package it.unisa.c07.biblionet.comunicazioneEsperto.service;

import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;

import java.util.List;

/**
 * @author Alessio Casolaro
 * @author Antonio Della Porta
 */
public interface ComunicazioneEspertoService {

    /**
     * Implementa la funzionalità di cercare degli esperti che hanno
     * come generi preferiti quelli passati.
     * @param generi i generi da cercare
     * @return la lista di esperti
     */
    List<Esperto> getEspertiByGeneri(List<Genere> generi);
}
