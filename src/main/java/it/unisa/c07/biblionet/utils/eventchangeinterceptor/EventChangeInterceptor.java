package it.unisa.c07.biblionet.utils.eventchangeinterceptor;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import it.unisa.c07.biblionet.model.entity.Evento;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventChangeInterceptor extends EmptyInterceptor {

    private static final long serialVersionUID = -814390916422407298L;

    /**
     * L'istanza dell'Intercettore.
     */
    private static EventChangeInterceptor instance;

    /**
     * La lista dei Consumer collegati all'Intercettore.
     */
    private List<OnEventChangeConsumer> reactors = new LinkedList<>();

    /**
     * Aggiunge un Consumer alla lista dei Consumer.
     * @param reactor Il Consumer da iscrivere all'Intercettore
     */
    public void subscribe(final OnEventChangeConsumer reactor) {
        this.reactors.add(reactor);
    }

    /**
     * Inizializza e restituisce l'unica istanza dell'intercettore.
     * @return L'istanza dell'Intercettore
     */
    public static EventChangeInterceptor getInstance() {
        if (EventChangeInterceptor.instance == null) {
            EventChangeInterceptor.instance = new EventChangeInterceptor();
        }
        return EventChangeInterceptor.instance;
    }

    /**
     * Reagisce quando vengono inseriti i cambiamenti nel database.
     *
     * Passa ogni evento a tutti i Consumer.
     * @param entity L'entità inserita nel database
     *
     */
    @Override
    public boolean onFlushDirty(
        final Object entity,
        final Serializable id,
        final Object[] currentState,
        final Object[] previousState,
        final String[] propertyNames,
        final Type[] types
    ) {
        if (entity.getClass() != Evento.class) {
            return super.onFlushDirty(
                entity,
                id,
                currentState,
                previousState,
                propertyNames,
                types
            );
        }
        var evento = (Evento) entity;
        this.reactors.forEach(x -> x.accept(evento));
        return true;
    }

}
