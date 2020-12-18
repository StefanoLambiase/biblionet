package it.unisa.c07.biblionet;

import it.unisa.c07.biblionet.model.dao.utente.EspertoDAO;
import it.unisa.c07.biblionet.model.dao.utente.LettoreDAO;
import it.unisa.c07.biblionet.model.dao.utente.UtenteRegistratoDAO;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BiblionetApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext =
                SpringApplication.run(BiblionetApplication.class, args);
/*
        UtenteRegistratoDAO lettoreDAO = configurableApplicationContext.getBean(LettoreDAO.class);
        UtenteRegistratoDAO espertoDAO = configurableApplicationContext.getBean(EspertoDAO.class);

        Lettore lettore = new Lettore(
                "stefano@gmail.com",
                "password",
                "Salerno",
                "Salerno",
                "Torrione",
                "089",
                "Stefano",
                "Stefano",
                "Lambiase");

        Esperto esperto = new Esperto(
                "Antonio@gmail.com",
                "password",
                "Roma",
                "Roma",
                "Romana",
                "089",
                "Antonio",
                "Antonio",
                "DellaPorta");

        lettoreDAO.save(lettore);
        espertoDAO.save(esperto);
        
        System.out.println(lettoreDAO.findById("stefano@gmail.com").get().getCitta());
        System.out.println(espertoDAO.findById("Antonio@gmail.com").get().getCitta());
        */

    }

}
