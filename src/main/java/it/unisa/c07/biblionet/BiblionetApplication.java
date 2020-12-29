package it.unisa.c07.biblionet;

import it.unisa.c07.biblionet.model.dao.PossessoDAO;
import it.unisa.c07.biblionet.model.dao.TicketPrestitoDAO;
import it.unisa.c07.biblionet.model.dao.LibroDAO;
import it.unisa.c07.biblionet.model.dao.GenereDAO;
import it.unisa.c07.biblionet.model.dao.EventoDAO;
import it.unisa.c07.biblionet.model.dao.ClubDelLibroDAO;
import it.unisa.c07.biblionet.model.dao.utente.BibliotecaDAO;
import it.unisa.c07.biblionet.model.dao.utente.EspertoDAO;
import it.unisa.c07.biblionet.model.dao.utente.LettoreDAO;
import it.unisa.c07.biblionet.model.entity.Possesso;
import it.unisa.c07.biblionet.model.entity.Libro;
import it.unisa.c07.biblionet.model.entity.TicketPrestito;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.Evento;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.compositeKey.PossessoId;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

/**
 * Questa è la main class del progetto, che fa partire l'applicazione e popola
 * il database.
 */
@SpringBootApplication
public class BiblionetApplication {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        ConfigurableApplicationContext configurableApplicationContext =
                SpringApplication.run(BiblionetApplication.class, args);

//----------------------------Definizione oggetti DAO per ogni entity---------------------------------------------------

        BibliotecaDAO bibliotecaDAO = configurableApplicationContext.getBean(BibliotecaDAO.class);
        EspertoDAO espertoDAO = configurableApplicationContext.getBean(EspertoDAO.class);
        LettoreDAO lettoreDAO = configurableApplicationContext.getBean(LettoreDAO.class);
        ClubDelLibroDAO clubDelLibroDAO = configurableApplicationContext.getBean(ClubDelLibroDAO.class);
        EventoDAO eventoDAO = configurableApplicationContext.getBean(EventoDAO.class);
        GenereDAO genereDAO = configurableApplicationContext.getBean(GenereDAO.class);
        LibroDAO libroDAO = configurableApplicationContext.getBean(LibroDAO.class);
        PossessoDAO possessoDAO = configurableApplicationContext.getBean(PossessoDAO.class);
        TicketPrestitoDAO ticketPrestitoDAO = configurableApplicationContext.getBean(TicketPrestitoDAO.class);

//------------------------------Definizione oggetti per popolamento Database--------------------------------------------


//-------------------------------Definizione ed inserimento Biblioteche-------------------------------------------------
        Biblioteca biblioteca = new Biblioteca(
                "bibliotecacarrisi@gmail.com",
                "BibliotecaPassword",
                "Napoli",
                "Torre del Greco",
                "Via Carrisi 47",
                "1234567890",
                "Biblioteca Carrisi"
        );

        Biblioteca biblioteca1 = new Biblioteca(
                "feltrinellipezzotta@gmail.com",
                "FabioGrosso2006",
                "Napoli",
                "Torre del Greco",
                "Via Rivoluzione Francese 70",
                "3331245741",
                "Better Than Feltrinelli"
        );

        Biblioteca biblioteca2 = new Biblioteca(
                "stellina04@live.com",
                "OneDirectionUnicoAmore1!",
                "Milano",
                "Milano",
                "Via Lambrate 47",
                "3310214754",
                "Biblioteca Father and Son"
        );

        Biblioteca biblioteca3 = new Biblioteca(
                "gmail@gmail.com",
                "Ueuagliobellstuorolog69",
                "Napoli",
                "Scampia",
                "Via Portici 47",
                "3341278415",
                "Vieni che non ti faccio niente bookstore"
        );

        Biblioteca biblioteca4 = new Biblioteca(
                "b4@gmail.com",
                "aaaaa",
                "Napoli",
                "Scampia",
                "Via Portici 47",
                "3341278415",
                "Naboli"
        );

        bibliotecaDAO.save(biblioteca);
        bibliotecaDAO.save(biblioteca1);
        bibliotecaDAO.save(biblioteca2);
        bibliotecaDAO.save(biblioteca3);
        bibliotecaDAO.save(biblioteca4);

//----------------------Definizione ed inserimento esperti--------------------------------------------------------------


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

