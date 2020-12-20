package it.unisa.c07.biblionet.clubDelLibro.controller;

import it.unisa.c07.biblionet.clubDelLibro.service.ClubDelLibroService;
import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;


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
    @RequestMapping(value="/", method=RequestMethod.GET)
    public String visualizzaListaClubs(Model model){
        model.addAttribute("listaClubs", clubService.visualizzaClubsDelLibro());
        return "visualizza-clubs";
    }

    /**
     * Metodo del controller che si occupa
     * di gestire la chiamata POST
     * per creare un club del libro.
     * @param club Il club del libro passato dalla view
     * @param copertina L'immagine di copertina del Club
     * @return La view che visualizza i Club del Libro
     */
    @RequestMapping(value = "/crea", method = RequestMethod.POST)
    public String creaClubDelLibro(final ClubDelLibro club,
                                   //@RequestParam("generi") String[] generi,
                                   @RequestParam("copertina")MultipartFile copertina)  {
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
            e.printStackTrace(); }

        //INSERIRE LISTA GENERI QUANDO ESISTE GENERI SERVICE

        this.clubService.creaClubDelLibro(club);
        return "redirect:/club-del-libro/";
    }
}
