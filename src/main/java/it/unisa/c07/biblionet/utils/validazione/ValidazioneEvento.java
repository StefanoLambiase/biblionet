package it.unisa.c07.biblionet.utils.validazione;

/**
 * Classe che fornisce i parametri di validazione
 * per i campi di evento.
 * @author Nicola Pagliara
 * @author Luca Topo
 */
public final class ValidazioneEvento {

    /**
     * Costruttore vuoto.
     */
    private ValidazioneEvento() { }

    /**
     * Lunghezza minima del nome dell'evento.
     */
    private static final int LUNGHEZZA_MINIMA_NOME = 1;
    /**
     * Lunghezza massima del nome di un evento.
     */
    private static final int LUNGHEZZA_MASSIMA_NOME = 30;

    /**
     * Implementa la funzionalità di validazione di un nome di un evento.
     * @param nome Il nome dell'evento inserito
     * @return True se il nome rispetta le lunghezze minime e massime
     */
    public static boolean isNomeValido(final String nome) {
        return nome.length() >= LUNGHEZZA_MINIMA_NOME
               && nome.length() <= LUNGHEZZA_MASSIMA_NOME;
    }

    /**
     * Lunghezza massima della descrizione di un evento.
     */
    private static final int LUNGHEZZA_MASSIMA_DESCRIZIONE = 255;

    /**
     * Implementa la funzionalità di validazione della descrizione di un evento.
     * @param descrizione La descrizione dell'evento inserito
     * @return True se la descizione rispetta la lunghezza massima
     */
    public static boolean isDescrizioneValida(final String descrizione) {
        return descrizione.length() <= LUNGHEZZA_MASSIMA_DESCRIZIONE;
    }

}