        Esperto esperto1 = new Esperto(
                "ciro@maiorino.com",
                "ILoveHentai<3",
                "Salerno",
                "Pagani",
                "Via Roma 2",
                "2345678901",
                "miann",
                "Ciro",
                "Maiorino",
                biblioteca
        );

        Esperto esperto2 = new Esperto(
                "giulio@triggianispa.com",
                "TheBest99!",
                "Napoli",
                "Torre del Greco",
                "Via CasaDiGiulio 2",
                "3127403698",
                "Rayleigh",
                "Giulio",
                "Triggiani",
                biblioteca1
        );

        Esperto esperto3 = new Esperto(
                "luca@zekromaster.com",
                "ComunismoMerda4Ever",
                "Napoli",
                "Ischia",
                "Via Roma 2",
                "3912479852",
                "Espertissimo",
                "Luca",
                "Topo",
                biblioteca1
        );

        Esperto esperto4 = new Esperto(
                "viviana@pentangelo.ass",
                "Dodiiiiiiiiiiiiiiiii",
                "Salerno",
                "Pagani",
                "Piazza S.Alfonso 2",
                "3789612345",
                "theLogoOwner",
                "Viviana",
                "Pentangelo",
                biblioteca2
        );

        Esperto esperto5 = new Esperto(
                "gianmario@blacklivesmatter.com",
                "MartinLutherKingDream",
                "Salerno",
                "Pallinuro",
                "Via Dalle Palle 2",
                "3961234784",
                "nero01",
                "Gianmario",
                "Voria",
                biblioteca2
        );

        Esperto esperto6 = new Esperto(
                "stefano@lambiase.com",
                "TheProjectMaster",
                "Salerno",
                "Salerno",
                "Via vicino casa di Stefano 1",
                "2345678901",
                "thePM",
                "Stefano",
                "Lambiase",
                biblioteca2
        );

        Esperto esperto7 = new Esperto(
                "alessio@casolare.com",
                "AccattTuttCos",
                "Napoli",
                "Somma Vesuviana",
                "Via Vesuvio 69",
                "3336977841",
                "napolethanos",
                "Alessio",
                "Casolaro",
                biblioteca3
        );

        Esperto esperto8 = new Esperto(
                "antonio@sushilover.it",
                "Imababygirlinababyworld",
                "Salerno",
                "Nocera Inferiore",
                "Via Fatti i cazzi tuoi 2",
                "3963636963",
                "possessoResolver",
                "Antonio",
                "Della Porta",
                biblioteca3
        );

        Esperto esperto9 = new Esperto(
                "drink@home.com",
                "ALotOfBeerInMyLife",
                "Salerno",
                "Salerno",
                "Via vicino casa di Stefano 2",
                "3694578963",
                "mrDuff",
                "Nicola",
                "Pagliara",
                biblioteca3
        );


        espertoDAO.save(esperto);
        espertoDAO.save(esperto1);
        espertoDAO.save(esperto2);
        espertoDAO.save(esperto3);
        espertoDAO.save(esperto4);
        espertoDAO.save(esperto5);
        espertoDAO.save(esperto6);
        espertoDAO.save(esperto7);
        espertoDAO.save(esperto8);
        espertoDAO.save(esperto9);


//----------------------Definizione ed inserimento lettori--------------------------------------------------------------

        Lettore lettore = new Lettore(
                "giuliociccione@gmail.com",
                "LettorePassword",
                "Salerno",
                "Baronissi",
                "Via Barone 11",
                "3456789012",
                "SuperLettore",
                "Giulio",
                "Ciccione"
        );

        Lettore lettore1 = new Lettore(
                "francescone@gmail.com",
                "BellaFrate",
                "Salerno",
                "Baronissi",
                "Via Gemma 11",
                "3451349012",
                "kekkobomba",
                "Francesco",
                "Broski"
        );

        Lettore lettore2 = new Lettore(
                "jd@gmail.com",
                "ILoveCox",
                "Salerno",
                "Fisciano",
                "Via Hospedale 19",
                "3451343794",
                "jd",
                "John",
                "Dorian"
        );

        Lettore lettore3 = new Lettore(
                "cox@gmail.com",
                "OdioTutti",
                "Napoli",
                "Mergellina",
                "Via Bertino 780",
                "3459013412",
                "coxBest",
                "Mr",
                "Cox"
        );

