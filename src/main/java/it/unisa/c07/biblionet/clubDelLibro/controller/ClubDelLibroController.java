package it.unisa.c07.biblionet.clubDelLibro.controller;

import it.unisa.c07.biblionet.clubDelLibro.service.ClubDelLibroService;
import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.Lettore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;


/**
 * Implementa il controller per il sottosistema
 * ClubDelLibro.
 * @author Viviana Pentangelo, Gianmario Voria
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
}
