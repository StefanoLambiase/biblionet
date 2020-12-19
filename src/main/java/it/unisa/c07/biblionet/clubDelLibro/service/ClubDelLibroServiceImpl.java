package it.unisa.c07.biblionet.clubDelLibro.service;

import it.unisa.c07.biblionet.model.dao.ClubDelLibroDAO;
import it.unisa.c07.biblionet.model.entity.ClubDelLibro;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubDelLibroServiceImpl implements ClubDelLibroService {

    private final ClubDelLibroDAO clubDAO;

    @Override
    public ClubDelLibro creaClubDelLibro(ClubDelLibro club) {
        return (ClubDelLibro) clubDAO.save(club);
    }
}