        lettoreDAO.save(lettore);
        lettoreDAO.save(lettore1);
        lettoreDAO.save(lettore2);
        lettoreDAO.save(lettore3);

//----------------------Definizione ed inserimento generi---------------------------------------------------------------

        Genere genere = new Genere(
                "Fantasy",
                "Genere fantastico"
        );

        Genere genere1 = new Genere(
                "Azione",
                "Genere molto movimentato"
        );

        Genere genere2 = new Genere(
                "Space",
                "Genere spaziale"
        );

        Genere genere3 = new Genere(
                "Autobiografico",
                "Genere introspettivo"
        );

        Genere genere4 = new Genere(
                "Politico",
                "Genere interessante se vuoi sapere come rubare 59 milioni di euro dalle casse dello stato"
        );

        genereDAO.save(genere);
        genereDAO.save(genere1);
        genereDAO.save(genere2);
        genereDAO.save(genere3);
        genereDAO.save(genere4);

//----------------------Definizione ed inserimento libri----------------------------------------------------------------

        Libro libro = new Libro(
                "BiblioNet",
                "Stefano Lambiase",
                "1234567890123",
                LocalDateTime.now(),
                "Biblioteche 2.0",
                "Mondadori"

        );

        Libro libro1 = new Libro(
                "Amore Amaro",
                "Fru",
                "9597845613497",
                LocalDateTime.of(LocalDate.of(2010,10,15),LocalTime.now()),
                "Biblioteche 2.0",
                "Mondadori"

        );

        Libro libro2 = new Libro(
                "La vita è una cascata: A Ciro Maiorino Story",
                "Ciro Maiorino",
                "9597845613496",
                LocalDateTime.of(LocalDate.of(2015,10,15),LocalTime.now()),
                "La vita fa schifo",
                "NicolaPagliara"

        );

        Libro libro3 = new Libro(
                "Come smettere di giocare a LoL: A failure story by Ciro Maiorino",
                "Ciro Maiorino",
                "9597845613467",
                LocalDateTime.of(LocalDate.of(2017,1,15),LocalTime.now()),
                "LoL fa schifo",
                "NicolaPagliara"

        );

        Libro libro4 = new Libro(
                "Come bere tanto e ricordarsi il proprio nome",
                "Nicola Pagliara",
                "9597845613974",
                LocalDateTime.of(LocalDate.of(2019,5,20),LocalTime.now()),
                "La birra è bella",
                "NicolaPagliara"

        );

        Libro libro5 = new Libro(
                "Sopravvivere in un mondo che odia i neri",
                "Gianmario Voria",
                "9597845613129",
                LocalDateTime.of(LocalDate.of(2017,10,15),LocalTime.now()),
                "Mariaritaaaaaaaa",
                "PallinuroEditore"

        );

        Libro libro6= new Libro(
                "Bestemmiare perché Tyhmeleaf non mette i loghi bene: Life of a Front-end developer",
                "Luca Topo",
                "9597845613123",
                LocalDateTime.now(),
                "Front-end merda",
                "Autopubblicato"

        );

        Libro libro7 = new Libro(
                "Mi scoccio di fare FIA: A day in the life of Viviana Pentangelo",
                "Viviana Pentangelo",
                "9597845613130",
                LocalDateTime.now(),
                "Non so come farò questo esame sinceramente",
                "PallinuroEditore"

        );

        libroDAO.save(libro);
        libroDAO.save(libro1);
        libroDAO.save(libro2);
        libroDAO.save(libro3);
        libroDAO.save(libro4);
        libroDAO.save(libro5);
        libroDAO.save(libro6);
        libroDAO.save(libro7);


//----------------------Definizione ed inserimento possessi-------------------------------------------------------------

        PossessoId possessoId = new PossessoId(
                biblioteca.getEmail(),
                libro.getIdLibro()
        );

        PossessoId possessoId1 = new PossessoId(
                biblioteca1.getEmail(),
                libro1.getIdLibro()
        );

        PossessoId possessoId2 = new PossessoId(
                biblioteca2.getEmail(),
                libro2.getIdLibro()
        );

        PossessoId possessoId3 = new PossessoId(
                biblioteca3.getEmail(),
                libro3.getIdLibro()
        );

        PossessoId possessoId4 = new PossessoId(
                biblioteca.getEmail(),
                libro4.getIdLibro()
        );

        PossessoId possessoId5 = new PossessoId(
                biblioteca.getEmail(),
                libro5.getIdLibro()
        );

