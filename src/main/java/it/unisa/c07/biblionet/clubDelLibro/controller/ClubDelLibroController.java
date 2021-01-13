package it.unisa.c07.biblionet.clubDelLibro.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.server.ResponseStatusException;

import it.unisa.c07.biblionet.clubDelLibro.service.ClubDelLibroService;
import it.unisa.c07.biblionet.gestioneEventi.service.GestioneEventiService;
import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.Evento;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import it.unisa.c07.biblionet.model.form.ClubForm;
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
     * Metodo di utilità che modifica o crea un evento, validando
     * i dati.
     * @param eventoForm Il form con i dati da modificare
     * @param view La view da restituire se l'operazione va a buon fine.
     * @param idClub L'id del club del libro in cui inserire l'evento.
     * @param idEvento L'id dell'evento, che può essere vuoto per ottenere
     *                 l'autoassegnazione.
     * @param operazione L'operazione, tra creazione e modifica, che si vuole
     *                   effettuare.
     * @return La view inserita.
     */
    private String modificaCreaEvento(final EventoForm eventoForm,
                                      final String view,
                                      final int idClub,
                                      final Optional<Integer> idEvento,
                                      final Consumer<Evento> operazione) {

        var club = this.clubService.getClubByID(idClub);

        if (club == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Club del Libro Inesistente"
            );
        }

        var evento = new Evento();

        if (idEvento.isPresent()) {
            evento.setIdEvento(idEvento.get());
        }

        evento.setClub(club);

        if (!ValidazioneEvento.isNomeValido(eventoForm.getNome())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Lunghezza del nome non valida."
            );
        }

        evento.setNomeEvento(eventoForm.getNome());

        if (!ValidazioneEvento.
                isDescrizioneValida(eventoForm.getDescrizione())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Lunghezza della descrizione non valida."
            );
        }

        evento.setDescrizione(eventoForm.getDescrizione());

        var dataOra =
                LocalDateTime.of(eventoForm.getData(), eventoForm.getOra());

        if (dataOra.isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Data non valida."
            );
        }

        evento.setDataOra(dataOra);

        if (eventoForm.getLibro() != null) {
            var libro =
                    this.eventiService.getLibroById(eventoForm.getLibro());
            if (libro.isEmpty()) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Il libro inserito non è valido."
                );
            }
            evento.setLibro(libro.get());
        }

        operazione.accept(evento);

        return view;

    }

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

                var generiDaDB =
                        clubService.getGeneri(generi.get());

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
                        public final String immagineCopertina =
                                                club.getImmagineCopertina();
                        public final Set<String> generi =
                                club.getGeneri()
                                        .stream()
                                        .map(Genere::getNome)
                                        .collect(Collectors.toSet());
                        public final int idClub = club.getIdClub();
                        public final int iscritti = club.getLettori().size();
                        public final String email =
                                club.getEsperto().getEmail();
                }
        ).collect(Collectors.toList()));

        model.addAttribute("generi", this.clubService.getTuttiGeneri());
        model.addAttribute("citta", this.clubService.getCitta());

        return "club-del-libro/visualizza-clubs";
    }

    /**
     * Implementa la funzionalità di visualizzare la pagina di creazione di
     * un club del libro.
     * @param model L'oggetto model usato per inserire gli attributi
     * @param club Il form in cui inserire i dati del club
     * @return La pagina del Club
     */
    @RequestMapping(value = "crea", method = RequestMethod.GET)
    public String visualizzaCreaClubDelLibro(final Model model,
                                             final @ModelAttribute
                                                ClubForm club) {
        var utente = (UtenteRegistrato) model.getAttribute("loggedUser");
        if (utente == null || !utente.getTipo().equals("Esperto")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        model.addAttribute("generi", this.clubService.getTuttiGeneri());
        model.addAttribute("club", club);

        return "club-del-libro/creazione-club";
    }

    /**
     * Implementa la funzionalità di creazione di un club del libro.
     * @param model L'oggetto model usato per inserire gli attributi
     * @param club Il club che si vuole creare
     * @return la pagina del Club
     */
    @RequestMapping(value = "/crea", method = RequestMethod.POST)
    public String creaClubDelLibro(final Model model,
                                   final @ModelAttribute ClubForm club) {
        UtenteRegistrato utente =
                (UtenteRegistrato) model.getAttribute("loggedUser");
        if (utente == null || !utente.getTipo().equals("Esperto")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        var esperto = (Esperto) utente;
        ClubDelLibro cdl = new ClubDelLibro();
        cdl.setNome(club.getNome());
        cdl.setDescrizione(club.getDescrizione());
        cdl.setEsperto(esperto);
        if (club.getCopertina() != null && !club.getCopertina().isEmpty()) {
                try {
                        byte[] imageBytes = club.getCopertina().getBytes();
                        String base64Image =
                                Base64.getEncoder().encodeToString(imageBytes);
                        cdl.setImmagineCopertina(base64Image);
                        } catch (IOException e) {
                        e.printStackTrace();
                        }
        }

        cdl.setGeneri(Arrays.asList(new Genere[] {}));
        if (club.getGeneri() != null) {
            cdl.setGeneri(
                    this.clubService.getGeneri(
                            club.getGeneri()
                )
            );
        }

        this.clubService.creaClubDelLibro(cdl);
        return "redirect:/club-del-libro/";
    }

    /**
     * Implementa la funzionalità che permette
     * di re-indirizzare alla pagina di modifica
     * dei dati di un Club del Libro.
     * @param id l'ID del Club da modificare
     * @param club Il club che si vuole creare
     * @param model l'oggetto model usato per inserire gli attributi
     * @return La view che visualizza il form di modifica dati
     */
    @RequestMapping(value = "/{id}/modifica", method = RequestMethod.GET)
    public String visualizzaModificaDatiClub(final @PathVariable int id,
                                             final @ModelAttribute
                                                     ClubForm club,
                                             final Model model) {
        var esperto = (UtenteRegistrato) model.getAttribute("loggedUser");
        var cdl = this.clubService.getClubByID(id);
        if (cdl == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (esperto == null
                || !cdl.getEsperto().getEmail().equals(esperto.getEmail())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        club.setNome(cdl.getNome());
        club.setDescrizione(cdl.getDescrizione());
        club.setGeneri(cdl.getGeneri().stream().map(Genere::getNome).
                collect(Collectors.toList()));

        model.addAttribute("club", club);
        model.addAttribute("id", id);
        model.addAttribute("generi", this.clubService.getTuttiGeneri());
        return "club-del-libro/modifica-club";
    }

    /**
     * Implementa la funzionalità per la modifica dei dati di un Club.
     * @param id Lo Id del Club
     * @param club Il form dove inserire i nuovi dati
     * @return La schermata del club
     */
    @RequestMapping(value = "/{id}/modifica",
            method = RequestMethod.POST)
    public String modificaDatiClub(final @PathVariable int id,
                                   final @ModelAttribute ClubForm club) {

        ClubDelLibro clubPers = this.clubService.getClubByID(id);
        if (!club.getCopertina().isEmpty()) {
            try {
                byte[] imageBytes = club.getCopertina().getBytes();
                String base64Image = Base64.getEncoder()
                        .encodeToString(imageBytes);
                clubPers.setImmagineCopertina(base64Image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (club.getGeneri() != null) {
            List<Genere> gList = clubService.getGeneri(club.getGeneri());
            clubPers.setGeneri(gList);
        }
        clubPers.setNome(club.getNome());
        clubPers.setDescrizione(club.getDescrizione());
        this.clubService.modificaDatiClub(clubPers);
        return "redirect:/club-del-libro/";
    }

    /**
     * Implementa la funzionalità che permette
     * l'iscrizione di un lettore ad un
     * Club del Libro.
     * @param id l'ID del Club a cui iscriversi
     * @param model Il model da passare alla view
     * @return La view che visualizza la lista dei club
     */
    @RequestMapping(value = "/{id}/iscrizione", method = RequestMethod.POST)
    public String partecipaClub(final @PathVariable int id,
                                final Model model) {

        UtenteRegistrato lettore =
                (UtenteRegistrato) model.getAttribute("loggedUser");
        if (lettore == null || !lettore.getTipo().equals("Lettore")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        this.clubService.partecipaClub(
                this.clubService.getClubByID(id),
                (Lettore) lettore);
        return "redirect:/club-del-libro/";
    }

    @RequestMapping(
        value = "/{idClub}/eventi/{idEvento}/modifica",
        method = RequestMethod.GET
    )
    public String visualizzaModificaEvento(final @PathVariable int idClub,
                                           final @PathVariable int idEvento,
                                           final @ModelAttribute
                                                       EventoForm evento,
                                           final Model model) {
        var eventoBaseOpt =
                this.eventiService.getEventoById(idEvento);
        var esperto = (UtenteRegistrato) model.getAttribute("loggedUser");

        if (eventoBaseOpt.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Evento Inesistente"
            );
        }

        if (esperto != null && !eventoBaseOpt.get().getClub().getEsperto()
                .getEmail().equals(esperto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        var eventoBase = eventoBaseOpt.get();

        if (eventoBase.getClub().getIdClub() != idClub) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "L'evento con id " + idEvento
                + "non è associato al club con id "
                + idClub + "."
            );
        }

        evento.setNome(eventoBase.getNomeEvento());
        evento.setData(eventoBase.getDataOra().toLocalDate());
        evento.setOra(eventoBase.getDataOra().toLocalTime());
        evento.setDescrizione(eventoBase.getDescrizione());
        if (evento.getLibro() != null) {
            evento.setLibro(eventoBase.getLibro().getIdLibro());
        }

        model.addAttribute("evento", evento);
        model.addAttribute("club", eventoBase.getClub());
        model.addAttribute("id", eventoBase.getIdEvento());

        return "club-del-libro/modifica-evento";
    }

    /**
     * Implementa la funzionalità che permette
     * di gestire la chiamata POST
     * per creare un evento un club del libro.
     */
    @RequestMapping(value = "/{id}/eventi/crea", method = RequestMethod.POST)
    public String creaEvento(final @PathVariable int id,
                             final @ModelAttribute EventoForm eventoForm) {
        return this.modificaCreaEvento(
            eventoForm,
            "redirect:/club-del-libro/" + id + "/eventi",
            id,
            Optional.empty(),
            this.eventiService::creaEvento
        );
    }

    @RequestMapping(value = "/{idClub}/eventi/{idEvento}/modifica",
            method = RequestMethod.POST)
    public String modificaEvento(final @PathVariable int idClub,
                                 final @PathVariable int idEvento,
                                 final @ModelAttribute EventoForm eventoForm) {
        return this.modificaCreaEvento(
            eventoForm,
            "redirect:/club-del-libro/" + idClub + "/eventi",
            idClub,
            Optional.of(idEvento),
            evento -> {
                var statusModifica =
                        this.eventiService.modificaEvento(evento);
                if (statusModifica.isEmpty()) {
                    throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "L'evento con id " + idEvento
                        + "non è associato al club con id "
                        + idClub + "."
                    );
                }
            }
        );
    }

    /**
     * Implementa la funzionalità che permette
     * la creazione da parte di un Esperto
     * di un Evento.
     * @return La view che visualizza il form di creazione Evento
     */
    @RequestMapping(value = "/{id}/eventi/crea", method = RequestMethod.GET)
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
    @RequestMapping(value = "/{club}/eventi/{id}",
            method = RequestMethod.DELETE)
    public String eliminaEvento(final @PathVariable int club,
                                final @PathVariable int id) {
        var eventoEliminato =
                this.eventiService.eliminaEvento(id);

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
        if (clubService.getClubByID(id) == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("club", clubService.getClubByID(id));
        model.addAttribute("eventi",
                clubService.getClubByID(id).getEventi());

        return "club-del-libro/visualizza-eventi";
    }

    /**
     * Implementa la funzionalità che permette di iscriversi
     * ad uno degli eventi presenti nella lista relativa ad
     * un Club del Libro.
     * @param idEvento l'evento a cui partecipare
     * @param model l'oggetto Model da cui ottenere il lettore autenticato
     * @return la view che visualizza la lista degli eventi
     */
    @RequestMapping(value = "/{id}/partecipa",
            method = RequestMethod.POST)
    public String partecipaEvento(final @PathVariable int idEvento,
                                  final Model model) {
        UtenteRegistrato utente =
                (UtenteRegistrato) model.getAttribute("loggedUser");
        if (utente == null || !utente.getTipo().equals("Lettore")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        eventiService.partecipaEvento((Lettore)utente, idEvento);
        return "club-del-libro/visualizza-eventi";
    }
}
