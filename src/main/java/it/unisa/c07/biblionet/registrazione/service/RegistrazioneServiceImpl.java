package it.unisa.c07.biblionet.registrazione.service;

import it.unisa.c07.biblionet.model.dao.GenereDAO;
import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.dao.utente.EspertoDAO;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RegistrazioneServiceImpl implements RegistrazioneService{

   final private EspertoDAO espertoDAO;
    final private BibliotecaDAO bibliotecaDAO;
    final private GenereDAO genereDAO;

    @Override
    public UtenteRegistrato registraLettore(Lettore lettore) {
        return null;
    }

    @Override
    public UtenteRegistrato registraEsperto(Esperto esperto) {
        return espertoDAO.save(esperto);
    }

    @Override
    public Biblioteca findBibliotecaByEmail(String email) {

        Optional<UtenteRegistrato> b= bibliotecaDAO.findById(email);
        if(b.isPresent())
            return (Biblioteca) b.get();
        else
            return null;
    }

    @Override
    public List<Genere> findGeneriByName(String[] generi) {
        List<Genere> toReturn= new ArrayList<>();
        for(String g: generi){
            Genere gen=genereDAO.findByName(g);
            if(gen!=null)
                toReturn.add(gen);
        }
        return toReturn;
    }


}
