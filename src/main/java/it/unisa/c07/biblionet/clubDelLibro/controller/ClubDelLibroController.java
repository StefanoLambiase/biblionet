package it.unisa.c07.biblionet.clubDelLibro.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
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
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
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
@SessionAttributes("loggedUser")
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
     * @param generi Un Optional che contiene una lista di generi per cui
     *               filtrare
     * @param citta Un Optional che contiene una lista di possibili città
     * @return La pagina di visualizzazione
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String visualizzaListaClubs(@RequestParam(value = "generi")
                                                final Optional<List<String>>
                                                generi,
                                       @RequestParam(value = "citta")
                                                final Optional<List<String>>
                                                citta,
                                       final Model model) {

        // Molto più pulito della concatenazione con gli stream
        Predicate<ClubDelLibro> filtroGenere = x -> true;

        if (generi.isPresent()) {
                filtroGenere = x -> false;

                var generiDaDB = clubService.getGeneri(generi.get());

                for (Genere genere: generiDaDB) {
                        filtroGenere = filtroGenere.or(
                                c -> c.getGeneri().contains(genere)
                        );
                }
        }

        Predicate<ClubDelLibro> filtroCitta = x -> true;

        if (citta.isPresent()) {
                filtroCitta = x -> false;
                for (String cittaSingola: citta.get()) {
                        filtroCitta = filtroCitta.or(
                                c -> clubService.getCittaFromClubDelLibro(c)
                                                .equals(cittaSingola)
                        );
                }
        }

        List<ClubDelLibro> listaClubs = clubService.visualizzaClubsDelLibro(
                filtroCitta.and(filtroGenere)
        );


        // Necessito di un oggetto anonimo per evitare problemi con JS
        model.addAttribute("listaClubs", listaClubs.stream().map(
                club -> new Object() {
                        public final String nome = club.getNome();
                        public final String descrizione = 
                                                club.getDescrizione();
                        public final String nomeEsperto = club.getEsperto()
                                                              .getNome()
                                                          + " "
                                                          + club.getEsperto()
                                                                .getCognome();
                        public final String copertina = 
                                                club.getImmagineCopertina();
                        public final Set<String> generi = club.getGeneri()
                                                                .stream()
                                                                .map(Genere::getNome)
                                                                .collect(
                                                                    Collectors
                                                                    .toSet()
                                                                );
                        public final int idClub = club.getIdClub();
                        public final int iscritti = club.getLettori().size();
                }
        ).collect(Collectors.toList()));

        model.addAttribute("generi", this.clubService.getTuttiGeneri());
        model.addAttribute("citta", this.clubService.getCitta());

        return "club-del-libro/visualizza-clubs";
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
    public String creaClubDelLibro(final Model model,
                                   final @RequestParam(value = "nome")
                                           String nome,
                                   final @RequestParam(value = "descrizione")
                                           String descrizione,
                                   final @RequestParam(value = "generi", required = false)
                                           String[] generi,
                                   final @RequestParam(value = "copertina", required = false)
                                           MultipartFile copertina) {
        UtenteRegistrato utente = (UtenteRegistrato) model.getAttribute("loggedUser");
        if (utente == null || utente.getTipo() != "Esperto") {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        var esperto = (Esperto) utente;
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
        return "club-del-libro/modifica-club";
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
    @RequestMapping(value = "/{id}/iscrizione", method = RequestMethod.POST)
    public String partecipaClub(final @PathVariable int id,
                                final Model model) {

        UtenteRegistrato lettore = (UtenteRegistrato) model.getAttribute("loggedUser");
        if (lettore == null || lettore.getTipo() != "Lettore") {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        this.clubService.partecipaClub(
                this.clubService.getClubByID(id),
                (Lettore) lettore);
        return "redirect:/club-del-libro/";
    }

    /**
     * Implementa la funzionalità che permette
     * di gestire la chiamata POST
     * per creare un evento un club del libro.
     */
    // Gestione efficace degli errori
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
                    "Ora inserita non valida."
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

        return "club-del-libro/aggiungi-evento";
    }

    /**
     * Implementa la funzionalità che permette di gestire
     * la visualizzazione dei dati di un Club del Libro.
     * @param id l'ID del Club di cui visualizzare i dati
     * @param model il model per il passaggio dei dati
     * @return La view che visualizza i dati
     */
    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET)
    public String visualizzaDatiClub(final @PathVariable int id,
                                     final Model model) {
        model.addAttribute("club", clubService.getClubByID(id));
        return "club-del-libro/visualizza-singolo-club";
    }

    /**
     * Implementa la funzionalità che permette di eliminare
     * un evento.
     * @param club L'identificativo del Club dell'evento
     * @param id L'identificativo dell'evento da eliminare
     * @return La view della lista degli eventi
     */
    @RequestMapping(value = "/{club}/eventi/{id}", method = RequestMethod.DELETE)
    public String eliminaEvento(final @PathVariable int club,
                                final @PathVariable int id) {
        var eventoEliminato = this.eventiService.eliminaEvento(id);

        if (eventoEliminato.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Evento Inesistente"
            );
        }

        return "redirect:/club-del-libro/" + club + "/eventi";
    }

    @RequestMapping(value = "/{id}/iscritti",
            method = RequestMethod.GET)
    public String visualizzaIscrittiClub(final @PathVariable int id,
                                         final Model model) {
        model.addAttribute("club", clubService.getClubByID(id));
        return "club-del-libro/visualizza-iscritti";
    }

    @RequestMapping(value = "/{id}/eventi",
            method = RequestMethod.GET)
    public String visualizzaListaEventiClub(final @PathVariable int id,
                                            final Model model) {
        model.addAttribute("club", clubService.getClubByID(id));
        return "club-del-libro/visualizza-eventi";
    }
}
