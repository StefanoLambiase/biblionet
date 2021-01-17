package it.unisa.c07.biblionet.model.entity.utente;

import it.unisa.c07.biblionet.model.entity.Genere;

import java.util.List;

/**
 * Questa è un interfaccia contenente un solo metodo
 * utilizzato per ricevere i generi di un utente.
 */
public interface HaGenere {

    /**
     * Implementa la funzionalità di restituzione dei generi da parte
     * delle entità che li possiedono.
     * @return la lista di generi
     */
    List<Genere> getGeneri();
}
