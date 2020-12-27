package it.unisa.c07.biblionet.clubDelLibro.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import it.unisa.c07.biblionet.clubDelLibro.service.ClubDelLibroService;
import it.unisa.c07.biblionet.gestioneEventi.service.GestioneEventiService;
import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.Evento;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.form.EventoForm;
import it.unisa.c07.biblionet.utils.validazione.ValidazioneEvento;
import lombok.RequiredArgsConstructor;


/**
 * Implementa il controller per il sottosistema
 * ClubDelLibro.
 * @author Viviana Pentangelo
 * @author Gianmario Voria
 * @author Nicola Pagliara
 * @author Luca Topo
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/club-del-libro")
public class ClubDelLibroController {

    /**
     * Il service per effettuare le operazioni di persistenza.
     */
    private final ClubDelLibroService clubService;

    /**
     * Il service per effettuare le operazioni di persistenza
     * degli eventi.
     */
    private final GestioneEventiService eventiService;

    /**
     * Implementa la funzionalità che permette
     * di visualizzare i Club del Libro
     * presenti nel Database.
     * @param model L'oggetto model usato per inserire gli attributi
     * @return La pagina di visualizzazione
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String visualizzaListaClubs(final Model model) {
        model.addAttribute("listaClubs", clubService.visualizzaClubsDelLibro());
        return "visualizza-clubs";
    }

    /**
     * Implementa la funzionalità che permette
     * di gestire la chiamata POST
     * per creare un club del libro.
     * @param nome Il nome del club
     * @param descrizione La descrizione del club
     * @param copertina L'immagine di copertina del Club
     * @param generi Lista dei generi del club
     * @return La view che visualizza i Club
     */
    @RequestMapping(value = "/crea", method = RequestMethod.POST)
    public String creaClubDelLibro(final @RequestParam(value = "nome")
                  String nome,
                  final @RequestParam(value = "descrizione")
                  String descrizione,
                  final @RequestParam(value = "generi", required = false)
                  String[] generi,
                  final @RequestParam(value = "copertina", required = false)
                  MultipartFile copertina) {
        //Sarà modificato quando ci sarà la sessione.
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
                new Biblioteca(
                        "bibliotecacarrisi@gmail.com",
                        "BibliotecaPassword",
                        "Napoli",
                        "Torre del Greco",
                        "Via Carrisi 47",
                        "1234567890",
                        "Biblioteca Carrisi"
                )
        );
        ClubDelLibro club = new ClubDelLibro();
        club.setNome(nome);
        club.setDescrizione(descrizione);
        club.setEsperto(esperto);
        try {
            byte[] imageBytes = copertina.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            club.setImmagineCopertina(base64Image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (generi != null) {
            List<Genere> gList = clubService
                    .getGeneri(Arrays.asList(generi.clone()));
            club.setGeneri(gList);
        }
        this.clubService.creaClubDelLibro(club);
        return "redirect:/club-del-libro/";
    }

    /**
     * Implementa la funzionalità che permette
     * di re-indirizzare alla pagina di modifica
     * dei dati di un Club del Libro.
     * @param id l'ID del Club da modificare
     * @param model l'oggetto model usato per inserire gli attributi
     * @return La view che visualizza il form di modifica dati
     */
    @RequestMapping(value = "/modifica-dati/{id}", method = RequestMethod.GET)
    public String visualizzaModificaDatiClub(final @PathVariable int id,
                                             final Model model) {
        model.addAttribute("club", this.clubService.getClubByID(id));
        return "modifica-club";
    }

    /**
     * Implementa la funzionalità che permette
     * di gestire la chiamata POST
     * per modificare i dati di un club del libro.
     * @param idClub L'id del club
     * @param nome Il nome del club
     * @param descrizione La descrizione del club
     * @param copertina L'immagine di copertina del Club
     * @param generi Lista dei generi del club
     * @return La view che visualizza i Club del Libro
     */
    @RequestMapping(value = "/modifica-dati",
                    method = RequestMethod.POST)
    public String modificaDatiClub(final @RequestParam(value = "idClub")
                        String idClub,
                        final @RequestParam(value = "nome")
                        String nome,
                        final @RequestParam(value = "descrizione")
                        String descrizione,
                        final @RequestParam(value = "generi", required = false)
                        String[] generi,
                        final @RequestParam(value = "copertina",
                                required = false)
                        MultipartFile copertina) {

        ClubDelLibro clubPers = this.clubService
                .getClubByID(Integer.parseInt(idClub));
        if (!copertina.isEmpty()) {
            try {
                byte[] imageBytes = copertina.getBytes();
                String base64Image = Base64.getEncoder()
                        .encodeToString(imageBytes);
                clubPers.setImmagineCopertina(base64Image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (generi != null) {
            List<Genere> gList = clubService
                    .getGeneri(Arrays.asList(generi.clone()));
            clubPers.setGeneri(gList);
        }
        clubPers.setNome(nome);
        clubPers.setDescrizione(descrizione);
        this.clubService.modificaDatiClub(clubPers);
        return "redirect:/club-del-libro/";
    }

    /**
     * Implementa la funzionalità che permette
     * l'iscrizione di un lettore ad un
     * Club del Libro.
     * @param id l'ID del Club a cui iscriversi
     * @return La view che visualizza la lista dei club
     */
    @RequestMapping(value = "/iscrizione-club/{id}", method = RequestMethod.GET)
    public String partecipaClub(final @PathVariable int id) {
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
        this.clubService.partecipaClub(
                            this.clubService.getClubByID(id),
                            lettore);
        return "redirect:/club-del-libro/";
    }

    /**
     * Implementa la funzionalità che permette
     * di gestire la chiamata POST
     * per creare un evento un club del libro.
     */
    // TODO: Gestione efficace degli errori
    @RequestMapping(value = "/{id}/crea-evento", method = RequestMethod.POST)
    public String creaEvento(final @PathVariable int id,
                             final @ModelAttribute EventoForm eventoForm) {
        var club = this.clubService.getClubByID(id);

        if (club == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Club del Libro Inesistente"
            );
        }

        var evento = new Evento();

        evento.setClub(club);

        if (!ValidazioneEvento.isNomeValido(eventoForm.getNome())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Lunghezza del nome non valida."
            );
        }

        evento.setNomeEvento(eventoForm.getDescrizione());

        if (!ValidazioneEvento.isDescrizioneValida(eventoForm.getDescrizione())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Lunghezza della descrizione non valida."
            );
        }

        evento.setDescrizione(eventoForm.getDescrizione());

        var dataOra = LocalDateTime.of(eventoForm.getData(), eventoForm.getOra());

        if (dataOra.isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Lunghezza della descrizione non valida."
            );
        }

        evento.setDataOra(dataOra);

        if (eventoForm.getLibro() != null) {
            var libro = this.eventiService.getLibroById(eventoForm.getLibro());
            if (libro.isEmpty()) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Il libro inserito non è valido."
                );
            }
            evento.setLibro(libro.get());
        }

        this.eventiService.creaEvento(evento);

        return "redirect:/club-del-libro/" + id + "/eventi";
    }

    /**
     * Implementa la funzionalità che permette
     * la creazione da parte di un Esperto
     * di un Evento.
     * @param clubDelLibro L'id del ClubDelLibro a cui è collegato l'Evento
     * @return La view che visualizza il form di creazione Evento
     */
    @RequestMapping(value = "/{id}/crea-evento", method = RequestMethod.GET)
    public String visualizzaCreaEvento(final @PathVariable int id,
                                       final @ModelAttribute EventoForm evento,
                                       final Model model) {
        var club = this.clubService.getClubByID(id);

        if (club == null) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Club del Libro Inesistente"
            );
        }

        model.addAttribute("club", club);
        model.addAttribute("evento", evento);

        return "aggiungi-evento";
    }

    /* Implementa la funzionalità che permette di gestire
     * la visualizzazione dei dati di un Club del Libro.
     * @param id l'ID del Club di cui visualizzare i dati
     * @param model il model per il passaggio dei dati
     * @return La view che visualizza i dati
     */
    @RequestMapping(value = "/visualizza-dati-club/{id}",
                                method = RequestMethod.GET)
    public String visualizzaDatiClub(final @PathVariable int id,
                                     final Model model) {
        model.addAttribute("club", clubService.getClubByID(id));
        return "visualizza-iscritti";
    }

}
