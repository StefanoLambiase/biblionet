package it.unisa.c07.biblionet.utils.validazione;

/**
 * Classe che fornisce i parametri di validazione
 * per i campi di evento.
 */
public final class ValidazioneEvento {

    private ValidazioneEvento() { }

    private static final int LUNGHEZZA_MINIMA_NOME = 1;
    private static final int LUNGHEZZA_MASSIMA_NOME = 30;

    public static boolean isNomeValido(final String nome) {
        return nome.length() >= LUNGHEZZA_MINIMA_NOME
               && nome.length() <= LUNGHEZZA_MASSIMA_NOME;
    }

    private static final int LUNGHEZZA_MASSIMA_DESCRIZIONE = 255;

    public static boolean isDescrizioneValida(final String descrizione) {
        return descrizione.length() <= LUNGHEZZA_MASSIMA_DESCRIZIONE;
    }

}
