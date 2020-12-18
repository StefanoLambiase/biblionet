package it.unisa.c07.biblionet;

import it.unisa.c07.biblionet.model.dao.*;
import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.dao.utente.EspertoDAO;
import it.unisa.c07.biblionet.model.dao.utente.LettoreDAO;
import it.unisa.c07.biblionet.model.dao.utente.UtenteRegistratoDAO;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.Libro;
import it.unisa.c07.biblionet.model.entity.Possesso;
import it.unisa.c07.biblionet.model.entity.TicketPrestito;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import org.apache.tomcat.jni.Local;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Scanner;

@SpringBootApplication
public class BiblionetApplication {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        ConfigurableApplicationContext configurableApplicationContext =
                SpringApplication.run(BiblionetApplication.class, args);

        BibliotecaDAO bibliotecaDAO = configurableApplicationContext.getBean(BibliotecaDAO.class);
        EspertoDAO espertoDAO = configurableApplicationContext.getBean(EspertoDAO.class);
        LettoreDAO lettoreDAO = configurableApplicationContext.getBean(LettoreDAO.class);
        ClubDelLibroDAO clubDelLibroDAO = configurableApplicationContext.getBean(ClubDelLibroDAO.class);
        EventoDAO eventoDAO = configurableApplicationContext.getBean(EventoDAO.class);
        GenereDAO genereDAO = configurableApplicationContext.getBean(GenereDAO.class);
        LibroDAO libroDAO = configurableApplicationContext.getBean(LibroDAO.class);
        PossessoDAO possessoDAO = configurableApplicationContext.getBean(PossessoDAO.class);
        TicketPrestitoDAO ticketPrestitoDAO = configurableApplicationContext.getBean(TicketPrestitoDAO.class);

        Biblioteca test = new Biblioteca("TestBiblio@gmail.com","asdlol123","Salerno","Nocera", "Boh","4567894512", "BellaBiblioteca");
        Esperto esperto = new Esperto("esperto@gmail.com","ASDLOL123","Salerno","Bella","Storia","123456123","pippo","Ciccio","Mamma",test);

        bibliotecaDAO.save(test);
        espertoDAO.save(esperto);



        try (Scanner sc = new Scanner(System.in)) {
            sc.next();
            bibliotecaDAO.delete(test);
        }

    }

   /* @Bean
    public ApplicationRunner initializerBiblioteca(BibliotecaDAO bibliotecaDAO) throws NoSuchAlgorithmException {

        MessageDigest md;
        md = MessageDigest.getInstance("SHA-256");

        return args -> bibliotecaDAO.saveAll(Arrays.asList(
                Biblioteca.builder().nomeBiblioteca("Bella Biblioteca").citta("Nocera").provincia("Salerno").via("Via Dalle Palle").email("dino@dino.com").password(md.digest("passwordSicura".getBytes())).recapitoTelefonico("456456456456").build()

        ));
    }

    @Bean
    public ApplicationRunner initializer(EspertoDAO espertoDAO) throws NoSuchAlgorithmException {

        MessageDigest md;
        md = MessageDigest.getInstance("SHA-256");
        Biblioteca b= new Biblioteca();
        b.setEmail("dino@dino.com");
        return args -> espertoDAO.saveAll(Arrays.asList(
                Esperto.builder().nome("Antonio").cognome("DellaPorta").username("Mimmo").biblioteca(b).email("Antonio@gmail.com").citta("Nocera Inferiore").provincia("Salerno").via("Che coas vuoi stalker 46").recapitoTelefonico("4561234564").password(md.digest("passwordSicura".getBytes())).build()
        ));


    }*/


}