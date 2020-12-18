package it.unisa.c07.biblionet.model.dao;

import it.unisa.c07.biblionet.model.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroDAO extends JpaRepository<Libro,Integer> {
}
