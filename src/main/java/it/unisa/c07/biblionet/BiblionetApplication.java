package it.unisa.c07.biblionet;

import it.unisa.c07.biblionet.prenotazioneLibri.service.bookApiAdapter.BookApiAdapter;
import it.unisa.c07.biblionet.prenotazioneLibri.service.bookApiAdapter.GoogleBookApiAdapterImpl;
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
import java.time.LocalDateTime;
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

        BookApiAdapter bookApiAdapter = new GoogleBookApiAdapterImpl();
        bookApiAdapter.getLibroDaBookApi("978-88-238-2264-1");

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
                "bunnapoli@libero.it",
                "BibliotecaPassword",
                "Napoli",
                "Napoli",
                "Via Giovanni Paladino",
                "0815517025",
                "BUN - Biblioteca Universitaria di Napoli"
        );

        Biblioteca biblioteca2 = new Biblioteca(
                "villacarrara@gmail.com",
                "BibliotecaPassword",
                "Salerno",
                "Salerno",
                "Via Posidonia 47",
                "3341278415",
                "Biblioteca Villa Carrara"
        );

        Biblioteca biblioteca3 = new Biblioteca(
                "centrodilettura@gmail.com",
                "BibliotecaPassword",
                "Salerno",
                "Fisciano",
                "Via Roma 1",
                "3341276715",
                "Biblioteca del Centro di Lettura"
        );

        Biblioteca biblioteca4 = new Biblioteca(
                "unisapoloscientifico@gmail.com",
                "BibliotecaPassword",
                "Salerno",
                "Fisciano",
                "Università di Salerno",
                "0815168272",
                "Biblioteca del Polo scientifico e tecnologico"
        );

        Biblioteca biblioteca5 = new Biblioteca(
                "comunenocera@gmail.com",
                "BibliotecaPassword",
                "Salerno",
                "Nocera Inferiore",
                "Corso Vittorio Emanuele II 52",
                "0813235611",
                "Biblioteca Comunale di Nocera Inferiore"
        );

        Biblioteca biblioteca6 = new Biblioteca(
                "aldomoronocera@gmail.com",
                "BibliotecaPassword",
                "Salerno",
                "Nocera Inferiore",
                "Piazza Aldo Moro",
                "0814535611",
                "Biblioteca Aldo Moro"
        );

        Biblioteca biblioteca7 = new Biblioteca(
                "deliguori@gmail.com",
                "BibliotecaPassword",
                "Salerno",
                "Pagani",
                "Piazza Sant'Alfonso",
                "0815436114",
                "Biblioteca S. Alfonso dè Liguori"
        );

        Biblioteca biblioteca8 = new Biblioteca(
                "lpepepompei@gmail.com",
                "BibliotecaPassword",
                "Napoli",
                "Pompei",
                "Via Aldo Moro II",
                "081324357",
                "Biblioteca L. Pepe"
        );

        Biblioteca biblioteca9 = new Biblioteca(
                "fmorlicchio@gmail.com",
                "BibliotecaPassword",
                "Salerno",
                "Scafati",
                "Via Galileo Galilei 34",
                "0813223238",
                "Biblioteca Francesco Morlicchio"
        );

        bibliotecaDAO.save(biblioteca);
        bibliotecaDAO.save(biblioteca1);
        bibliotecaDAO.save(biblioteca2);
        bibliotecaDAO.save(biblioteca3);
        bibliotecaDAO.save(biblioteca4);
        bibliotecaDAO.save(biblioteca5);
        bibliotecaDAO.save(biblioteca6);
        bibliotecaDAO.save(biblioteca7);
        bibliotecaDAO.save(biblioteca8);
        bibliotecaDAO.save(biblioteca9);

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
                "ciromaiorino@gmail.com",
                "EspertoPassword",
                "Salerno",
                "Pagani",
                "Via Roma 2",
                "2345678901",
                "ciromaiorino",
                "Ciro",
                "Maiorino",
                biblioteca1
        );

        Esperto esperto2 = new Esperto(
                "giuliotriggiani@gmail.com",
                "EspertoPassword",
                "Napoli",
                "Torre del Greco",
                "Via CasaDiGiulio 2",
                "3127403698",
                "giuliotriggiani",
                "Giulio",
                "Triggiani",
                biblioteca2
        );

        Esperto esperto3 = new Esperto(
                "lucatopo@gmail.com",
                "EspertoPassword",
                "Napoli",
                "Ischia",
                "Via Roma 2",
                "3912479852",
                "lucatopo",
                "Luca",
                "Topo",
                biblioteca3
        );

        Esperto esperto4 = new Esperto(
                "vivianapentangelo@gmail.com",
                "EspertoPassword",
                "Salerno",
                "Pagani",
                "Piazza S.Alfonso 2",
                "3789612345",
                "vivianapentangelo",
                "Viviana",
                "Pentangelo",
                biblioteca4
        );

        Esperto esperto5 = new Esperto(
                "gianmariovoria@gmail.com",
                "EspertoPassword",
                "Salerno",
                "Agropoli",
                "Via Bonora 41",
                "3961234784",
                "gianmariovoria",
                "Gianmario",
                "Voria",
                biblioteca5
        );

        Esperto esperto6 = new Esperto(
                "stefanolambiase@gmail.com",
                "EspertoPassword",
                "Salerno",
                "Salerno",
                "Via Torrione",
                "2345678901",
                "stefanolambiaseBESTPM",
                "Stefano",
                "Lambiase",
                biblioteca6
        );

        Esperto esperto7 = new Esperto(
                "alessiocasolaro@gmail.com",
                "EspertoPassword",
                "Napoli",
                "Somma Vesuviana",
                "Via Vesuvio 69",
                "3336977841",
                "alessiocasolaser",
                "Alessio",
                "Casolaro",
                biblioteca7
        );

        Esperto esperto8 = new Esperto(
                "antoniodellaporta@gmail.com",
                "EspertoPassword",
                "Salerno",
                "Nocera Inferiore",
                "Via Nocerina 45",
                "3963636963",
                "antoniodellaporta",
                "Antonio",
                "Della Porta",
                biblioteca8
        );

        Esperto esperto9 = new Esperto(
                "nicolapagliara@gmail.com",
                "EspertoPassword",
                "Salerno",
                "Salerno",
                "Via Torrione",
                "3694578963",
                "nicolapagliara",
                "Nicola",
                "Pagliara",
                biblioteca9
        );

        Esperto esperto10 = new Esperto(
                "paolobonolis@gmail.com",
                "EspertoPassword",
                "Salerno",
                "Paestum",
                "Via Avanti un Altro",
                "3694578963",
                "paolobonolis",
                "Paolo",
                "Bonolis",
                biblioteca3
        );

        Esperto esperto11 = new Esperto(
                "lucalaurenti@gmail.com",
                "EspertoPassword",
                "Caserta",
                "Marcianise",
                "Via della Reggia",
                "3694578963",
                "lucalaurenti",
                "Luca",
                "Laurenti",
                biblioteca1
        );

        Esperto esperto12 = new Esperto(
                "alfonsosignorini@gmail.com",
                "EspertoPassword",
                "Napoli",
                "Castellammare di Stabia",
                "Via delle Sabbie 45",
                "3694578963",
                "alfonsosignorini",
                "Alfonso",
                "Signorini",
                biblioteca2
        );

        Esperto esperto13 = new Esperto(
                "antoninocannavacciuolo@gmail.com",
                "EspertoPassword",
                "Napoli",
                "Napoli",
                "Via Villa Crespi",
                "3694578963",
                "antoninocannavacciuolo",
                "Antonino",
                "Cannavacciuolo",
                biblioteca3
        );

        Esperto esperto14 = new Esperto(
                "brunobarbieri@gmail.com",
                "EspertoPassword",
                "Avellino",
                "Avellino",
                "Via Tortellino 54",
                "3694578963",
                "brunobarbieri",
                "Bruno",
                "Barbieri",
                biblioteca4
        );

        Esperto esperto15 = new Esperto(
                "carlocracco@gmail.com",
                "EspertoPassword",
                "Avellino",
                "Ariano Irpino",
                "Via Nuova Pizza 4",
                "3694578963",
                "carlocracco",
                "Carlo",
                "Cracco",
                biblioteca5
        );

        Esperto esperto16 = new Esperto(
                "joebastianich@gmail.com",
                "EspertoPassword",
                "Napoli",
                "Pompei",
                "Via Mc Donald 1",
                "3694578963",
                "joebastianich",
                "Joe",
                "Bastianich",
                biblioteca6
        );

        Esperto esperto17 = new Esperto(
                "iginiomassari@gmail.com",
                "EspertoPassword",
                "Napoli",
                "Torre Annunziata",
                "Via Pasticcino 26",
                "3694578963",
                "iginiomassari",
                "Iginio",
                "Massari",
                biblioteca7
        );

        Esperto esperto18 = new Esperto(
                "antonellaclerici@gmail.com",
                "EspertoPassword",
                "Salerno",
                "Nocera Superiore",
                "Via Del Cuoco 78",
                "3694578963",
                "antonellaclerici",
                "Antonella",
                "Clerici",
                biblioteca8
        );

        Esperto esperto19 = new Esperto(
                "alessandroborghese@gmail.com",
                "EspertoPassword",
                "Salerno",
                "San Marzano sul Sarno",
                "Via Ristoranti 4",
                "3694578963",
                "alessandroborghese",
                "Alessandro",
                "Borghese",
                biblioteca9
        );

        Esperto esperto20 = new Esperto(
                "gennaroesposito@gmail.com",
                "EspertoPassword",
                "Salerno",
                "Vietri sul Mare",
                "Via Buon Appetito 5",
                "3694578963",
                "gennaroesposito",
                "Gennaro",
                "Esposito",
                biblioteca2
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
        espertoDAO.save(esperto10);
        espertoDAO.save(esperto11);
        espertoDAO.save(esperto12);
        espertoDAO.save(esperto13);
        espertoDAO.save(esperto14);
        espertoDAO.save(esperto15);
        espertoDAO.save(esperto16);
        espertoDAO.save(esperto17);
        espertoDAO.save(esperto18);
        espertoDAO.save(esperto19);
        espertoDAO.save(esperto20);


//----------------------Definizione ed inserimento lettori--------------------------------------------------------------

        Lettore lettore = new Lettore(
                "antoniorenatomontefusco@gmail.com",
                "LettorePassword",
                "Napoli",
                "Somma Vesuviana",
                "Via Vesuvio 33",
                "3456789012",
                "antoniomontefusco",
                "Antonio",
                "Montefusco"
        );

        Lettore lettore1 = new Lettore(
                "jacopogambardella@gmail.com",
                "LettorePassword",
                "Salerno",
                "Pagani",
                "Via San Domenico 10",
                "3451349012",
                "jacopogambardella",
                "Jacopo",
                "Gambardella"
        );

        Lettore lettore2 = new Lettore(
                "pamelacasullo@gmail.com",
                "LettorePassword",
                "Milano",
                "Milano",
                "Viale dei Navigli",
                "3451343794",
                "pamelacasullo",
                "Pamela",
                "Casullo"
        );

        Lettore lettore3 = new Lettore(
                "angeloambrosio@gmail.com",
                "LettorePassword",
                "Napoli",
                "Saomma Vesuviana",
                "Via Dalluni 22",
                "3459013412",
                "angeloambrosio",
                "Angelo",
                "Ambrosio"
        );

        Lettore lettore4 = new Lettore(
                "giuseppedavino@gmail.com",
                "LettorePassword",
                "Napoli",
                "Saomma Vesuviana",
                "Via Rochet Lig 55",
                "3459013412",
                "giuseppedavino",
                "Giuseppe",
                "D'Avino"
        );

        Lettore lettore5 = new Lettore(
                "paoloapostolico@gmail.com",
                "LettorePassword",
                "Salerno",
                "Castel San Giorgio",
                "Via Bertini 42",
                "3459013412",
                "paoloapostolico",
                "Paolo",
                "Apostolico"
        );

        Lettore lettore6 = new Lettore(
                "marcocostante@gmail.com",
                "LettorePassword",
                "Avellino",
                "Ariano Irpino",
                "Via Costante K",
                "3459013412",
                "marcocostante",
                "Marco",
                "Costante"
        );

        Lettore lettore7 = new Lettore(
                "mafaldaingenito@gmail.com",
                "LettorePassword",
                "Napoli",
                "Gragnano",
                "Via Franco 6",
                "3459013412",
                "mafaldaingenito",
                "Mafalda",
                "Ingenito"
        );

        Lettore lettore8 = new Lettore(
                "carminegallina@gmail.com",
                "LettorePassword",
                "Caserta",
                "Capua",
                "Via Dalla Spagna 36",
                "3459013412",
                "carminegallina",
                "Carmine",
                "Gallina"
        );

        Lettore lettore9 = new Lettore(
                "vincenzodisanto@gmail.com",
                "LettorePassword",
                "Caserta",
                "San Nicola La Strada",
                "Via Dall'Appello 13",
                "3459013412",
                "vincenzodisanto",
                "Vincenzo",
                "Di Santo"
        );

        Lettore lettore10 = new Lettore(
                "mauriziofabrocile@gmail.com",
                "LettorePassword",
                "Caserta",
                "Caserta",
                "Via Dragoni 23",
                "3459013412",
                "mauriziofabrocile",
                "Maurizio Fabrizio",
                "Fabrocile"
        );

        Lettore lettore11 = new Lettore(
                "giuseppecontaldi@gmail.com",
                "LettorePassword",
                "Salerno",
                "Salerno",
                "Via Per Sempre 1",
                "3459013412",
                "giuseppecontaldi",
                "Giuseppe",
                "Contaldi"
        );

        Lettore lettore12 = new Lettore(
                "carminedilieto@gmail.com",
                "LettorePassword",
                "Salerno",
                "Pagani",
                "Via Ferrante 2",
                "3459013412",
                "carminedilieto",
                "Carmine",
                "Di Lieto"
        );

        lettoreDAO.save(lettore);
        lettoreDAO.save(lettore1);
        lettoreDAO.save(lettore2);
        lettoreDAO.save(lettore3);
        lettoreDAO.save(lettore4);
        lettoreDAO.save(lettore5);
        lettoreDAO.save(lettore6);
        lettoreDAO.save(lettore7);
        lettoreDAO.save(lettore8);
        lettoreDAO.save(lettore9);
        lettoreDAO.save(lettore10);
        lettoreDAO.save(lettore11);
        lettoreDAO.save(lettore12);

//----------------------Definizione ed inserimento generi---------------------------------------------------------------

        Genere fantasy = new Genere(
                "Fantasy",
                "Genere fantastico"
        );

        Genere azione = new Genere(
                "Azione",
                "Genere molto movimentato"
        );

        Genere space = new Genere(
                "Space",
                "Genere spaziale"
        );

        Genere biografico = new Genere(
                "Biografico",
                "Genere introspettivo"
        );

        Genere politico = new Genere(
                "Politico",
                "Genere ingannevole"
        );

        Genere narrativa = new Genere(
                "Narrativa",
                "Genere narrativo"
        );

        Genere romanzo = new Genere(
                "Romanzo",
                "Genere che si descrive da solo"
        );

        Genere storico = new Genere(
                "Storico",
                "Genere sulla storia"
        );

        Genere fantascienza = new Genere(
                "Fantascienza",
                "Genere genere fantastico ma scientifico"
        );

        Genere tecnologia = new Genere(
                "Tecnologia",
                "Genere moderno"
        );

        Genere noir = new Genere(
                "Noir",
                "Genere scuro"
        );

        Genere distopia = new Genere(
                "Distopia",
                "Genere ingannevole"
        );

        Genere romantico = new Genere(
                "Romantico",
                "Genere sdolcinato"
        );

        Genere avventura = new Genere(
                "D'Avventura",
                "Genere intraprendente"
        );

        Genere formazione = new Genere(
                "Di formazione",
                "Genere formativo"
        );

        Genere ragazzi = new Genere(
                "Per ragazzi",
                "Genere giovanile"
        );

        Genere horror = new Genere(
                "Horror",
                "Genere spaventosissimo"
        );

        Genere thriller = new Genere(
                "Thriller",
                "Genere ansioso"
        );

        Genere gotico = new Genere(
                "Gotico",
                "Genere dal gusto gotico"
        );

        Genere giallo = new Genere(
                "Giallo",
                "Genere investigativo"
        );

        Genere scientifico = new Genere(
                "Scientifico",
                "Genere scientifico"
        );

        Genere psicologico = new Genere(
                "Psicologico",
                "Genere psicologico"
        );

        Genere saggio = new Genere(
                "Saggio",
                "Genere sapiente"
        );

        Genere comico = new Genere(
                "Comico",
                "Genere divertente"
        );

        Genere fiabefavole = new Genere(
                "Fiabe e favole",
                "Genere fiabesco"
        );

        genereDAO.save(avventura);
        genereDAO.save(azione);
        genereDAO.save(giallo);
        genereDAO.save(gotico);
        genereDAO.save(biografico);
        genereDAO.save(comico);
        genereDAO.save(distopia);
        genereDAO.save(fantasy);
        genereDAO.save(fantascienza);
        genereDAO.save(fiabefavole);
        genereDAO.save(formazione);
        genereDAO.save(horror);
        genereDAO.save(narrativa);
        genereDAO.save(noir);
        genereDAO.save(politico);
        genereDAO.save(psicologico);
        genereDAO.save(ragazzi);
        genereDAO.save(romantico);
        genereDAO.save(romanzo);
        genereDAO.save(saggio);
        genereDAO.save(scientifico);
        genereDAO.save(space);
        genereDAO.save(storico);
        genereDAO.save(tecnologia);
        genereDAO.save(thriller);

//----------------------Definizione ed inserimento libri----------------------------------------------------------------

        Libro libro = bookApiAdapter.getLibroDaBookApi("9781781101582");
        libro.setGeneri(Arrays.asList(fantasy, ragazzi, romanzo));

        Libro libro1 = bookApiAdapter.getLibroDaBookApi("9781781102121");
        libro1.setGeneri(Arrays.asList(fantasy, ragazzi, romanzo));

        Libro libro2 = bookApiAdapter.getLibroDaBookApi("9788804616351");
        libro2.setGeneri(Arrays.asList(fantasy, avventura, azione));

        Libro libro3 = bookApiAdapter.getLibroDaBookApi("9780141439570");
        libro3.setGeneri(Arrays.asList(narrativa, gotico));

        Libro libro4 = bookApiAdapter.getLibroDaBookApi("9780141034591");
        libro4.setGeneri(Arrays.asList(saggio));

        Libro libro5 = bookApiAdapter.getLibroDaBookApi("9788838473463");
        libro5.setGeneri(Arrays.asList(horror, thriller));

        Libro libro6 = bookApiAdapter.getLibroDaBookApi("9780198321668");
        libro6.setGeneri(Arrays.asList(romantico));

        Libro libro7 = bookApiAdapter.getLibroDaBookApi("9788845295300");
        libro7.setGeneri(Arrays.asList(ragazzi, fiabefavole));

        Libro libro8 = bookApiAdapter.getLibroDaBookApi("9788852049774");
        libro8.setGeneri(Arrays.asList(giallo, thriller, romanzo));

        Libro libro9 = bookApiAdapter.getLibroDaBookApi("9788854122789");
        libro9.setGeneri(Arrays.asList(narrativa));

        Libro libro10 = bookApiAdapter.getLibroDaBookApi("9788852028489");
        libro10.setGeneri(Arrays.asList(narrativa, storico));

        Libro libro11 = bookApiAdapter.getLibroDaBookApi("9788860816412");
        libro11.setGeneri(Arrays.asList(psicologico));

        Libro libro12 = bookApiAdapter.getLibroDaBookApi("9788854133587");
        libro12.setGeneri(Arrays.asList(romanzo, giallo, storico));

        Libro libro13 = bookApiAdapter.getLibroDaBookApi("9781633397354");
        libro13.setGeneri(Arrays.asList(tecnologia, scientifico));

        Libro libro14 = bookApiAdapter.getLibroDaBookApi("9788841869741");
        libro14.setGeneri(Arrays.asList(narrativa, ragazzi));

        Libro libro15 = bookApiAdapter.getLibroDaBookApi("9788864113036");
        libro15.setGeneri(Arrays.asList(fantasy, romantico));

        Libro libro16 = bookApiAdapter.getLibroDaBookApi("9788852021558");
        libro16.setGeneri(Arrays.asList(distopia, fantascienza));

        Libro libro17 = bookApiAdapter.getLibroDaBookApi("9788851161439");
        libro17.setGeneri(Arrays.asList(ragazzi, fiabefavole));

        Libro libro18 = bookApiAdapter.getLibroDaBookApi("9788866327769");
        libro18.setGeneri(Arrays.asList(giallo, thriller));

        Libro libro19 = bookApiAdapter.getLibroDaBookApi("9788830455320");
        libro19.setGeneri(Arrays.asList(giallo, noir));

        Libro libro20 = bookApiAdapter.getLibroDaBookApi("9788854513006");
        libro20.setGeneri(Arrays.asList(biografico, storico));

        Libro libro21 = bookApiAdapter.getLibroDaBookApi("9788858420430");
        libro21.setGeneri(Arrays.asList(biografico, storico));

        Libro libro22 = bookApiAdapter.getLibroDaBookApi("9788858420423");
        libro22.setGeneri(Arrays.asList(biografico, storico));

        Libro libro23 = bookApiAdapter.getLibroDaBookApi("9788841878217");
        libro23.setGeneri(Arrays.asList(distopia, fantascienza));

        Libro libro24 = bookApiAdapter.getLibroDaBookApi("9788854158245");
        libro24.setGeneri(Arrays.asList(romantico, fantasy));

        Libro libro25 = bookApiAdapter.getLibroDaBookApi("9788858672198");
        libro25.setGeneri(Arrays.asList(comico));

        Libro libro26 = bookApiAdapter.getLibroDaBookApi("9788858693322");
        libro26.setGeneri(Arrays.asList(comico, narrativa));

        Libro libro27 = bookApiAdapter.getLibroDaBookApi("9788865188071");
        libro27.setGeneri(Arrays.asList(scientifico, tecnologia));

        Libro libro28 = bookApiAdapter.getLibroDaBookApi("9788820096533");
        libro28.setGeneri(Arrays.asList(saggio, scientifico));

        Libro libro29 = bookApiAdapter.getLibroDaBookApi("9788852022562");
        libro29.setGeneri(Arrays.asList(tecnologia, fantascienza, narrativa));

        Libro libro30 = bookApiAdapter.getLibroDaBookApi("9788852022586");
        libro30.setGeneri(Arrays.asList(fantasy, romanzo, fantascienza));

        Libro libro31 = bookApiAdapter.getLibroDaBookApi("9788868958855");
        libro31.setGeneri(Arrays.asList(tecnologia));

        Libro libro32 = bookApiAdapter.getLibroDaBookApi("9788862316019");
        libro32.setGeneri(Arrays.asList(biografico));

        Libro libro33 = bookApiAdapter.getLibroDaBookApi("9788852011610");
        libro33.setGeneri(Arrays.asList(fantasy, romanzo, ragazzi));

        Libro libro34 = bookApiAdapter.getLibroDaBookApi("9788868656188");
        libro34.setGeneri(Arrays.asList(romanzo, romantico));

        Libro libro35 = bookApiAdapter.getLibroDaBookApi("9788854143982");
        libro35.setGeneri(Arrays.asList(avventura));

        Libro libro36 = bookApiAdapter.getLibroDaBookApi("9788842922650");
        libro36.setGeneri(Arrays.asList(avventura, azione, fantasy));

        Libro libro37 = bookApiAdapter.getLibroDaBookApi("9781633398863");
        libro37.setGeneri(Arrays.asList(tecnologia));

        Libro libro38 = bookApiAdapter.getLibroDaBookApi("9788809906440");
        libro38.setGeneri(Arrays.asList(giallo, thriller));

        Libro libro39 = bookApiAdapter.getLibroDaBookApi("9781326891220");
        libro39.setGeneri(Arrays.asList(storico, biografico));

        Libro libro40 = bookApiAdapter.getLibroDaBookApi("9788893128933");
        libro40.setGeneri(Arrays.asList(romanzo, fantasy));

        libroDAO.save(libro);
        libroDAO.save(libro1);
        libroDAO.save(libro2);
        libroDAO.save(libro3);
        libroDAO.save(libro4);
        libroDAO.save(libro5);
        libroDAO.save(libro6);
        libroDAO.save(libro7);
        libroDAO.save(libro8);
        libroDAO.save(libro9);
        libroDAO.save(libro10);
        libroDAO.save(libro11);
        libroDAO.save(libro12);
        libroDAO.save(libro13);
        libroDAO.save(libro14);
        libroDAO.save(libro15);
        libroDAO.save(libro16);
        libroDAO.save(libro17);
        libroDAO.save(libro18);
        libroDAO.save(libro19);
        libroDAO.save(libro20);
        libroDAO.save(libro21);
        libroDAO.save(libro22);
        libroDAO.save(libro23);
        libroDAO.save(libro24);
        libroDAO.save(libro25);
        libroDAO.save(libro26);
        libroDAO.save(libro27);
        libroDAO.save(libro28);
        libroDAO.save(libro29);
        libroDAO.save(libro30);
        libroDAO.save(libro31);
        libroDAO.save(libro32);
        libroDAO.save(libro33);
        libroDAO.save(libro34);
        libroDAO.save(libro35);
        libroDAO.save(libro36);
        libroDAO.save(libro37);
        libroDAO.save(libro38);
        libroDAO.save(libro39);
        libroDAO.save(libro40);

//----------------------Definizione ed inserimento possessi-------------------------------------------------------------

        //BIBLIOTECA 1

        PossessoId possessoId1B1 = new PossessoId(
                biblioteca.getEmail(),
                libro1.getIdLibro()
        );
        PossessoId possessoId1B2 = new PossessoId(
                biblioteca.getEmail(),
                libro2.getIdLibro()
        );
        PossessoId possessoId1B3 = new PossessoId(
                biblioteca.getEmail(),
                libro3.getIdLibro()
        );
        PossessoId possessoId1B4 = new PossessoId(
                biblioteca.getEmail(),
                libro4.getIdLibro()
        );
        PossessoId possessoId1B5 = new PossessoId(
                biblioteca.getEmail(),
                libro5.getIdLibro()
        );
        PossessoId possessoId1B6 = new PossessoId(
                biblioteca.getEmail(),
                libro6.getIdLibro()
        );
        PossessoId possessoId1B7 = new PossessoId(
                biblioteca.getEmail(),
                libro7.getIdLibro()
        );
        PossessoId possessoId1B8 = new PossessoId(
                biblioteca.getEmail(),
                libro8.getIdLibro()
        );

        Possesso possesso1B1 = new Possesso(
                possessoId1B1,
                10
        );
        Possesso possesso1B2 = new Possesso(
                possessoId1B2,
                10
        );
        Possesso possesso1B3 = new Possesso(
                possessoId1B3,
                10
        );
        Possesso possesso1B4 = new Possesso(
                possessoId1B4,
                10
        );
        Possesso possesso1B5 = new Possesso(
                possessoId1B5,
                10
        );
        Possesso possesso1B6 = new Possesso(
                possessoId1B6,
                10
        );
        Possesso possesso1B7 = new Possesso(
                possessoId1B7,
                10
        );
        Possesso possesso1B8 = new Possesso(
                possessoId1B8,
                10
        );

        possessoDAO.save(possesso1B1);
        possessoDAO.save(possesso1B2);
        possessoDAO.save(possesso1B3);
        possessoDAO.save(possesso1B4);
        possessoDAO.save(possesso1B5);
        possessoDAO.save(possesso1B6);
        possessoDAO.save(possesso1B7);
        possessoDAO.save(possesso1B8);

        //BIBLIOTECA 2
        PossessoId possessoId2B1 = new PossessoId(
                biblioteca1.getEmail(),
                libro9.getIdLibro()
        );
        PossessoId possessoId2B2 = new PossessoId(
                biblioteca1.getEmail(),
                libro10.getIdLibro()
        );
        PossessoId possessoId2B3 = new PossessoId(
                biblioteca1.getEmail(),
                libro11.getIdLibro()
        );
        PossessoId possessoId2B4 = new PossessoId(
                biblioteca1.getEmail(),
                libro12.getIdLibro()
        );
        PossessoId possessoId2B5 = new PossessoId(
                biblioteca1.getEmail(),
                libro13.getIdLibro()
        );
        PossessoId possessoId2B6 = new PossessoId(
                biblioteca1.getEmail(),
                libro14.getIdLibro()
        );
        PossessoId possessoId2B7 = new PossessoId(
                biblioteca1.getEmail(),
                libro15.getIdLibro()
        );
        PossessoId possessoId2B8 = new PossessoId(
                biblioteca1.getEmail(),
                libro16.getIdLibro()
        );

        Possesso possesso2B1 = new Possesso(
                possessoId2B1,
                10
        );
        Possesso possesso2B2 = new Possesso(
                possessoId2B2,
                10
        );
        Possesso possesso2B3 = new Possesso(
                possessoId2B3,
                10
        );
        Possesso possesso2B4 = new Possesso(
                possessoId2B4,
                10
        );
        Possesso possesso2B5 = new Possesso(
                possessoId2B5,
                10
        );
        Possesso possesso2B6 = new Possesso(
                possessoId2B6,
                10
        );
        Possesso possesso2B7 = new Possesso(
                possessoId2B7,
                10
        );
        Possesso possesso2B8 = new Possesso(
                possessoId2B8,
                10
        );

        possessoDAO.save(possesso2B1);
        possessoDAO.save(possesso2B2);
        possessoDAO.save(possesso2B3);
        possessoDAO.save(possesso2B4);
        possessoDAO.save(possesso2B5);
        possessoDAO.save(possesso2B6);
        possessoDAO.save(possesso2B7);
        possessoDAO.save(possesso2B8);

        //BIBLIOTECA 3

        PossessoId possessoId3B1 = new PossessoId(
                biblioteca2.getEmail(),
                libro17.getIdLibro()
        );
        PossessoId possessoId3B2 = new PossessoId(
                biblioteca2.getEmail(),
                libro18.getIdLibro()
        );
        PossessoId possessoId3B3 = new PossessoId(
                biblioteca2.getEmail(),
                libro19.getIdLibro()
        );
        PossessoId possessoId3B4 = new PossessoId(
                biblioteca2.getEmail(),
                libro20.getIdLibro()
        );
        PossessoId possessoId3B5 = new PossessoId(
                biblioteca2.getEmail(),
                libro21.getIdLibro()
        );
        PossessoId possessoId3B6 = new PossessoId(
                biblioteca2.getEmail(),
                libro22.getIdLibro()
        );
        PossessoId possessoId3B7 = new PossessoId(
                biblioteca2.getEmail(),
                libro23.getIdLibro()
        );
        PossessoId possessoId3B8 = new PossessoId(
                biblioteca2.getEmail(),
                libro24.getIdLibro()
        );

        Possesso possesso3B1 = new Possesso(
                possessoId3B1,
                10
        );
        Possesso possesso3B2 = new Possesso(
                possessoId3B2,
                10
        );
        Possesso possesso3B3 = new Possesso(
                possessoId3B3,
                10
        );
        Possesso possesso3B4 = new Possesso(
                possessoId3B4,
                10
        );
        Possesso possesso3B5 = new Possesso(
                possessoId3B5,
                10
        );
        Possesso possesso3B6 = new Possesso(
                possessoId3B6,
                10
        );
        Possesso possesso3B7 = new Possesso(
                possessoId3B7,
                10
        );
        Possesso possesso3B8 = new Possesso(
                possessoId3B8,
                10
        );

        possessoDAO.save(possesso3B1);
        possessoDAO.save(possesso3B2);
        possessoDAO.save(possesso3B3);
        possessoDAO.save(possesso3B4);
        possessoDAO.save(possesso3B5);
        possessoDAO.save(possesso3B6);
        possessoDAO.save(possesso3B7);
        possessoDAO.save(possesso3B8);


        //BIBLIOTECA 4

        PossessoId possessoId4B1 = new PossessoId(
                biblioteca3.getEmail(),
                libro32.getIdLibro()
        );
        PossessoId possessoId4B2 = new PossessoId(
                biblioteca3.getEmail(),
                libro25.getIdLibro()
        );
        PossessoId possessoId4B3 = new PossessoId(
                biblioteca3.getEmail(),
                libro26.getIdLibro()
        );
        PossessoId possessoId4B4 = new PossessoId(
                biblioteca3.getEmail(),
                libro27.getIdLibro()
        );
        PossessoId possessoId4B5 = new PossessoId(
                biblioteca3.getEmail(),
                libro28.getIdLibro()
        );
        PossessoId possessoId4B6 = new PossessoId(
                biblioteca3.getEmail(),
                libro29.getIdLibro()
        );
        PossessoId possessoId4B7 = new PossessoId(
                biblioteca3.getEmail(),
                libro30.getIdLibro()
        );
        PossessoId possessoId4B8 = new PossessoId(
                biblioteca3.getEmail(),
                libro31.getIdLibro()
        );

        Possesso possesso4B1 = new Possesso(
                possessoId4B1,
                10
        );
        Possesso possesso4B2 = new Possesso(
                possessoId4B2,
                10
        );
        Possesso possesso4B3 = new Possesso(
                possessoId4B3,
                10
        );
        Possesso possesso4B4 = new Possesso(
                possessoId4B4,
                10
        );
        Possesso possesso4B5 = new Possesso(
                possessoId4B5,
                10
        );
        Possesso possesso4B6 = new Possesso(
                possessoId4B6,
                10
        );
        Possesso possesso4B7 = new Possesso(
                possessoId4B7,
                10
        );
        Possesso possesso4B8 = new Possesso(
                possessoId4B8,
                10
        );

        possessoDAO.save(possesso4B1);
        possessoDAO.save(possesso4B2);
        possessoDAO.save(possesso4B3);
        possessoDAO.save(possesso4B4);
        possessoDAO.save(possesso4B5);
        possessoDAO.save(possesso4B6);
        possessoDAO.save(possesso4B7);
        possessoDAO.save(possesso4B8);


        //BIBLIOTECA 5

        PossessoId possessoId5B1 = new PossessoId(
                biblioteca4.getEmail(),
                libro33.getIdLibro()
        );
        PossessoId possessoId5B2 = new PossessoId(
                biblioteca4.getEmail(),
                libro40.getIdLibro()
        );
        PossessoId possessoId5B3 = new PossessoId(
                biblioteca4.getEmail(),
                libro34.getIdLibro()
        );
        PossessoId possessoId5B4 = new PossessoId(
                biblioteca4.getEmail(),
                libro35.getIdLibro()
        );
        PossessoId possessoId5B5 = new PossessoId(
                biblioteca4.getEmail(),
                libro36.getIdLibro()
        );
        PossessoId possessoId5B6 = new PossessoId(
                biblioteca4.getEmail(),
                libro37.getIdLibro()
        );
        PossessoId possessoId5B7 = new PossessoId(
                biblioteca4.getEmail(),
                libro38.getIdLibro()
        );
        PossessoId possessoId5B8 = new PossessoId(
                biblioteca4.getEmail(),
                libro39.getIdLibro()
        );

        Possesso possesso5B1 = new Possesso(
                possessoId5B1,
                10
        );
        Possesso possesso5B2 = new Possesso(
                possessoId5B2,
                10
        );
        Possesso possesso5B3 = new Possesso(
                possessoId5B3,
                10
        );
        Possesso possesso5B4 = new Possesso(
                possessoId5B4,
                10
        );
        Possesso possesso5B5 = new Possesso(
                possessoId5B5,
                10
        );
        Possesso possesso5B6 = new Possesso(
                possessoId5B6,
                10
        );
        Possesso possesso5B7 = new Possesso(
                possessoId5B7,
                10
        );
        Possesso possesso5B8 = new Possesso(
                possessoId5B8,
                10
        );

        possessoDAO.save(possesso5B1);
        possessoDAO.save(possesso5B2);
        possessoDAO.save(possesso5B3);
        possessoDAO.save(possesso5B4);
        possessoDAO.save(possesso5B5);
        possessoDAO.save(possesso5B6);
        possessoDAO.save(possesso5B7);
        possessoDAO.save(possesso5B8);


        //BIBLIOTECA 6

        PossessoId possessoId6B1 = new PossessoId(
                biblioteca5.getEmail(),
                libro1.getIdLibro()
        );
        PossessoId possessoId6B2 = new PossessoId(
                biblioteca5.getEmail(),
                libro2.getIdLibro()
        );
        PossessoId possessoId6B3 = new PossessoId(
                biblioteca5.getEmail(),
                libro3.getIdLibro()
        );
        PossessoId possessoId6B4 = new PossessoId(
                biblioteca5.getEmail(),
                libro4.getIdLibro()
        );
        PossessoId possessoId6B5 = new PossessoId(
                biblioteca5.getEmail(),
                libro5.getIdLibro()
        );
        PossessoId possessoId6B6 = new PossessoId(
                biblioteca5.getEmail(),
                libro6.getIdLibro()
        );
        PossessoId possessoId6B7 = new PossessoId(
                biblioteca5.getEmail(),
                libro7.getIdLibro()
        );
        PossessoId possessoId6B8 = new PossessoId(
                biblioteca5.getEmail(),
                libro8.getIdLibro()
        );

        Possesso possesso6B1 = new Possesso(
                possessoId6B1,
                10
        );
        Possesso possesso6B2 = new Possesso(
                possessoId6B2,
                10
        );
        Possesso possesso6B3 = new Possesso(
                possessoId6B3,
                10
        );
        Possesso possesso6B4 = new Possesso(
                possessoId6B4,
                10
        );
        Possesso possesso6B5 = new Possesso(
                possessoId6B5,
                10
        );
        Possesso possesso6B6 = new Possesso(
                possessoId6B6,
                10
        );
        Possesso possesso6B7 = new Possesso(
                possessoId6B7,
                10
        );
        Possesso possesso6B8 = new Possesso(
                possessoId6B8,
                10
        );

        possessoDAO.save(possesso6B1);
        possessoDAO.save(possesso6B2);
        possessoDAO.save(possesso6B3);
        possessoDAO.save(possesso6B4);
        possessoDAO.save(possesso6B5);
        possessoDAO.save(possesso6B6);
        possessoDAO.save(possesso6B7);
        possessoDAO.save(possesso6B8);


        //BIBLIOTECA 7
        PossessoId possessoId7B1 = new PossessoId(
                biblioteca6.getEmail(),
                libro9.getIdLibro()
        );
        PossessoId possessoId7B2 = new PossessoId(
                biblioteca6.getEmail(),
                libro10.getIdLibro()
        );
        PossessoId possessoId7B3 = new PossessoId(
                biblioteca6.getEmail(),
                libro11.getIdLibro()
        );
        PossessoId possessoId7B4 = new PossessoId(
                biblioteca6.getEmail(),
                libro12.getIdLibro()
        );
        PossessoId possessoId7B5 = new PossessoId(
                biblioteca6.getEmail(),
                libro13.getIdLibro()
        );
        PossessoId possessoId7B6 = new PossessoId(
                biblioteca6.getEmail(),
                libro14.getIdLibro()
        );
        PossessoId possessoId7B7 = new PossessoId(
                biblioteca6.getEmail(),
                libro15.getIdLibro()
        );
        PossessoId possessoId7B8 = new PossessoId(
                biblioteca6.getEmail(),
                libro16.getIdLibro()
        );

        Possesso possesso7B1 = new Possesso(
                possessoId7B1,
                10
        );
        Possesso possesso7B2 = new Possesso(
                possessoId7B2,
                10
        );
        Possesso possesso7B3 = new Possesso(
                possessoId7B3,
                10
        );
        Possesso possesso7B4 = new Possesso(
                possessoId7B4,
                10
        );
        Possesso possesso7B5 = new Possesso(
                possessoId7B5,
                10
        );
        Possesso possesso7B6 = new Possesso(
                possessoId7B6,
                10
        );
        Possesso possesso7B7 = new Possesso(
                possessoId7B7,
                10
        );
        Possesso possesso7B8 = new Possesso(
                possessoId7B8,
                10
        );

        possessoDAO.save(possesso7B1);
        possessoDAO.save(possesso7B2);
        possessoDAO.save(possesso7B3);
        possessoDAO.save(possesso7B4);
        possessoDAO.save(possesso7B5);
        possessoDAO.save(possesso7B6);
        possessoDAO.save(possesso7B7);
        possessoDAO.save(possesso7B8);

        //BIBLIOTECA 8

        PossessoId possessoId8B1 = new PossessoId(
                biblioteca7.getEmail(),
                libro17.getIdLibro()
        );
        PossessoId possessoId8B2 = new PossessoId(
                biblioteca7.getEmail(),
                libro18.getIdLibro()
        );
        PossessoId possessoId8B3 = new PossessoId(
                biblioteca7.getEmail(),
                libro19.getIdLibro()
        );
        PossessoId possessoId8B4 = new PossessoId(
                biblioteca7.getEmail(),
                libro20.getIdLibro()
        );
        PossessoId possessoId8B5 = new PossessoId(
                biblioteca7.getEmail(),
                libro21.getIdLibro()
        );
        PossessoId possessoId8B6 = new PossessoId(
                biblioteca7.getEmail(),
                libro22.getIdLibro()
        );
        PossessoId possessoId8B7 = new PossessoId(
                biblioteca7.getEmail(),
                libro23.getIdLibro()
        );
        PossessoId possessoId8B8 = new PossessoId(
                biblioteca7.getEmail(),
                libro24.getIdLibro()
        );

        Possesso possesso8B1 = new Possesso(
                possessoId8B1,
                10
        );
        Possesso possesso8B2 = new Possesso(
                possessoId8B2,
                10
        );
        Possesso possesso8B3 = new Possesso(
                possessoId8B3,
                10
        );
        Possesso possesso8B4 = new Possesso(
                possessoId8B4,
                10
        );
        Possesso possesso8B5 = new Possesso(
                possessoId8B5,
                10
        );
        Possesso possesso8B6 = new Possesso(
                possessoId8B6,
                10
        );
        Possesso possesso8B7 = new Possesso(
                possessoId8B7,
                10
        );
        Possesso possesso8B8 = new Possesso(
                possessoId8B8,
                10
        );

        possessoDAO.save(possesso8B1);
        possessoDAO.save(possesso8B2);
        possessoDAO.save(possesso8B3);
        possessoDAO.save(possesso8B4);
        possessoDAO.save(possesso8B5);
        possessoDAO.save(possesso8B6);
        possessoDAO.save(possesso8B7);
        possessoDAO.save(possesso8B8);


        //BIBLIOTECA 9

        PossessoId possessoId9B1 = new PossessoId(
                biblioteca8.getEmail(),
                libro32.getIdLibro()
        );
        PossessoId possessoId9B2 = new PossessoId(
                biblioteca8.getEmail(),
                libro25.getIdLibro()
        );
        PossessoId possessoId9B3 = new PossessoId(
                biblioteca8.getEmail(),
                libro26.getIdLibro()
        );
        PossessoId possessoId9B4 = new PossessoId(
                biblioteca8.getEmail(),
                libro27.getIdLibro()
        );
        PossessoId possessoId9B5 = new PossessoId(
                biblioteca8.getEmail(),
                libro28.getIdLibro()
        );
        PossessoId possessoId9B6 = new PossessoId(
                biblioteca8.getEmail(),
                libro29.getIdLibro()
        );
        PossessoId possessoId9B7 = new PossessoId(
                biblioteca8.getEmail(),
                libro30.getIdLibro()
        );
        PossessoId possessoId9B8 = new PossessoId(
                biblioteca8.getEmail(),
                libro31.getIdLibro()
        );

        Possesso possesso9B1 = new Possesso(
                possessoId9B1,
                10
        );
        Possesso possesso9B2 = new Possesso(
                possessoId9B2,
                10
        );
        Possesso possesso9B3 = new Possesso(
                possessoId9B3,
                10
        );
        Possesso possesso9B4 = new Possesso(
                possessoId9B4,
                10
        );
        Possesso possesso9B5 = new Possesso(
                possessoId9B5,
                10
        );
        Possesso possesso9B6 = new Possesso(
                possessoId9B6,
                10
        );
        Possesso possesso9B7 = new Possesso(
                possessoId9B7,
                10
        );
        Possesso possesso9B8 = new Possesso(
                possessoId9B8,
                10
        );

        possessoDAO.save(possesso9B1);
        possessoDAO.save(possesso9B2);
        possessoDAO.save(possesso9B3);
        possessoDAO.save(possesso9B4);
        possessoDAO.save(possesso9B5);
        possessoDAO.save(possesso9B6);
        possessoDAO.save(possesso9B7);
        possessoDAO.save(possesso9B8);


        //BIBLIOTECA 10

        PossessoId possessoId10B1 = new PossessoId(
                biblioteca9.getEmail(),
                libro33.getIdLibro()
        );
        PossessoId possessoId10B2 = new PossessoId(
                biblioteca9.getEmail(),
                libro40.getIdLibro()
        );
        PossessoId possessoId10B3 = new PossessoId(
                biblioteca9.getEmail(),
                libro34.getIdLibro()
        );
        PossessoId possessoId10B4 = new PossessoId(
                biblioteca9.getEmail(),
                libro35.getIdLibro()
        );
        PossessoId possessoId10B5 = new PossessoId(
                biblioteca9.getEmail(),
                libro36.getIdLibro()
        );
        PossessoId possessoId10B6 = new PossessoId(
                biblioteca9.getEmail(),
                libro37.getIdLibro()
        );
        PossessoId possessoId10B7 = new PossessoId(
                biblioteca9.getEmail(),
                libro38.getIdLibro()
        );
        PossessoId possessoId10B8 = new PossessoId(
                biblioteca9.getEmail(),
                libro39.getIdLibro()
        );

        Possesso possesso10B1 = new Possesso(
                possessoId10B1,
                10
        );
        Possesso possesso10B2 = new Possesso(
                possessoId10B2,
                10
        );
        Possesso possesso10B3 = new Possesso(
                possessoId10B3,
                10
        );
        Possesso possesso10B4 = new Possesso(
                possessoId10B4,
                10
        );
        Possesso possesso10B5 = new Possesso(
                possessoId10B5,
                10
        );
        Possesso possesso10B6 = new Possesso(
                possessoId10B6,
                10
        );
        Possesso possesso10B7 = new Possesso(
                possessoId10B7,
                10
        );
        Possesso possesso10B8 = new Possesso(
                possessoId10B8,
                10
        );

        possessoDAO.save(possesso10B1);
        possessoDAO.save(possesso10B2);
        possessoDAO.save(possesso10B3);
        possessoDAO.save(possesso10B4);
        possessoDAO.save(possesso10B5);
        possessoDAO.save(possesso10B6);
        possessoDAO.save(possesso10B7);
        possessoDAO.save(possesso10B8);

//----------------------Definizione ed inserimento ticket prestiti------------------------------------------------------


        TicketPrestito ticket = new TicketPrestito(
                TicketPrestito.Stati.IN_ATTESA_DI_CONFERMA,
                LocalDateTime.now(),
                libro1,
                biblioteca,
                lettore
        );

        TicketPrestito ticket1 = new TicketPrestito(
                TicketPrestito.Stati.IN_ATTESA_DI_RESTITUZIONE,
                LocalDateTime.now(),
                libro2,
                biblioteca,
                lettore2
        );

        TicketPrestito ticket2 = new TicketPrestito(
                TicketPrestito.Stati.IN_ATTESA_DI_CONFERMA,
                LocalDateTime.now(),
                libro3,
                biblioteca,
                lettore3
        );

        ticketPrestitoDAO.save(ticket);
        ticketPrestitoDAO.save(ticket1);
        ticketPrestitoDAO.save(ticket2);

        TicketPrestito ticket3 = new TicketPrestito(
                TicketPrestito.Stati.IN_ATTESA_DI_CONFERMA,
                LocalDateTime.now(),
                libro8,
                biblioteca1,
                lettore4
        );

        TicketPrestito ticket4 = new TicketPrestito(
                TicketPrestito.Stati.IN_ATTESA_DI_RESTITUZIONE,
                LocalDateTime.now(),
                libro9,
                biblioteca1,
                lettore5
        );

        TicketPrestito ticket5 = new TicketPrestito(
                TicketPrestito.Stati.IN_ATTESA_DI_CONFERMA,
                LocalDateTime.now(),
                libro10,
                biblioteca1,
                lettore6
        );

        ticketPrestitoDAO.save(ticket3);
        ticketPrestitoDAO.save(ticket4);
        ticketPrestitoDAO.save(ticket5);

        TicketPrestito ticket6 = new TicketPrestito(
                TicketPrestito.Stati.IN_ATTESA_DI_CONFERMA,
                LocalDateTime.now(),
                libro17,
                biblioteca2,
                lettore9
        );

        TicketPrestito ticket7 = new TicketPrestito(
                TicketPrestito.Stati.IN_ATTESA_DI_RESTITUZIONE,
                LocalDateTime.now(),
                libro18,
                biblioteca2,
                lettore10
        );

        TicketPrestito ticket8 = new TicketPrestito(
                TicketPrestito.Stati.IN_ATTESA_DI_CONFERMA,
                LocalDateTime.now(),
                libro19,
                biblioteca2,
                lettore12
        );

        ticketPrestitoDAO.save(ticket6);
        ticketPrestitoDAO.save(ticket7);
        ticketPrestitoDAO.save(ticket8);

//----------------------Definizione ed inserimento clubs----------------------------------------------------------------


        ClubDelLibro clubDelLibro1 = new ClubDelLibro(
                "Fantasticy",
                "Si parla di libri fantasy",
                esperto
        );

        ClubDelLibro clubDelLibro2 = new ClubDelLibro(
                "Storici si diventa",
                "Siamo appassionati di storia",
                esperto1
        );


        ClubDelLibro clubDelLibro3 = new ClubDelLibro(
                "Programmatori uniti",
                "Leggiamo documentazione javadoc",
                esperto3
        );

        ClubDelLibro clubDelLibro4 = new ClubDelLibro(
                "Favole e fiabe",
                "Per tutti gli appassionati delle favole!",
                esperto5
        );

        ClubDelLibro clubDelLibro5 = new ClubDelLibro(
                "The Pirates",
                "Appassionati all'avventura e all'azione!",
                esperto8
        );

        ClubDelLibro clubDelLibro6 = new ClubDelLibro(
                "Gli investigatori di Biblionet",
                "Qui ci sono i migliori intenditori di gialli",
                esperto11
        );

        ClubDelLibro clubDelLibro7 = new ClubDelLibro(
                "Psicologia e scienza",
                "Analizziamo testi psicologici e scientifici",
                esperto13
        );

        ClubDelLibro clubDelLibro8 = new ClubDelLibro(
                "#inRosa",
                "Per chi non ha vergogna di ammettere di amare il rosa",
                esperto20
        );

        ClubDelLibro clubDelLibro9 = new ClubDelLibro(
                "BlackBoys",
                "Ami il thriller e il noir? Unisciti a noi!",
                esperto15
        );

        ClubDelLibro clubDelLibro10 = new ClubDelLibro(
                "BOO!",
                "Per i veri appassionati di horror!",
                esperto17
        );

        clubDelLibroDAO.save(clubDelLibro1);
        clubDelLibroDAO.save(clubDelLibro2);
        clubDelLibroDAO.save(clubDelLibro3);
        clubDelLibroDAO.save(clubDelLibro4);
        clubDelLibroDAO.save(clubDelLibro5);
        clubDelLibroDAO.save(clubDelLibro6);
        clubDelLibroDAO.save(clubDelLibro7);
        clubDelLibroDAO.save(clubDelLibro8);
        clubDelLibroDAO.save(clubDelLibro9);
        clubDelLibroDAO.save(clubDelLibro10);

//----------------------Definizione ed inserimento eventi---------------------------------------------------------------

        Evento evento = new Evento(
                "Evento fantastyco",
                "Evento fantastyco per gente fantastyca",
                LocalDateTime.now(),
                clubDelLibro1
        );

        Evento evento2 = new Evento(
                "Analizziamo la GEOGRAFIA",
                "Sembra noioso ma non dovrebbe esserlo troppo",
                LocalDateTime.now(),
                clubDelLibro2
        );

        Evento evento3 = new Evento(
                "Impariamo i Design Patterns",
                "Impariamo i Design Patterns come veri ingegneri del software",
                LocalDateTime.now(),
                clubDelLibro3
        );

        Evento evento4 = new Evento(
                "Fiabe originali vs Disney",
                "Analizziamo le differenze fra i famosi libri di fiabe e i classici Disney",
                LocalDateTime.now(),
                clubDelLibro4
        );

        Evento evento5 = new Evento(
                "Azione 01!",
                "Parliamo dei migliori libri d'azione del mese di gennaio",
                LocalDateTime.now(),
                clubDelLibro5
        );

        Evento evento6 = new Evento(
                "Risolviamo il caso",
                "Chi riesce a indovinare il colpevole prima della fine del libro?",
                LocalDateTime.now(),
                clubDelLibro6
        );

        Evento evento7 = new Evento(
                "Freud: genio o cretino?",
                "Discussione sulle tesi di Freud",
                LocalDateTime.now(),
                clubDelLibro8
        );


        Evento evento8 = new Evento(
                "Classifica romanzi rosa",
                "Discutiamo insieme e stiliamo una classifica dei romanzi rosa preferiti!",
                LocalDateTime.now(),
                clubDelLibro7
        );

        Evento evento9 = new Evento(
                "King's Fanboy",
                "Gloria alle storie di King",
                LocalDateTime.now(),
                clubDelLibro9
        );

        Evento evento10 = new Evento(
                "Maratona Harry Potter",
                "Rileggiamo le parti più belle della saga di Harry Potter",
                LocalDateTime.now(),
                clubDelLibro1
        );

        Evento evento11 = new Evento(
                "Analizziamo la STORIA",
                "Sembra noioso ma non dovrebbe esserlo troppo",
                LocalDateTime.now(),
                clubDelLibro2
        );

        Evento evento12 = new Evento(
                "Le basi per un buon giallo",
                "Un esperto tiene una lezione su come scrivere un buon giallo",
                LocalDateTime.now(),
                clubDelLibro6
        );

        Evento evento13 = new Evento(
                "Thrills and Chills",
                "Quale thriller non ti ha lasciato col fiato sospeso?",
                LocalDateTime.now(),
                clubDelLibro9
        );

        eventoDAO.save(evento);
        eventoDAO.save(evento2);
        eventoDAO.save(evento3);
        eventoDAO.save(evento4);
        eventoDAO.save(evento5);
        eventoDAO.save(evento6);
        eventoDAO.save(evento7);
        eventoDAO.save(evento8);
        eventoDAO.save(evento9);
        eventoDAO.save(evento10);
        eventoDAO.save(evento11);
        eventoDAO.save(evento12);
        eventoDAO.save(evento13);

//-------------------------------POPOLAMENTO MANY TO MANY E ONE TO MANY-------------------------------------------------


//-------------------Associo ai lettori un club del libro---------------------------------------------------------------

        lettore.setClubs(Arrays.asList(clubDelLibro1, clubDelLibro2, clubDelLibro3, clubDelLibro4));
        lettore1.setClubs(Arrays.asList(clubDelLibro5, clubDelLibro6, clubDelLibro7, clubDelLibro8));
        lettore2.setClubs(Arrays.asList(clubDelLibro9, clubDelLibro10));
        lettore3.setClubs(Arrays.asList(clubDelLibro1, clubDelLibro6, clubDelLibro9));
        lettore4.setClubs(Arrays.asList(clubDelLibro5, clubDelLibro2, clubDelLibro3, clubDelLibro8));
        lettore5.setClubs(Arrays.asList(clubDelLibro9, clubDelLibro10));
        lettore6.setClubs(Arrays.asList(clubDelLibro1, clubDelLibro6, clubDelLibro9));
        lettore7.setClubs(Arrays.asList(clubDelLibro3, clubDelLibro9, clubDelLibro5, clubDelLibro8));
        lettore8.setClubs(Arrays.asList(clubDelLibro9, clubDelLibro4));
        lettore9.setClubs(Arrays.asList(clubDelLibro1, clubDelLibro7, clubDelLibro9));
        lettore10.setClubs(Arrays.asList(clubDelLibro5, clubDelLibro2, clubDelLibro1, clubDelLibro10));


        lettoreDAO.save(lettore);
        lettoreDAO.save(lettore1);
        lettoreDAO.save(lettore2);
        lettoreDAO.save(lettore3);
        lettoreDAO.save(lettore4);
        lettoreDAO.save(lettore5);
        lettoreDAO.save(lettore6);
        lettoreDAO.save(lettore7);
        lettoreDAO.save(lettore8);
        lettoreDAO.save(lettore9);
        lettoreDAO.save(lettore10);


//------------------Associo dei ticket alle biblioteche-----------------------------------------------------------------

        biblioteca.setTickets(Arrays.asList(ticket, ticket1, ticket2));
        biblioteca1.setTickets(Arrays.asList(ticket3, ticket4, ticket5));
        biblioteca2.setTickets(Arrays.asList(ticket6, ticket7, ticket8));

        bibliotecaDAO.save(biblioteca);
        bibliotecaDAO.save(biblioteca1);
        bibliotecaDAO.save(biblioteca2);

//-----------------Associo degli eventi ai club del libro---------------------------------------------------------------

        clubDelLibro1.setEventi(Arrays.asList(evento));
        clubDelLibro2.setEventi(Arrays.asList(evento2, evento11));
        clubDelLibro3.setEventi(Arrays.asList(evento3));
        clubDelLibro4.setEventi(Arrays.asList(evento4));
        clubDelLibro5.setEventi(Arrays.asList(evento5));
        clubDelLibro6.setEventi(Arrays.asList(evento6, evento12));
        clubDelLibro7.setEventi(Arrays.asList(evento8));
        clubDelLibro8.setEventi(Arrays.asList(evento7));
        clubDelLibro9.setEventi(Arrays.asList(evento9, evento13));
        clubDelLibro10.setEventi(Arrays.asList(evento10));

        clubDelLibroDAO.save(clubDelLibro1);
        clubDelLibroDAO.save(clubDelLibro2);
        clubDelLibroDAO.save(clubDelLibro3);
        clubDelLibroDAO.save(clubDelLibro4);
        clubDelLibroDAO.save(clubDelLibro5);
        clubDelLibroDAO.save(clubDelLibro6);
        clubDelLibroDAO.save(clubDelLibro7);
        clubDelLibroDAO.save(clubDelLibro8);
        clubDelLibroDAO.save(clubDelLibro9);
        clubDelLibroDAO.save(clubDelLibro10);

//------------------Associo dei generi ai club del libro----------------------------------------------------------------

        clubDelLibro1.setGeneri(Arrays.asList(fantasy, fantascienza, romanzo));
        clubDelLibro2.setGeneri(Arrays.asList(storico, saggio, biografico));
        clubDelLibro3.setGeneri(Arrays.asList(tecnologia, saggio, scientifico));
        clubDelLibro4.setGeneri(Arrays.asList(fiabefavole, ragazzi, narrativa, romanzo));
        clubDelLibro5.setGeneri(Arrays.asList(azione, avventura, narrativa));
        clubDelLibro6.setGeneri(Arrays.asList(giallo, thriller, romanzo));
        clubDelLibro7.setGeneri(Arrays.asList(psicologico, scientifico, tecnologia));
        clubDelLibro8.setGeneri(Arrays.asList(romantico, romanzo));
        clubDelLibro9.setGeneri(Arrays.asList(noir, thriller));
        clubDelLibro10.setGeneri(Arrays.asList(horror, thriller));

        clubDelLibroDAO.save(clubDelLibro1);
        clubDelLibroDAO.save(clubDelLibro2);
        clubDelLibroDAO.save(clubDelLibro3);
        clubDelLibroDAO.save(clubDelLibro4);
        clubDelLibroDAO.save(clubDelLibro5);
        clubDelLibroDAO.save(clubDelLibro6);
        clubDelLibroDAO.save(clubDelLibro7);
        clubDelLibroDAO.save(clubDelLibro8);
        clubDelLibroDAO.save(clubDelLibro9);
        clubDelLibroDAO.save(clubDelLibro10);

//------------------Associo degli esperti ai club del libro-------------------------------------------------------------

        esperto.setClubs(Arrays.asList(clubDelLibro1));
        esperto1.setClubs(Arrays.asList(clubDelLibro2));
        esperto3.setClubs(Arrays.asList(clubDelLibro3));
        esperto5.setClubs(Arrays.asList(clubDelLibro4));
        esperto8.setClubs(Arrays.asList(clubDelLibro5));
        esperto11.setClubs(Arrays.asList(clubDelLibro6));
        esperto13.setClubs(Arrays.asList(clubDelLibro7));
        esperto15.setClubs(Arrays.asList(clubDelLibro9));
        esperto17.setClubs(Arrays.asList(clubDelLibro10));
        esperto20.setClubs(Arrays.asList(clubDelLibro8));

        espertoDAO.save(esperto);
        espertoDAO.save(esperto1);
        espertoDAO.save(esperto3);
        espertoDAO.save(esperto5);
        espertoDAO.save(esperto8);
        espertoDAO.save(esperto11);
        espertoDAO.save(esperto13);
        espertoDAO.save(esperto15);
        espertoDAO.save(esperto17);
        espertoDAO.save(esperto20);

//-----------------Associo dei generi agli esperti----------------------------------------------------------------------

        esperto.setGeneri(Arrays.asList(fantasy,fantascienza));
        esperto1.setGeneri(Arrays.asList(politico,storico));
        esperto2.setGeneri(Arrays.asList(romantico, avventura));
        esperto3.setGeneri(Arrays.asList(tecnologia, scientifico));
        esperto4.setGeneri(Arrays.asList(noir, thriller, horror));
        esperto5.setGeneri(Arrays.asList(fiabefavole, fantasy, ragazzi));
        esperto6.setGeneri(Arrays.asList(storico, scientifico, saggio));
        esperto7.setGeneri(Arrays.asList(narrativa, romanzo, azione));
        esperto8.setGeneri(Arrays.asList(azione, avventura, fantascienza));
        esperto10.setGeneri(Arrays.asList(space,scientifico));
        esperto11.setGeneri(Arrays.asList(giallo, thriller, narrativa));
        esperto12.setGeneri(Arrays.asList(romantico, romanzo, ragazzi));
        esperto13.setGeneri(Arrays.asList(psicologico, scientifico));
        esperto14.setGeneri(Arrays.asList(fantascienza, comico, saggio));
        esperto15.setGeneri(Arrays.asList(noir, thriller));
        esperto16.setGeneri(Arrays.asList(ragazzi, politico, horror));
        esperto17.setGeneri(Arrays.asList(horror, narrativa, romanzo));
        esperto18.setGeneri(Arrays.asList(formazione, biografico));
        esperto19.setGeneri(Arrays.asList(fantascienza, fantasy, romantico));
        esperto20.setGeneri(Arrays.asList(romantico, romanzo));

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
        espertoDAO.save(esperto10);
        espertoDAO.save(esperto11);
        espertoDAO.save(esperto12);
        espertoDAO.save(esperto13);
        espertoDAO.save(esperto14);
        espertoDAO.save(esperto15);
        espertoDAO.save(esperto16);
        espertoDAO.save(esperto17);
        espertoDAO.save(esperto18);
        espertoDAO.save(esperto19);
        espertoDAO.save(esperto20);

//-----------------Associo dei lettori agli eventi----------------------------------------------------------------------

        lettore.setEventi(Arrays.asList(evento, evento2));
        lettore1.setEventi(Arrays.asList(evento5, evento6));
        lettore2.setEventi(Arrays.asList(evento13));

        lettoreDAO.save(lettore);
        lettoreDAO.save(lettore1);
        lettoreDAO.save(lettore2);

//-----------------Associo dei generi ai lettori------------------------------------------------------------------------

        lettore.setGeneri(Arrays.asList(noir, politico));
        lettore1.setGeneri(Arrays.asList(ragazzi, avventura));
        lettore2.setGeneri(Arrays.asList(storico, saggio));
        lettore3.setGeneri(Arrays.asList(space, scientifico));
        lettore4.setGeneri(Arrays.asList(noir, politico));
        lettore5.setGeneri(Arrays.asList(ragazzi, avventura));
        lettore6.setGeneri(Arrays.asList(storico, saggio));
        lettore7.setGeneri(Arrays.asList(space, scientifico));
        lettore8.setGeneri(Arrays.asList(noir, politico));
        lettore9.setGeneri(Arrays.asList(ragazzi, avventura));
        lettore10.setGeneri(Arrays.asList(storico, saggio));

        lettoreDAO.save(lettore);
        lettoreDAO.save(lettore1);
        lettoreDAO.save(lettore2);
        lettoreDAO.save(lettore3);
        lettoreDAO.save(lettore4);
        lettoreDAO.save(lettore5);
        lettoreDAO.save(lettore6);
        lettoreDAO.save(lettore7);
        lettoreDAO.save(lettore8);
        lettoreDAO.save(lettore9);
        lettoreDAO.save(lettore10);


//----------------Associo dei ticket ai lettori-------------------------------------------------------------------------

        lettore.setTickets(Arrays.asList(ticket));
        lettore2.setTickets(Arrays.asList(ticket1));
        lettore3.setTickets(Arrays.asList(ticket2));
        lettore4.setTickets(Arrays.asList(ticket3));
        lettore5.setTickets(Arrays.asList(ticket4));
        lettore6.setTickets(Arrays.asList(ticket5));
        lettore9.setTickets(Arrays.asList(ticket6));
        lettore10.setTickets(Arrays.asList(ticket7));
        lettore12.setTickets(Arrays.asList(ticket8));

        lettoreDAO.save(lettore);
        lettoreDAO.save(lettore1);
        lettoreDAO.save(lettore2);
        lettoreDAO.save(lettore3);

    }
}
