package it.unisa.c07.biblionet.prenotazioneLibri.service.bookApiAdapter;

import it.unisa.c07.biblionet.model.entity.Libro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Implementa l'intefaccia del design pattern Adapter per
 * l'interfacciamento con la Google API Books
 * Esegue la chiamata alla API e riceve un JSON che viene
 * trasformato in un oggetto Libro di BiblioNet
 */
public class GoogleBookApiAdapterImpl implements BookApiAdapter {

    String googleApiUrl = "https://www.googleapis.com/books/v1/volumes?q=isbn:";
    int ERROR_STATUS_CODE = 299;

    /**
     * Implementa la funzionalità che converte la risposta
     * ottenuta dall'interrogazione della google API
     * per i libri, tramite ISBN, in un oggetto della
     * classe Libro, definita in BiblioNet
     *
     * * Documentazione di google sulla API: https://developers.google.com/books/docs/overview
     * ! Attualmente la chiamata viene fatta dalla classe BiblionetApplication a riga 44 con isbn fisso
     * ! Non funziona la parte di conversione
     * TODO: implementare la parte di conversione
     *
     * @param isbn Il codice ISBN a 13 cifre del libro che si vuole cercare
     * @return L'oggetto Libro contenente le informazioni sul libro cercato
     */
    @Override
    public Libro getLibroDaBookApi(final String isbn) {
        try {
            // Compose url string
            String formattedIsbn = isbn.replace("-", "");
            String urlString = googleApiUrl + formattedIsbn;
            URL url = new URL(urlString);

            // Connection part
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            // Check the response status code
            int status = con.getResponseCode();
            Reader sr = null;
            if (status > ERROR_STATUS_CODE) {
                sr = new InputStreamReader(con.getErrorStream());
                //? Dovrebbe ritornare il messaggio di errore nel service?
                return null;
            } else {
                sr = new InputStreamReader(con.getInputStream());
                BufferedReader bf = new BufferedReader(sr);
                String inputLine;
                StringBuilder stringBuilder = new StringBuilder();
                while ((inputLine = bf.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
                bf.close();
                con.disconnect();
                // Stampa in console del libro trovato
                System.out.println("\n --- LIBRO TROVATO --- \n");
                System.out.println(stringBuilder.toString());

                return creaLibroDaResponse(stringBuilder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO: Implementare completamente
    private Libro creaLibroDaResponse(final StringBuilder stringBuilder) {
        return new Libro();
    }

}
