package it.unisa.c07.biblionet.comunicazioneEsperto.service;

import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;

import java.util.List;

/**
 * @author Alessio Casolaro
 * @author Antonio Della Porta
 */
public interface ComunicazioneEspertoService {

    //POSSO COPIARE IL GET GENERI???
    List<Esperto> getEspertiByGeneri(List<Genere> generi);
}
