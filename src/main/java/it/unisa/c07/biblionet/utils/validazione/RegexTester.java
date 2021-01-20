package it.unisa.c07.biblionet.utils.validazione;

import java.util.HashMap;

public class RegexTester {

    public boolean toTest(HashMap<String,String> regexToTest) {

        return regexToTest.entrySet().stream().allMatch(
                entry -> {
                    if(entry.getKey() == null)
                        return true;

                    return entry.getKey().matches(entry.getValue());
                }
        );
    }

}
