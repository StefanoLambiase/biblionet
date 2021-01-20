package it.unisa.c07.biblionet.utils.validazione;

import java.util.HashMap;

/**
 * Implementa la funzionalità di verifica che una stringa
 * rispetti una regex.
 */
public class RegexTester {

    /**
     * Implementa la funzionalità di verifica che una stringa rispetti
     * una regex.
     * @param regexToTest la regex da testare
     * @return true se la rispetta, false altrimenti
     */
    public boolean toTest(final HashMap<String, String> regexToTest) {

        return regexToTest.entrySet().stream().allMatch(
                entry -> {
                    if (entry.getKey() == null) {
                        return true;
                    }

                    return entry.getKey().matches(entry.getValue());
                }
        );
    }

}
