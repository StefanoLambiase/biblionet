package it.unisa.c07.biblionet;

import it.unisa.c07.biblionet.model.dao.*;
import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.dao.utente.EspertoDAO;
import it.unisa.c07.biblionet.model.dao.utente.LettoreDAO;
import it.unisa.c07.biblionet.model.dao.utente.UtenteRegistratoDAO;
import it.unisa.c07.biblionet.model.entity.*;
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

//----------------------------Definizione oggetti DAO per ogni entity-----------------------------------------

        BibliotecaDAO bibliotecaDAO = configurableApplicationContext.getBean(BibliotecaDAO.class);
        EspertoDAO espertoDAO = configurableApplicationContext.getBean(EspertoDAO.class);
        LettoreDAO lettoreDAO = configurableApplicationContext.getBean(LettoreDAO.class);
        ClubDelLibroDAO clubDelLibroDAO = configurableApplicationContext.getBean(ClubDelLibroDAO.class);
        EventoDAO eventoDAO = configurableApplicationContext.getBean(EventoDAO.class);
        GenereDAO genereDAO = configurableApplicationContext.getBean(GenereDAO.class);
        LibroDAO libroDAO = configurableApplicationContext.getBean(LibroDAO.class);
        //PossessoDAO possessoDAO = configurableApplicationContext.getBean(PossessoDAO.class);
        TicketPrestitoDAO ticketPrestitoDAO = configurableApplicationContext.getBean(TicketPrestitoDAO.class);

//------------------------------Definizione oggetti per popolamento Database------------------------------------------

        Biblioteca biblioteca = new Biblioteca(
                "bibliotecacarrisi@gmail.com",
                "BibliotecaPassword",
                "Napoli",
                "Torre del Greco",
                "Via Carrisi 47",
                "1234567890",
                "Biblioteca Carrisi"
        );

        bibliotecaDAO.save(biblioteca);

        Esperto esperto = new Esperto(
                "eliaviviani@gmail.com",
                "EspertoPassword",
                "Napoli",
                "Torre del Greco",
                "Via Roma 2",
                "2345678901",
                "Espertissimo",
                "Elia",
                "Viviani",
                biblioteca
        );

        espertoDAO.save(esperto);

        Lettore lettore = new Lettore(
                "giuliociccone@gmail.com",
                "LettorePassword",
                "Salerno",
                "Baronissi",
                "Via Barone 11",
                "3456789012",
                "SuperLettore",
                "Giulio",
                "Ciccone"
        );

        lettoreDAO.save(lettore);

        Genere genere = new Genere(
                "Fantasy",
                "Genere fantastico"
        );

        genereDAO.save(genere);

        Libro libro = new Libro(
                "BiblioNet",
                "Stefano Lambiase",
                "1234567890123",
                LocalDateTime.now(),
                "Biblioteche 2.0",
                "Mondadori"

        );

        libroDAO.save(libro);

        TicketPrestito ticket = new TicketPrestito(
                TicketPrestito.stati.CREATO,
                LocalDateTime.now(),
                LocalDateTime.of(LocalDate.of(2020,12,25), LocalTime.of(12,25,12)),
                libro,
                biblioteca,
                lettore
        );

        ticketPrestitoDAO.save(ticket);

        ClubDelLibro clubDelLibro = new ClubDelLibro(
                "Club Fantasy",
                "Si parla di libri fantasy",
                esperto
        );

        clubDelLibroDAO.save(clubDelLibro);

        Evento evento = new Evento(
                "Evento fantasy",
                "Evento fantasy",
                LocalDateTime.now(),
                clubDelLibro
        );

        eventoDAO.save(evento);

        //Aspetto per un input da linea di comando
        Scanner sc = new Scanner(System.in);
        System.out.println("Inserire un qualsiasi testo per eseguire un aggiornamento e premere Invio");
        sc.next();

        //Eseguo un aggiornamento di un esperto, associandogli un genere
        esperto.setGeneri(Arrays.asList(genere));
        espertoDAO.save(esperto);




    }
}