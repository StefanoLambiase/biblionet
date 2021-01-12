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
        List<UtenteRegistrato> allEsperti = espertoDAO.findAll();
        List<Esperto> toReturn = new ArrayList<>();
        for(UtenteRegistrato u: allEsperti){
            Esperto e = (Esperto) u;
            List<Genere> generiEsperto = e.getGeneri();
            for(Genere g : generiEsperto){
                if(generiEsperto.contains(g)&&!toReturn.contains(e))
                    toReturn.add(e);
            }
        }
        return toReturn;
    }
}