        PossessoId possessoId6 = new PossessoId(
                biblioteca1.getEmail(),
                libro5.getIdLibro()
        );

        PossessoId possessoId7 = new PossessoId(
                biblioteca2.getEmail(),
                libro5.getIdLibro()
        );

        PossessoId possessoId8 = new PossessoId(
                biblioteca3.getEmail(),
                libro5.getIdLibro()
        );

        PossessoId possessoId9 = new PossessoId(
                biblioteca4.getEmail(),
                libro5.getIdLibro()
        );

        Possesso possesso = new Possesso(
                possessoId,
                20
        );

        Possesso possesso1 = new Possesso(
                possessoId1,
                50
        );

        Possesso possesso2 = new Possesso(
                possessoId2,
                2
        );

        Possesso possesso3 = new Possesso(
                possessoId3,
                100
        );

        Possesso possesso4 = new Possesso(
                possessoId4,
                5
        );

        Possesso possesso5 = new Possesso(
                possessoId5,
                10
        );

        Possesso possesso6 = new Possesso(
                possessoId6,
                10
        );

        Possesso possesso7 = new Possesso(
                possessoId7,
                10
        );

        Possesso possesso8 = new Possesso(
                possessoId8,
                10
        );

        Possesso possesso9 = new Possesso(
                possessoId9,
                10
        );

        possessoDAO.save(possesso);
        possessoDAO.save(possesso1);
        possessoDAO.save(possesso2);
        possessoDAO.save(possesso3);
        possessoDAO.save(possesso4);
        possessoDAO.save(possesso5);
        possessoDAO.save(possesso6);
        possessoDAO.save(possesso7);
        possessoDAO.save(possesso8);
        possessoDAO.save(possesso9);

//----------------------Definizione ed inserimento ticket prestiti------------------------------------------------------
        TicketPrestito ticket = new TicketPrestito(
                TicketPrestito.Stati.CREATO,
                LocalDateTime.now(),
                libro,
                biblioteca,
                lettore
        );

        TicketPrestito ticket1 = new TicketPrestito(
                TicketPrestito.Stati.CREATO,
                LocalDateTime.now(),
                libro2,
                biblioteca3,
                lettore2
        );

        TicketPrestito ticket2 = new TicketPrestito(
                TicketPrestito.Stati.CREATO,
                LocalDateTime.now(),
                libro4,
                biblioteca2,
                lettore3
        );

        TicketPrestito ticket3 = new TicketPrestito(
                TicketPrestito.Stati.CREATO,
                LocalDateTime.now(),
                libro3,
                biblioteca3,
                lettore2
        );

        TicketPrestito ticket4 = new TicketPrestito(
                TicketPrestito.Stati.CREATO,
                LocalDateTime.now(),
                libro5,
                biblioteca,
                lettore1
        );

        ticketPrestitoDAO.save(ticket);
        ticketPrestitoDAO.save(ticket1);
        ticketPrestitoDAO.save(ticket2);
        ticketPrestitoDAO.save(ticket3);
        ticketPrestitoDAO.save(ticket4);

//----------------------Definizione ed inserimento clubs----------------------------------------------------------------


        ClubDelLibro clubDelLibro = new ClubDelLibro(
                "Club Fantasy",
                "Si parla di libri fantasy",
                esperto
        );

        clubDelLibroDAO.save(clubDelLibro);

//----------------------Definizione ed inserimento eventi---------------------------------------------------------------

        Evento evento = new Evento(
                "Evento fantasy",
                "Evento fantasy",
                LocalDateTime.now(),
                clubDelLibro
        );

        eventoDAO.save(evento);

//-------------------------------POPOLAMENTO MANY TO MANY E ONE TO MANY-------------------------------------------------


//-------------------Associo ai lettori un club del libro---------------------------------------------------------------

        lettore.setClubs(Arrays.asList(clubDelLibro));
        lettore1.setClubs(Arrays.asList(clubDelLibro));
        lettore2.setClubs(Arrays.asList(clubDelLibro));
        lettore3.setClubs(Arrays.asList(clubDelLibro));

        lettoreDAO.save(lettore);
        lettoreDAO.save(lettore1);
        lettoreDAO.save(lettore2);
        lettoreDAO.save(lettore3);


//------------------Associo dei ticket alle biblioteche-----------------------------------------------------------------

