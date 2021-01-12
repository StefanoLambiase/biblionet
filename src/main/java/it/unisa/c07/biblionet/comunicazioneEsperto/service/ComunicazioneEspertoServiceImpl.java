package it.unisa.c07.biblionet.comunicazioneEsperto.service;


import it.unisa.c07.biblionet.model.dao.utente.EspertoDAO;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alessio Casolaro
 * @author Antonio Della Porta
 */
@Service
@RequiredArgsConstructor
public class ComunicazioneEspertoServiceImpl implements ComunicazioneEspertoService{
    /**
     * Si occupa delle funzioni CRUD per l'esperto.
     */
    private final EspertoDAO espertoDAO;


    @Override
    public List<Esperto> getEspertiByGeneri(List<Genere> generi) {

        List<Esperto> allEsperti = new ArrayList<>();
        List<Esperto> toReturn = new ArrayList<>();

        for(UtenteRegistrato utente : espertoDAO.findAll()){

            if(utente.getTipo().equals("Esperto"))
                allEsperti.add((Esperto)utente);

        }

        for(Genere g: generi){
            System.out.println(g);
        }

        for(Esperto esperto: allEsperti){
            for(Genere genere : esperto.getGeneri()){

                if(generi.contains(genere) && !toReturn.contains(esperto)) {
                    toReturn.add(esperto);
                }
            }

        }

        return toReturn;
    }
}
