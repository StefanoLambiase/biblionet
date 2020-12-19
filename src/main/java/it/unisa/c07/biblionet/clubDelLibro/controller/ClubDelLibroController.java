package it.unisa.c07.biblionet.clubDelLibro.controller;

import it.unisa.c07.biblionet.clubDelLibro.service.ClubDelLibroService;
import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("/club-del-libro")
public class ClubDelLibroController {

    private final ClubDelLibroService clubService;

    @RequestMapping(value = "/crea", method = RequestMethod.POST)
    public String creaClubDelLibro(ClubDelLibro club, HttpServletRequest request) {
        Esperto esperto = (Esperto) request.getSession().getAttribute("utente");
        club.setEsperto(esperto);
        clubService.creaClubDelLibro(club);
        return "crea-club";
    }




}
