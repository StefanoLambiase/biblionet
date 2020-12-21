package it.unisa.c07.biblionet.clubDelLibro.controller;

import it.unisa.c07.biblionet.clubDelLibro.service.ClubDelLibroService;
import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;


/**
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
     * Si occupa di visualizzare i Club del Libro
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
     * Metodo del controller che si occupa
     * di gestire la chiamata POST
     * per creare un club del libro.
     * @param club Il club del libro passato dalla view
     * @param copertina L'immagine di copertina del Club
     * @param generi Lista dei generi del club
     * @return La view che visualizza i Club del Libro
     */
    @RequestMapping(value = "/crea", method = RequestMethod.POST)
    public String creaClubDelLibro(final ClubDelLibro club,
                                   final @RequestParam("generi")
                                           String[] generi,
                                   final @RequestParam("copertina")
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
        club.setEsperto(esperto);
        try {
            byte[] imageBytes = copertina.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            club.setImmagineCopertina(base64Image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Genere> gList =
                clubService.getGeneri(Arrays.asList(generi.clone()));
        club.setGeneri(gList);
        this.clubService.creaClubDelLibro(club);
        return "redirect:/club-del-libro/";
    }

    @RequestMapping(value = "/modifica-dati", method = RequestMethod.POST)
    public String modificaDatiClub(final ClubDelLibro club,
                                   final @RequestParam("generi") String[] generi,
                                   final @RequestParam("copertina") MultipartFile copertina) {

        if(!copertina.isEmpty()) {
            try {
                byte[] imageBytes = copertina.getBytes();
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                club.setImmagineCopertina(base64Image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<Genere> gList =
                clubService.getGeneri(Arrays.asList(generi.clone()));
        club.setGeneri(gList);
        this.clubService.modificaDatiClub(club);
        return "redirect:/club-del-libro/";
    }

    @RequestMapping(value = "/modifica-dati/{id}", method = RequestMethod.GET)
    public String modificaDatiClub(final @PathVariable int id,
                                   Model model) {
        model.addAttribute("club", this.clubService.getClubByID(id));
        return "modifica-club";
    }

}
