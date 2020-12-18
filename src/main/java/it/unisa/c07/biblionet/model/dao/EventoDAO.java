package it.unisa.c07.biblionet.model.dao;

import it.unisa.c07.biblionet.model.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoDAO extends JpaRepository<Evento,Integer> {
}
