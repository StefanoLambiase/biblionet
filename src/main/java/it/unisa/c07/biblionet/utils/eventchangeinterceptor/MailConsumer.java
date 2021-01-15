package it.unisa.c07.biblionet.utils.eventchangeinterceptor;

import it.unisa.c07.biblionet.model.entity.Evento;

/**
 * CORRENTEMENTE DUMMY!
 *
 * Un consumer per {@link EventChangeInterceptor} che invia una mail
 * ad ogni iscritto all'evento.
 */
public class MailConsumer implements OnEventChangeConsumer {

    /**
     * CORRENTEMENTE DUMMY!
     *
     * Invia una mail ad ogni iscritto ad un evento con i dati dell'evento.
     *
     * @param evento L'evento di cui inviare i dati.
     */
    @Override
    public void accept(final Evento evento) {
        System.err.println(evento.getNomeEvento());
    }

}