        biblioteca.setTickets(Arrays.asList(ticket));
        biblioteca1.setTickets(Arrays.asList(ticket1));
        biblioteca2.setTickets(Arrays.asList(ticket2));
        biblioteca3.setTickets(Arrays.asList(ticket3));

        bibliotecaDAO.save(biblioteca);
        bibliotecaDAO.save(biblioteca1);
        bibliotecaDAO.save(biblioteca2);
        bibliotecaDAO.save(biblioteca3);

//-----------------Associo degli eventi ai club del libro---------------------------------------------------------------

        clubDelLibro.setEventi(Arrays.asList(evento));
        clubDelLibroDAO.save(clubDelLibro);

//------------------Associo dei generi ai club del libro----------------------------------------------------------------

        clubDelLibro.setGeneri(Arrays.asList(genere,genere1,genere2,genere3));
        clubDelLibroDAO.save(clubDelLibro);

//------------------Associo degli esperti ai club del libro-------------------------------------------------------------

        esperto.setClubs(Arrays.asList(clubDelLibro));
        espertoDAO.save(esperto);

//-----------------Associo dei generi agli esperti----------------------------------------------------------------------

        esperto.setGeneri(Arrays.asList(genere,genere1));
        esperto1.setGeneri(Arrays.asList(genere1,genere2));
        esperto2.setGeneri(Arrays.asList(genere2,genere3));
        esperto3.setGeneri(Arrays.asList(genere3,genere4));
        esperto4.setGeneri(Arrays.asList(genere4,genere));
        esperto5.setGeneri(Arrays.asList(genere,genere1));
        esperto6.setGeneri(Arrays.asList(genere1,genere2));
        esperto7.setGeneri(Arrays.asList(genere2,genere3));
        esperto8.setGeneri(Arrays.asList(genere3,genere4));
        esperto9.setGeneri(Arrays.asList(genere4,genere));

        espertoDAO.save(esperto);
        espertoDAO.save(esperto1);
        espertoDAO.save(esperto2);
        espertoDAO.save(esperto3);
        espertoDAO.save(esperto4);
        espertoDAO.save(esperto5);
        espertoDAO.save(esperto6);
        espertoDAO.save(esperto7);
        espertoDAO.save(esperto8);
        espertoDAO.save(esperto9);

//-----------------Associo dei lettori agli eventi----------------------------------------------------------------------

        lettore.setEventi(Arrays.asList(evento));
        lettore1.setEventi(Arrays.asList(evento));
        lettore2.setEventi(Arrays.asList(evento));
        lettore3.setEventi(Arrays.asList(evento));

        lettoreDAO.save(lettore);
        lettoreDAO.save(lettore1);
        lettoreDAO.save(lettore2);
        lettoreDAO.save(lettore3);


//-----------------Associo dei generi ai lettori------------------------------------------------------------------------

        lettore.setGeneri(Arrays.asList(genere,genere1));
        lettore1.setGeneri(Arrays.asList(genere1,genere2));
        lettore2.setGeneri(Arrays.asList(genere2,genere3));
        lettore3.setGeneri(Arrays.asList(genere3,genere4));

        lettoreDAO.save(lettore);
        lettoreDAO.save(lettore1);
        lettoreDAO.save(lettore2);
        lettoreDAO.save(lettore3);


//----------------Associo dei ticket ai lettori-------------------------------------------------------------------------

        lettore.setTickets(Arrays.asList(ticket));
        lettore1.setTickets(Arrays.asList(ticket1));
        lettore2.setTickets(Arrays.asList(ticket2));
        lettore3.setTickets(Arrays.asList(ticket3));

        lettoreDAO.save(lettore);
        lettoreDAO.save(lettore1);
        lettoreDAO.save(lettore2);
        lettoreDAO.save(lettore3);


//----------------Associo dei generi ad un libro------------------------------------------------------------------------

        libro.setGeneri(Arrays.asList(genere,genere1));
        libro1.setGeneri(Arrays.asList(genere1,genere2));
        libro2.setGeneri(Arrays.asList(genere2,genere3));
        libro3.setGeneri(Arrays.asList(genere3,genere4));
        libro4.setGeneri(Arrays.asList(genere4,genere));
        libro5.setGeneri(Arrays.asList(genere,genere1));

        libroDAO.save(libro);
        libroDAO.save(libro1);
        libroDAO.save(libro2);
        libroDAO.save(libro3);
        libroDAO.save(libro4);
        libroDAO.save(libro5);

    }
}
